package it.branjsmo.jetpackmvi.domain.usecase

import it.branjsmo.jetpackmvi.domain.repository.PostRepository
import javax.inject.Inject

class DeletePostUseCase @Inject constructor(
    private val repository: PostRepository
) {
    suspend operator fun invoke(id: Int): Boolean {
        return repository.deletePost(id)
    }
}
