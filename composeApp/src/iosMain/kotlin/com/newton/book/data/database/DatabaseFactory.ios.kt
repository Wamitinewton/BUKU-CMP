package com.newton.book.data.database

import androidx.room.Room
import androidx.room.RoomDatabase


actual class DatabaseFactory {
    actual fun create(): RoomDatabase.Builder<FavoriteBookDatabase> {
        return Room.databaseBuilder<FavoriteBookDatabase>(
            name = ""
        )
    }

}