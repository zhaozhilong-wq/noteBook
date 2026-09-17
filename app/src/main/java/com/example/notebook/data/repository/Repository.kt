package com.example.notebook.data.repository

import com.example.notebook.data.dao.noteDao
import com.example.notebook.data.entity.note

class Repository (
    private val noteDao: noteDao
){
    fun getAllNotes() = noteDao.getAllNotes()
    fun getNoteByGroup(group: String) = noteDao.getNoteByGroup(group)
    fun search(group: String, title: String) = noteDao.search(group, title)

    suspend fun insertNote(note: note) = noteDao.insertNote(note)
    suspend fun updateNote(note: note) = noteDao.updateNote(note)
    suspend fun deleteNote(note: note) = noteDao.deleteNote(note)
    suspend fun getNoteById(noteId: Long) = noteDao.getNoteById(noteId)
}