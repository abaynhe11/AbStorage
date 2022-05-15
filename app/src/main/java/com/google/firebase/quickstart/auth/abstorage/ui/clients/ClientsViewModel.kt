package com.google.firebase.quickstart.auth.abstorage.ui.clients


import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.google.firebase.quickstart.auth.abstorage.data.Client
import com.google.firebase.quickstart.auth.abstorage.data.ClientRepository
import com.google.firebase.quickstart.auth.abstorage.data.ClientRoomDatabase
import com.google.firebase.quickstart.auth.abstorage.data.ClientRoomRepository


class ClientsViewModel(application: Application) : AndroidViewModel(application) {
    private val clientRepository: ClientRepository

    init {
        val clientDao = ClientRoomDatabase.getInstance(application).clientDao()
        clientRepository = ClientRoomRepository(clientDao)
    }

    val allClients: LiveData<List<Client>> = clientRepository.getAllClients()
}