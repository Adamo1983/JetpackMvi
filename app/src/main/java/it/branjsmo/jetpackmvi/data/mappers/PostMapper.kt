package it.branjsmo.jetpackmvi.data.mappers

import it.branjsmo.jetpackmvi.data.local.entity.PostEntity
import it.branjsmo.jetpackmvi.data.remote.dto.PostDto
import it.branjsmo.jetpackmvi.domain.model.Post
import it.branjsmo.jetpackmvi.domain.model.PostTheme

fun PostEntity.toDomain(): Post {
    return Post(
        id = id,
        userId = userId,
        title = title,
        body = body,
        theme = try { PostTheme.valueOf(theme) } catch (e: Exception) { PostTheme.LANDSCAPE }
    )
}

fun Post.toEntity(): PostEntity {
    return PostEntity(
        id = id,
        userId = userId,
        title = title,
        body = body,
        theme = theme.name
    )
}

fun PostDto.toDomain(): Post {
    return Post(
        id = id ?: 0,
        userId = userId,
        title = title,
        body = body,
        theme = try { PostTheme.valueOf(theme ?: "") } catch (e: Exception) { PostTheme.LANDSCAPE }
    )
}

fun Post.toDto(): PostDto {
    return PostDto(
        id = if (id == 0) null else id,
        userId = userId,
        title = title,
        body = body,
        theme = theme.name
    )
}
