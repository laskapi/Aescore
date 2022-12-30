package com.gmail.in2horizon.aescore.model

import androidx.lifecycle.ViewModel
import com.gmail.in2horizon.aescore.data.AescoreRepository
import com.gmail.in2horizon.aescore.data.CompRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import javax.inject.Inject

@HiltViewModel
class CompetitionViewModel @Inject constructor(aescoreRepository: AescoreRepository,
    compRepository: CompRepository
): MyViewModel(aescoreRepository) {

    override fun loadSelectedEntity(id: Long): Job {
        TODO("Not yet implemented")
    }

    override fun deleteEntity(id: Long) {
        TODO("Not yet implemented")
    }


}
