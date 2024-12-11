package com.example.pertemuan9.repository

import com.example.pertemuan9.data.entity.Mahasiswa

interface RepositoryMhs {
    suspend fun insertMhs(mahasiswa: Mahasiswa)
}