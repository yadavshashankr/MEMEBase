package com.shashank.memebase.usecases.domain

import android.content.Context
import androidx.appcompat.widget.Toolbar
import com.google.android.material.appbar.AppBarLayout


interface ToolbarHandler {
    fun setToolBarText(toolbar: Toolbar, text: String)

    fun setToolBarHeight(toolbar: Toolbar, appBar: AppBarLayout, context: Context, isBig: Boolean)
}