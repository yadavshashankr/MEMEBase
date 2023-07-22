package com.shashank.memebase.entry.viewModels

import android.widget.PopupWindow
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.shashank.memebase.agenda.dialogs.domain.PopUpTaskyListDialog
import com.shashank.memebase.globals.Constants
import com.shashank.memebase.meme.repositories.NetworkRepository
import com.shashank.memebase.usecases.NetworkStatus
import dagger.hilt.android.lifecycle.HiltViewModel
import java.util.concurrent.Executors
import javax.inject.Inject

@HiltViewModel
class EntryViewModel @Inject constructor(networkStatus: LiveData<NetworkStatus>, private val popUpTaskyListDialog: PopUpTaskyListDialog, private val networkRepository: NetworkRepository
) : ViewModel() {
    val networkStatusObserver : LiveData<NetworkStatus> = networkStatus

    fun showAgendaDialog(agenda: Constants.AgendaDialog) : PopupWindow {
        return popUpTaskyListDialog.showAgendaDialog(agenda)
    }

    fun agendaDialogObserver() : LiveData<String>{
        return popUpTaskyListDialog.dialogObserver()
    }

    fun makeMemeApiCall(){
        Executors.newSingleThreadExecutor().execute {
            networkRepository.makeApiCall()
        }
    }
}