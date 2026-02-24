package it.branjsmo.jetpackmvi.data.remote

import it.branjsmo.jetpackmvi.domain.model.PostTheme
import it.branjsmo.jetpackmvi.domain.repository.RepositoryImageProvider
import javax.inject.Inject

class PicsumImageProvider @Inject constructor() : RepositoryImageProvider {
    override suspend fun getPostImageUrl(postId: Int, theme: PostTheme, width: Int, height: Int): String {
        return when (theme) {
            PostTheme.LANDSCAPE -> "https://picsum.photos/seed/$postId/$width/$height"
            PostTheme.ROBOT -> "https://robohash.org/$postId?size=${width}x${height}"
            PostTheme.AVATAR -> "https://i.pravatar.cc/$width?u=$postId"
            PostTheme.TECH -> "https://loremflickr.com/$width/$height/technology?lock=$postId"
            PostTheme.KITTEN -> "https://loremflickr.com/$width/$height/kitten?lock=$postId"
            PostTheme.FOOD -> "https://loremflickr.com/$width/$height/food?lock=$postId"
            PostTheme.NATURE -> "https://loremflickr.com/$width/$height/nature?lock=$postId"
            PostTheme.BEARD -> "https://placebeard.it/$width/$height?random=$postId"
            PostTheme.CAGE -> "https://www.placecage.com/$width/$height?random=$postId"
        }
    }
}