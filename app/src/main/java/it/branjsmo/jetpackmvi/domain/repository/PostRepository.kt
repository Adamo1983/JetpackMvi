package it.branjsmo.jetpackmvi.domain.repository

import it.branjsmo.jetpackmvi.domain.model.Post
import kotlinx.coroutines.flow.Flow

interface PostRepository {
    fun getPosts(): Flow<List<Post>>
    suspend fun getPostById(id: Int): Post?
    suspend fun createPost(post: Post): Post?
    suspend fun updatePost(post: Post): Post?
    suspend fun deletePost(id: Int): Boolean
}
