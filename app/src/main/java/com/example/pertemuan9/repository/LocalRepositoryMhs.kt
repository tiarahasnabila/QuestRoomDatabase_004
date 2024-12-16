package com.example.pertemuan9.repository

import com.example.pertemuan9.data.dao.MahasiswaDao
import com.example.pertemuan9.data.entity.Mahasiswa
import kotlinx.coroutines.flow.Flow

class LocalRepositoryMhs (
    private val mahasiswaDao: MahasiswaDao
) : RepositoryMhs{
        override suspend fun insertMhs(mahasiswa: Mahasiswa){
            mahasiswaDao.insertMahasiswa(mahasiswa)
        }
    override fun getAllMhs(): Flow<List<Mahasiswa>> {
        return mahasiswaDao.getAllMahasiswa()
    }
    override fun getMhs(nim: String): Flow<Mahasiswa> {
        return mahasiswaDao.getMahasiswa(nim)
    }
}