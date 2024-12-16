package com.example.pertemuan9.ui.theme.viewmodel

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.pertemuan9.data.entity.Mahasiswa
import com.example.pertemuan9.repository.LocalRepositoryMhs
import com.example.pertemuan9.repository.RepositoryMhs
import com.example.pertemuan9.ui.theme.navigation.AlamatNavigasi
import com.example.pertemuan9.ui.theme.navigation.DestinasiDetail
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class DetailMhsViewModel (
    savedStateHandle: SavedStateHandle,
    private val repositoryMhs: RepositoryMhs,
) : ViewModel() {
    private val _nim: String = checkNotNull(savedStateHandle[DestinasiDetail.NIM])

    val detailUiState: StateFlow<DetailUiState> = repositoryMhs.getMhs(_nim)
        .filterNotNull()
        .map {
            //Log.i("DetailMhsViewModel", "Mahasiswa data found: ${it.nama}, NIM: ${it.nim}")
            DetailUiState(
                detailUiEvent = it.toDetailUiEvent(),
                isLoading = false,
            )
        }
        .onStart {
            //Log.i("DetailMhsViewModel", "Loading data for NIM: $_nim")
            emit(DetailUiState(isLoading = true))
            delay(600)
        }
        .catch {
            //Log.e("DetailMhsViewModel", "Error fetching data: ${it.message}")
            emit(
                DetailUiState(
                    isLoading = false,
                    isError = true,
                    errorMessage = it.message ?: "Terjadi Kesalahan",
                )
            )
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(2000),
            initialValue = DetailUiState(
                isLoading = true,
            ),
        )

    fun deleteMhs() {
        detailUiState.value.detailUiEvent.toMahasiswaEntity().let {
            viewModelScope.launch {
                repositoryMhs.deleteMhs(it)
            }
        }
    }
}

data class DetailUiState(
    val detailUiEvent: MahasiswaEvent = MahasiswaEvent(),
    val isLoading: Boolean = false,
    val isError: Boolean = false,
    val errorMessage: String = ""
) {
    val isUiEventEmpty: Boolean
        get() = detailUiEvent == MahasiswaEvent()

    val isUiEventNotEmpty: Boolean
        get() = detailUiEvent != MahasiswaEvent()
}

// Data class untuk menampung data yang akan ditampilkan di UI

// Memindahkan data dari entity ke ui
fun Mahasiswa.toDetailUiEvent(): MahasiswaEvent {
    //Log.i("DetailMhsViewModel", "Converting Mahasiswa to MahasiswaEvent: $nim, $nama")
    return MahasiswaEvent(
        nim = nim,
        nama = nama,
        jenisKelamin = jenisKelamin,
        alamat = alamat,
        kelas = kelas,
        angkatan = angkatan
    )
}