package it.branjsmo.jetpackmvi.data.repository

import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import it.branjsmo.jetpackmvi.data.local.PostDao
import it.branjsmo.jetpackmvi.data.remote.PicsumImageProvider
import it.branjsmo.jetpackmvi.domain.model.PostTheme
import it.branjsmo.jetpackmvi.domain.repository.RepositoryImageProvider
import okhttp3.OkHttpClient
import okhttp3.Request
import java.io.File
import java.io.FileOutputStream
import javax.inject.Inject

class RepositoryImageProviderImpl @Inject constructor(
    @ApplicationContext private val context: Context,
    private val postDao: PostDao,
    private val onlineProvider: PicsumImageProvider,
    private val okHttpClient: OkHttpClient
) : RepositoryImageProvider {

    override suspend fun getPostImageUrl(postId: Int, theme: PostTheme, width: Int, height: Int): String {
        val post = postDao.getPostById(postId)

        // Se l'immagine è già stata salvata localmente, restituiamo il path del file
        post?.imageUrl?.let { path ->
            val file = File(path)
            if (file.exists()) {
                return file.absolutePath
            }
        }

        // Altrimenti, generiamo l'URL online e scarichiamo l'immagine
        val onlineUrl = onlineProvider.getPostImageUrl(postId, theme, width, height)
        val localPath = downloadImage(onlineUrl, postId)

        if (localPath != null) {
            // Se il post esiste, aggiorniamo il record nel database con il percorso del file locale
            post?.let {
                postDao.insertPosts(listOf(it.copy(imageUrl = localPath)))
            }
            return localPath
        }

        return onlineUrl
    }

    private fun downloadImage(url: String, postId: Int): String? {
        return try {
            val request = Request.Builder().url(url).build()
            val response = okHttpClient.newCall(request).execute()

            if (response.isSuccessful) {
                val directory = File(context.filesDir, "post_images")
                if (!directory.exists()) {
                    directory.mkdirs()
                }

                val file = File(directory, "post_$postId.jpg")
                response.body?.byteStream()?.use { input ->
                    FileOutputStream(file).use { output ->
                        input.copyTo(output)
                    }
                }
                file.absolutePath
            } else {
                null
            }
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }
}