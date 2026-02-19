package it.branjsmo.jetpackmvi.data.remote

import it.branjsmo.jetpackmvi.data.remote.dto.PostDto
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface PostApi {
    @GET("posts")
    suspend fun getPosts(): List<PostDto>

    @GET("posts/{id}")
    suspend fun getPostById(@Path("id") id: Int): PostDto

    @POST("posts")
    suspend fun createPost(@Body post: PostDto): PostDto

    @PUT("posts/{id}")
    suspend fun updatePost(@Path("id") id: Int, @Body post: PostDto): PostDto

    @DELETE("posts/{id}")
    suspend fun deletePost(@Path("id") id: Int)

    companion object {
        const val BASE_URL = "https://jsonplaceholder.typicode.com/"
    }
}
