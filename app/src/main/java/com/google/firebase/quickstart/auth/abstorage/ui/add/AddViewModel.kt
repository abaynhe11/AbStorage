package com.google.firebase.quickstart.auth.abstorage.ui.add


import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.quickstart.auth.abstorage.data.Client
import com.google.firebase.quickstart.auth.abstorage.data.ClientRepository
import com.google.firebase.quickstart.auth.abstorage.data.ClientRoomDatabase
import com.google.firebase.quickstart.auth.abstorage.data.ClientRoomRepository

import kotlinx.coroutines.launch

class AddViewModel(application: Application) : AndroidViewModel(application) {
    private val clientRepository: ClientRepository

    init {
        val clientDao = ClientRoomDatabase.getInstance(application).clientDao()
        clientRepository = ClientRoomRepository(clientDao)
    }

    fun insert(client: Client) = viewModelScope.launch {
        clientRepository.insert(client)
    }
}