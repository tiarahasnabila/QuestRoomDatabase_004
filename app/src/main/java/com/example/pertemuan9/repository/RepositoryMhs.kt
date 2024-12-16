package com.example.pertemuan9.repository

import com.example.pertemuan9.data.entity.Mahasiswa
import kotlinx.coroutines.flow.Flow

interface RepositoryMhs {
    suspend fun insertMhs(mahasiswa: Mahasiswa)

    fun getAllMhs(): Flow<List<Mahasiswa>>

    fun getMhs(nim: String): Flow<Mahasiswa>
}