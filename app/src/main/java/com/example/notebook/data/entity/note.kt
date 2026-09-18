package com.example.notebook.data.entity

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "note")
data class note(
    @PrimaryKey(autoGenerate = true)
    val id : Long=0,
    val title : String,
    val content : String,
    val group: String,
    val createdAt: Long


): Parcelable