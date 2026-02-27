package it.branjsmo.jetpackmvi.presentation.screen.home.view.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import it.branjsmo.jetpackmvi.domain.model.Post
import it.branjsmo.jetpackmvi.domain.model.PostTheme
import it.branjsmo.jetpackmvi.presentation.theme.JetpackMviTheme

@Composable
fun PostItem(
    post: Post,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .clickable { onClick() },
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant
        )
    ) {
        Column {
            AsyncImage(
                model = post.imageUrl ?: getImageUrlForTheme(post.id, post.theme),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(16f / 9f),
                contentScale = ContentScale.Crop
            )
            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = post.title,
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = post.body,
                    style = MaterialTheme.typography.bodyMedium,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
    }
}

private fun getImageUrlForTheme(postId: Int, theme: PostTheme): String {
    return when (theme) {
        PostTheme.LANDSCAPE -> "https://picsum.photos/seed/$postId/800/400"
        PostTheme.ROBOT -> "https://robohash.org/$postId?size=800x400"
        PostTheme.AVATAR -> "https://i.pravatar.cc/800?u=$postId"
        PostTheme.TECH -> "https://loremflickr.com/800/400/technology?lock=$postId"
        PostTheme.KITTEN -> "https://loremflickr.com/800/400/kitten?lock=$postId"
        PostTheme.FOOD -> "https://loremflickr.com/800/400/food?lock=$postId"
        PostTheme.NATURE -> "https://loremflickr.com/800/400/nature?lock=$postId"
        PostTheme.BEARD -> "https://placebeard.it/800/400?random=$postId"
    }
}

@Preview(showBackground = true)
@Composable
private fun PostItemPreview() {
    JetpackMviTheme {
        val post = Post(1, 1, "Post 1", "Body 1")
        PostItem(post = post, onClick = {})
    }
}
