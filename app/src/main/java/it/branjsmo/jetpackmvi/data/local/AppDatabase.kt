package it.branjsmo.jetpackmvi.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import it.branjsmo.jetpackmvi.data.local.entity.PostEntity

@Database(
    entities = [PostEntity::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract val postDao: PostDao

    companion object {
        const val DATABASE_NAME = "jetpack_mvi_db"
    }
}
