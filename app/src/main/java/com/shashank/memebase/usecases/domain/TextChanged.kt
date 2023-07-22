package com.shashank.memebase.usecases.domain

import android.text.Editable
import com.shashank.memebase.views.TaskyAppCompatEditText

interface TextChanged {
    fun onTextChanged(editable: Editable, taskyAppcompatEditText: TaskyAppCompatEditText)
}