package com.tifd.tugasm3.local

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class TugasViewModel(application: Application) : AndroidViewModel(application) {
    private val tugasRepository: TugasRepository = TugasRepository(application)

    val tugasList: LiveData<List<Tugas>> = tugasRepository.getAllTugas()

    fun insert(tugas: Tugas) {
        viewModelScope.launch {
            tugasRepository.insert(tugas)
        }
    }

    fun updateTugasStatus(id: Int, selesai: Boolean) {
        viewModelScope.launch {
            tugasRepository.updateStatus(id, selesai)
        }
    }
}
