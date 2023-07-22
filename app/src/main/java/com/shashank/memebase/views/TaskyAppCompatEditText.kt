package com.shashank.memebase.views

import android.R.attr.*
import android.annotation.SuppressLint
import android.content.Context
import android.content.res.TypedArray
import android.graphics.drawable.Drawable
import android.text.InputType
import android.text.Selection
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.View.OnTouchListener
import android.widget.FrameLayout
import androidx.appcompat.widget.AppCompatEditText
import androidx.core.content.ContextCompat
import com.shashank.memebase.R
import com.shashank.memebase.databinding.LayoutInputFieldBinding


class TaskyAppCompatEditText(context: Context, attrs: AttributeSet) : FrameLayout(context, attrs), OnTouchListener {

    lateinit var subLayout: LayoutInputFieldBinding
    private var error : Boolean = false
    private var isPassword : Boolean = false
    private var drawableLast : Drawable? = null
    private lateinit var editText : AppCompatEditText
    private var inputType : Int = 0
    private var hint : String? = null


    init {
        setupView(attrs)
        setError(false)
        setDrawableLast()
        setInputType()
        setHint()
        setValid(false)
        switchInputType()
        setTouchListener()
    }

    private fun setHint() {
        subLayout.hint = hint
    }

    private fun setInputType() {
        subLayout.etInput.inputType = inputType
    }

    private fun setDrawableLast() {
        subLayout.drawableLast = drawableLast
    }

    fun setError(error : Boolean) {
        subLayout.error = error
    }

    fun setValid(valid: Boolean) {
        subLayout.valid = valid
    }
    private fun switchInputType() {
        subLayout.password = isPassword
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun setTouchListener() {
        subLayout.etInput.setOnTouchListener(this)
    }

    @SuppressLint("CustomViewStyleable")
    private fun setupView(attrs: AttributeSet) {
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        subLayout = LayoutInputFieldBinding.inflate(inflater, this, false)
        addView(subLayout.root)

        editText = subLayout.etInput
        val typedArray: TypedArray = context.obtainStyledAttributes(attrs, R.styleable.EditTextInputField)
        error = typedArray.getBoolean(R.styleable.EditTextInputField_error, false)
        drawableLast = typedArray.getDrawable(R.styleable.EditTextInputField_drawableLast)
        inputType = typedArray.getInt(R.styleable.EditTextInputField_inputType, InputType.TYPE_CLASS_TEXT)
        hint = typedArray.getString(R.styleable.EditTextInputField_hint)
        isPassword = typedArray.getBoolean(R.styleable.EditTextInputField_isPassword, false)
        typedArray.recycle()
    }

    override fun onTouch(v: View, event: MotionEvent): Boolean {
        if(event.action == MotionEvent.ACTION_UP ){
            showHidePassword(event)
        }

        return false
    }

    private fun showHidePassword(event: MotionEvent) {
        val textLocation = IntArray(2)
        if (clickedLocation(event) >= clickableLocation(textLocation[0])) {

            if(checkIfDrawableEndAvailable()) {

                val drawableText = ContextCompat.getDrawable(context, R.drawable.ic_password_text)
                val drawableDots = ContextCompat.getDrawable(context, R.drawable.ic_password_dots)

                if(isPassword){
                    subLayout.drawableLast = drawableDots
                }else{
                    subLayout.drawableLast = drawableText
                }
                isPassword = !isPassword
                switchInputType()
                Selection.setSelection(subLayout.etInput.text, subLayout.etInput.text.toString().length)
            }
        }

    }

    private fun checkIfDrawableEndAvailable(): Boolean {
        return subLayout.etInput.compoundDrawablesRelative[2] != null && !subLayout.valid!!
    }

    private fun clickedLocation(event: MotionEvent): Int {
        return event.rawX.toInt()
    }

    private fun clickableLocation(textLocation : Int): Int {
        return textLocation + subLayout.etInput.width - subLayout.etInput.totalPaddingRight
    }
}