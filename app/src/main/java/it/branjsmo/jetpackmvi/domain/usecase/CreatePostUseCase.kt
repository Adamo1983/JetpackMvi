package it.branjsmo.jetpackmvi.domain.usecase

import it.branjsmo.jetpackmvi.domain.model.Post
import it.branjsmo.jetpackmvi.domain.repository.PostRepository
import javax.inject.Inject

class CreatePostUseCase @Inject constructor(
    private val repository: PostRepository
) {
    suspend operator fun invoke(post: Post): Post? {
        return repository.createPost(post)
    }
}
