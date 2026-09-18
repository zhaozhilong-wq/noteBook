package com.example.notebook.ui.main

import android.app.Application
import android.app.framework.base.Effect
import android.app.framework.base.Event
import android.app.framework.base.MVIBaseAndroidVm
import android.app.framework.base.State
import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.example.notebook.data.entity.note
import com.example.notebook.data.repository.GroupPreferencesRepository
import com.example.notebook.data.repository.Repository
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.parcelize.Parcelize

@Parcelize
data class MainUiState(
    val notes: List<note> = emptyList(),
    val groups: List<String> = emptyList(),
    val selectedGroup: String = "全部",
    val isgrid: Boolean = false,
    val selectNote : note? = null
): State

sealed interface MainEvent: Event{
    data class search(
        val group: String, val title: String): MainEvent

    data class selectGroup(val group: String): MainEvent

    data class selectNote(val note: note): MainEvent

    data object toggleGrid: MainEvent

    data class addGroup(val group: String): MainEvent

    data class deleteNote(val note: note): MainEvent

    data class openDetail(val noteId: Long?): MainEvent

}

sealed interface MainEffect: Effect{
    data class navigateToDetail(val noteId: Long?): MainEffect

    object showToast: MainEffect
}


class MainViewModel (
    private val repository: Repository,
    private val groupPreferencesRepository : GroupPreferencesRepository,
    application: Application,
    savedStateHandle: SavedStateHandle
) : MVIBaseAndroidVm<
    MainUiState,
    MainEvent,
    MainEffect
>(
    application,
    savedStateHandle
) {

    private var notesJob: Job? = null
    override fun getInitState(): MainUiState {
        return MainUiState()
    }
    init {
        viewModelScope.launch {
            groupPreferencesRepository.groupFlow.collect { groups ->
                emitState {
                    copy(groups = groups)
                }
            }
        }
    }


//    init {
//        viewModelScope.launch {
//            for (i in 1..6)
//                repository.insertNote(note(i.toLong(),"标题$i","内容$i","工作",System.currentTimeMillis()))
//        }
//    }
    override fun dispatch(event: MainEvent) {
        when(event){
            is MainEvent.selectGroup->{
                emitState { copy(selectedGroup = event.group) }
                notesJob?.cancel()
                notesJob = viewModelScope.launch {

                    val notesFlow = when (event.group) {
                        "全部" -> {
                            repository.getAllNotes()
                        }

                        else -> {
                            repository.getNoteByGroup(event.group)
                        }
                    }

                    notesFlow.collect { notes ->
                        emitState {
                            copy(notes = notes)
                        }
                    }
                }
            }

            MainEvent.toggleGrid -> {
                emitState { copy(isgrid = !isgrid) }
            }
            is MainEvent.selectNote -> {
                emitState { copy(selectNote = event.note) }
            }
            is MainEvent.deleteNote -> {
                viewModelScope.launch {
                    repository.deleteNote(event.note)
                }
                emitState { copy(selectNote = null) }
            }
            is MainEvent.search -> {
                //如果group是全部，就搜索所有note，否则搜索group的note
                notesJob?.cancel()
                if (event.group != "全部")
                {
                    notesJob = viewModelScope.launch {
                        repository.search(event.group, event.title).collect { notes ->
                            emitState {
                                copy(notes = notes)
                            }
                        }
                    }
                }else{
                    notesJob = viewModelScope.launch {
                        repository.searchAll(event.title).collect { notes ->
                            emitState {
                                copy(notes = notes)
                            }
                        }
                    }
                }
            }
            is MainEvent.addGroup -> {
                //判断是否已重复，重复弹toast后面再做
                if(
                    !uiState.value.groups.contains(event.group)
                ){
                    viewModelScope.launch {
                        groupPreferencesRepository.addGroup(event.group)
                    }
                }
                else{
                    emitEffect(
                        MainEffect.showToast
                    )
                }
            }
            is MainEvent.openDetail -> {
                emitEffect(MainEffect.navigateToDetail(event.noteId))
            }
            else -> {}
        }
    }
}