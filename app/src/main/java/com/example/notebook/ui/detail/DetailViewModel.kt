package com.example.notebook.ui.detail

import android.app.Application
import android.app.framework.base.Effect
import android.app.framework.base.Event
import android.app.framework.base.MVIBaseAndroidVm
import android.app.framework.base.State
import androidx.lifecycle.SavedStateHandle
import com.example.notebook.data.repository.Repository
import kotlinx.parcelize.Parcelize

@Parcelize
data class DetailUiState(
    val nodeId: Long = 0,
    val title: String = "",
    val content: String = "",
    val group : String = ""
): State

sealed interface DetailEvent : Event{
    data class onTitleChange(val title: String): DetailEvent
    data class onContentChange(val content: String): DetailEvent
    data class selectGroup(val group: String): DetailEvent
    data object back : DetailEvent
}
sealed interface DetailEffect : Effect{

}



class DetailViewModel (
    private val repository: Repository,
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
    }
}