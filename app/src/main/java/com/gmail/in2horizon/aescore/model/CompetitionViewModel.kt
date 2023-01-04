package com.gmail.in2horizon.aescore.model

import androidx.lifecycle.ViewModel
import com.gmail.in2horizon.aescore.data.UserRepository
import com.gmail.in2horizon.aescore.data.CompRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import javax.inject.Inject

@HiltViewModel
class CompetitionViewModel @Inject constructor(userRepository: UserRepository,
                                               compRepository: CompRepository
):ViewModel() {

    fun loadSelectedEntity(id: Long): Job {
        TODO("Not yet implemented")
    }

    fun deleteEntity(id: Long) {
        TODO("Not yet implemented")
    }


}
