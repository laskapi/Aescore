package com.gmail.in2horizon.aescore.viewmodel

import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.lifecycle.viewModelScope
import com.gmail.in2horizon.aescore.MMessage
import com.gmail.in2horizon.aescore.data.CompRepository
import com.gmail.in2horizon.aescore.data.Competition
import com.gmail.in2horizon.aescore.data.User
import com.gmail.in2horizon.aescore.data.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CompsViewModel @Inject constructor(
    val compRepository: CompRepository, userRepository:
    UserRepository
) : BaseViewModel(userRepository) {


    private val _users = MutableStateFlow(emptyList<User>())
    val users = _users.asStateFlow()

    private val _comps = MutableStateFlow(emptyList<Competition>())
    val comps = _comps.asStateFlow()

    private val _selectedComp = MutableStateFlow(Competition())
    val selectedComp = _selectedComp.asStateFlow()

    private val _errorMessage = MutableStateFlow(MMessage(""))
    val errorMessage = _errorMessage.asStateFlow()

    init {
        loadComps()
    }

    fun loadComps() {
        viewModelScope.launch {

            _comps.value = compRepository.loadCompetitions()
            Log.d("comps", _comps.toString())
        }
    }

    fun loadSelectedComp(id: Long?) {
        viewModelScope.launch {
            _selectedComp.value = compRepository.getCompetition(id)

        }

    }

    fun deleteComp(id: Long) {
        viewModelScope.launch {
            try {
                compRepository.deleteCompetition(id)
                loadComps()
            } catch (e: Exception) {
                e.message?.apply { _errorMessage.value = MMessage(this) }
            }

        }
    }


    fun updateComp(): Deferred<Boolean> {

        return viewModelScope.async {
            try {
                val EMPTY_ID = -1L
                if (selectedComp.value.id == EMPTY_ID) {
                    compRepository.addCompetition(selectedComp.value)
                } else {

                    compRepository.updateCompetition(selectedComp.value)
                }
                loadComps()
                return@async true
            } catch (e: Exception) {
                return@async false
            }

        }


    }


    fun loadUsers() {
        viewModelScope.launch {
            _users.value = userRepository.getUsers()
        }

    }

    fun updateLocalSelectedComp(
        name: String? = null, admin: User? = null, users: MutableList<User>? = null,
    ) {
        name?.let { name -> _selectedComp.update { it.copy(name = name) } }
        admin?.let { admin -> _selectedComp.update { it.copy(admin = admin) } }
        users?.let { user -> _selectedComp.update { it.copy(users = users) } }

    }



    fun setSelectedUsersUser(index: Int, user: User) {
        val temp = _selectedComp.value.users.toMutableList()
        temp.set(index, user)
        updateLocalSelectedComp(users = temp)
    }

    fun addSelectedCompUser(user: User) {
        var temp = _selectedComp.value.users.toMutableList()
        temp.add(user)
        updateLocalSelectedComp(users = temp)

    }

    fun removeSelectedCompUsers(addedUsersToDelete: SnapshotStateList<User>) {
        val temp = _selectedComp.value.users.toMutableList()
        temp.removeAll(addedUsersToDelete)
        updateLocalSelectedComp(users = temp)
    }


}
