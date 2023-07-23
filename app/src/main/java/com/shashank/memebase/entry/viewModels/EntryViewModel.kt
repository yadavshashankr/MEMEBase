package com.shashank.memebase.entry.viewModels

import android.widget.PopupWindow
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shashank.memebase.agenda.dialogs.domain.PopUpTaskyListDialog
import com.shashank.memebase.globals.Constants
import com.shashank.memebase.meme.memesModels.MemeModel
import com.shashank.memebase.meme.repositories.NetworkRepository
import com.shashank.memebase.usecases.NetworkStatus
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EntryViewModel @Inject constructor(networkStatus: LiveData<NetworkStatus>, private val popUpTaskyListDialog: PopUpTaskyListDialog, private val networkRepository: NetworkRepository
) : ViewModel() {
    val networkStatusObserver : LiveData<NetworkStatus> = networkStatus
    var mutableMemeList: MutableLiveData<MemeModel?> = MutableLiveData()
    var memeList: LiveData<MemeModel?> = mutableMemeList
    fun showAgendaDialog(agenda: Constants.AgendaDialog) : PopupWindow {
        return popUpTaskyListDialog.showAgendaDialog(agenda)
    }

    fun agendaDialogObserver() : LiveData<String>{
        return popUpTaskyListDialog.dialogObserver()
    }

    fun makeMemeApiCall() : LiveData<MemeModel?>{
        viewModelScope.launch (Dispatchers.IO){
            val memes = networkRepository.makeApiCall()
            mutableMemeList.postValue(memes)
        }
        return memeList
    }
}