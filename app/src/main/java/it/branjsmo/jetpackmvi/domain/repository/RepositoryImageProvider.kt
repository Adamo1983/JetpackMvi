package it.branjsmo.jetpackmvi.domain.repository

import it.branjsmo.jetpackmvi.domain.model.PostTheme

interface RepositoryImageProvider {
    suspend fun getPostImageUrl(postId: Int, theme: PostTheme, width: Int, height: Int): String
}