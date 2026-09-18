package com.example.notebook.ui.detail

import android.app.Application
import android.app.framework.base.Effect
import android.app.framework.base.Event
import android.app.framework.base.MVIBaseAndroidVm
import android.app.framework.base.State
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.example.notebook.data.entity.note
import com.example.notebook.data.repository.GroupPreferencesRepository
import com.example.notebook.data.repository.Repository
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlinx.parcelize.Parcelize

@Parcelize
data class DetailUiState(
    val note: note? = null,
    val title : String = "",
    val content: String = "",
    val group: String = "全部",
    val groupList: List<String> = emptyList(),
    val isChanged: Boolean = false
): State

sealed interface DetailEvent : Event{
    data class onTitleChange(val title: String): DetailEvent
    data class onContentChange(val content: String): DetailEvent
    data class selectGroup(val group: String): DetailEvent
    data object back : DetailEvent
}
sealed interface DetailEffect : Effect{
    data object back : DetailEffect
}



class DetailViewModel (
    private val repository: Repository,
    private val groupPreferencesRepository : GroupPreferencesRepository,
    application: Application,
    savedStateHandle: SavedStateHandle
) : MVIBaseAndroidVm<
        DetailUiState,
        DetailEvent,
        DetailEffect
        >(
    application,
    savedStateHandle
) {
    override fun getInitState(): DetailUiState {
        return DetailUiState()
    }
    override fun dispatch(event: DetailEvent) {
        when(event){
            DetailEvent.back -> {
                if(uiState.value.isChanged){
                    if (uiState.value.note != null) {
                        val updatedNote = uiState.value.note?.copy(createdAt = System.currentTimeMillis())
                        viewModelScope.launch {
                            repository.updateNote(updatedNote ?: return@launch)
                        }
                    }else
                    {
                        val newNote = note(title = uiState.value.title, content = uiState.value.content, group = uiState.value.group, createdAt = System.currentTimeMillis())
                        viewModelScope.launch {
                            repository.insertNote(newNote)
                        }
                    }
                    emitEffect(DetailEffect.back)
                }else{
                    emitEffect(DetailEffect.back)
                }
            }
            is DetailEvent.onTitleChange -> {
                emitState { copy(note = note?.copy(title = event.title), isChanged = true, title = event.title) }
            }
            is DetailEvent.onContentChange -> {
                emitState { copy(note = note?.copy(content = event.content), isChanged = true, content = event.content) }
            }
            is DetailEvent.selectGroup -> {
                if (uiState.value.group != event.group) {
                    emitState { copy(note = note?.copy(group = event.group), isChanged = true, group = event.group) }

                }
            }
        }
    }

    init {
        viewModelScope.launch {
            groupPreferencesRepository.groupFlow.collect { groups ->
                emitState {
                    copy(groupList = groups)
                }
            }
        }
    }
    suspend fun loadNote(noteId: Long) {
        val note = repository.getNoteById(noteId)
        emitState { copy(note = note, title = note?.title ?: "", content = note?.content ?: "", group = note?.group ?: "全部") }
    }
}