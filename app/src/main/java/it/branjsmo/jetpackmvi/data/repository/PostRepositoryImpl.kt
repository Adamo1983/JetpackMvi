package it.branjsmo.jetpackmvi.data.repository

import it.branjsmo.jetpackmvi.data.local.PostDao
import it.branjsmo.jetpackmvi.data.mappers.toDomain
import it.branjsmo.jetpackmvi.data.mappers.toEntity
import it.branjsmo.jetpackmvi.data.mappers.toDto
import it.branjsmo.jetpackmvi.data.remote.PostApi
import it.branjsmo.jetpackmvi.domain.model.Post
import it.branjsmo.jetpackmvi.domain.repository.PostRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class PostRepositoryImpl @Inject constructor(
    private val api: PostApi,
    private val dao: PostDao
) : PostRepository {

    override fun getPosts(): Flow<List<Post>> = flow {
        // Carica dati dal DB locale immediatamente
        val localPostsFlow = dao.getPosts().map { entities ->
            entities.map { it.toDomain() }
        }
        emitAll(localPostsFlow)

        // Tenta di aggiornare dal network
        try {
            val remotePosts = api.getPosts().map { it.toDomain() }
            dao.deleteAllPosts()
            dao.insertPosts(remotePosts.map { it.toEntity() })
        } catch (e: Exception) {
            // Se fallisce il network, i dati locali sono già stati emessi tramite emitAll che osserva il DB
        }
    }

    override suspend fun getPostById(id: Int): Post? {
        // Tenta dal DB locale
        val localPost = dao.getPostById(id)?.toDomain()
        if (localPost != null) return localPost

        // Se non c'è, tenta dal network
        return try {
            val remotePost = api.getPostById(id).toDomain()
            dao.insertPosts(listOf(remotePost.toEntity()))
            remotePost
        } catch (e: Exception) {
            null
        }
    }

    override suspend fun createPost(post: Post): Post? {
        return try {
            val response = api.createPost(post.toDto())
            val domainPost = response.toDomain()
            dao.insertPosts(listOf(domainPost.toEntity()))
            domainPost
        } catch (e: Exception) {
            null
        }
    }

    override suspend fun updatePost(post: Post): Post? {
        return try {
            val response = api.updatePost(post.id, post.toDto())
            val domainPost = response.toDomain()
            dao.insertPosts(listOf(domainPost.toEntity()))
            domainPost
        } catch (e: Exception) {
            null
        }
    }

    override suspend fun deletePost(id: Int): Boolean {
        return try {
            api.deletePost(id)
            dao.deletePostById(id)
            true
        } catch (e: Exception) {
            false
        }
    }
}
