package com.yosha10.final_project.core.ui

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.yosha10.final_project.core.di.Injection
import com.yosha10.final_project.core.domain.usecase.DisasterUseCase
import com.yosha10.final_project.main.MainViewModel

class ViewModelFactory private constructor(private val disasterUseCase: DisasterUseCase) :
    ViewModelProvider.NewInstanceFactory() {

    companion object {
        @Volatile
        private var instance: ViewModelFactory? = null

        fun getInstance(context: Context): ViewModelFactory =
            instance
                ?: synchronized(this) {
                    instance
                        ?: ViewModelFactory(
                            Injection.provideDisasterUseCase(
                                context
                            )
                        )
                }
    }

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T =
        when {
            modelClass.isAssignableFrom(MainViewModel::class.java) -> {
                MainViewModel(disasterUseCase) as T
            }

            else -> throw Throwable("Unknown ViewModel class: " + modelClass.name)
        }

}