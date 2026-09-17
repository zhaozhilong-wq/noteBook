package com.example.notebook.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.notebook.data.dao.noteDao
import com.example.notebook.data.entity.note

@Database(
    entities = [note::class],
    version = 1,
    exportSchema = false
)
abstract class Database : RoomDatabase(){
    abstract fun noteDao(): noteDao
}