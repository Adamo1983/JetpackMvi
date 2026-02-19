package it.branjsmo.jetpackmvi.domain.util

import it.branjsmo.jetpackmvi.domain.model.PostTheme

interface ImageProvider {
    fun getPostImageUrl(postId: Int, theme: PostTheme, width: Int, height: Int): String
}
