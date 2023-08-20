package com.shashank.memebase.meme.domain

import android.widget.PopupWindow
import androidx.lifecycle.MutableLiveData
import com.shashank.memebase.globals.Constants

interface PopUpTaskyListDialog {

    fun showAgendaDialog(agenda: Constants.AgendaDialog) : PopupWindow

    fun dialogObserver() : MutableLiveData<String>

}