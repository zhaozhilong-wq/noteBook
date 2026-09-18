package com.example.notebook.di

import androidx.room.Room
import com.example.notebook.data.Database
import com.example.notebook.data.repository.GroupPreferencesRepository
import com.example.notebook.data.repository.Repository
import com.example.notebook.ui.detail.DetailViewModel
import com.example.notebook.ui.main.MainViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val appModule = module{

    single {
        Room.databaseBuilder(
            androidContext(),
            Database::class.java,
            "note_database"
        ).fallbackToDestructiveMigration(true)
            .build()
    }

    single {
            get<Database>().noteDao()
    }

    single {
        Repository(
            noteDao = get()
        )
    }

    single {
        GroupPreferencesRepository(
            get()
        )
    }

    viewModel {
        MainViewModel(
            repository = get(),
            groupPreferencesRepository = get(),
            application = get(),
            savedStateHandle = get()
        )
    }

    viewModel {
        DetailViewModel(
            repository = get(),
            application = get(),
            savedStateHandle = get()
        )
    }
}