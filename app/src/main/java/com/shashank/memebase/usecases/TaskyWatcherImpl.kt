package com.shashank.memebase.usecases

import android.text.Editable
import android.text.TextWatcher
import com.shashank.memebase.usecases.domain.TextChanged
import com.shashank.memebase.views.TaskyAppCompatEditText

open class TaskyWatcherImpl(
    taskyAppCompatEditText: TaskyAppCompatEditText,
    callBack: TextChanged
) : TextWatcher {

    private var taskyAppCompatEditText: TaskyAppCompatEditText
    var textChanged : TextChanged? = null

    init {
        this.taskyAppCompatEditText = taskyAppCompatEditText
        this.textChanged = callBack
    }
    override fun beforeTextChanged(p0: CharSequence, p1: Int, p2: Int, p3: Int) {/*TODO("Not yet implemented")*/}

    override fun onTextChanged(p0: CharSequence, p1: Int, p2: Int, p3: Int) {/*TODO("Not yet implemented")*/}

    override fun afterTextChanged(p0: Editable?) {textChanged?.onTextChanged(p0 as Editable, taskyAppCompatEditText)}
}