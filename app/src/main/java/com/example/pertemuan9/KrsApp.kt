package com.example.pertemuan9

import android.app.Application
import com.example.pertemuan9.dependenciesinjection.ContainerApp
import com.example.pertemuan9.dependenciesinjection.InterfaceContainerApp

class KrsApp :Application() {
    lateinit var containerApp: ContainerApp // fungsinya untuk menyimpan instance

    override fun onCreate() {
        super.onCreate()
        containerApp = ContainerApp(this) //membuat instance App
        //instance adalah objek yang dibuat dari class
    }
}