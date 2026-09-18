package com.example.notebook.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.notebook.data.entity.note
import kotlinx.coroutines.flow.Flow

@Dao
interface noteDao {
    //获取所有note返回list,list可空
    @Query("""SELECT * FROM note
            ORDER BY createdAt DESC""")
    fun getAllNotes(): Flow<List<note>>

    @Insert
    suspend fun insertNote(note: note)

    @Update
    suspend fun updateNote(note: note)

    @Query("""SELECT * FROM note
            WHERE id = :noteId""")
    suspend fun getNoteById(noteId: Long): note?

    //根据group查note
    @Query("""SELECT * FROM note
            WHERE `group` = :group
            ORDER BY createdAt DESC""")
    fun getNoteByGroup(group: String): Flow<List<note>>

    @Query("""
    SELECT * FROM note
    WHERE title LIKE '%' || :title || '%'
       OR content LIKE '%' || :title || '%'
    ORDER BY createdAt DESC
""")
    fun searchAll(title: String): Flow<List<note>>

    //限定某一group的，title模糊查询
    @Query("""
    SELECT * FROM note
    WHERE `group` = :group
      AND (
          title LIKE '%' || :title || '%'
          OR content LIKE '%' || :title || '%'
      )
    ORDER BY createdAt DESC
""")
    fun search(group: String, title: String): Flow<List<note>>


    @Delete
    suspend fun deleteNote(note: note)
}