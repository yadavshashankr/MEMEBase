package com.shashank.memebase.usecases

import android.animation.ValueAnimator
import android.content.Context
import android.text.TextUtils
import android.view.animation.TranslateAnimation
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import androidx.core.view.children
import com.google.android.material.appbar.AppBarLayout
import com.shashank.memebase.globals.Constants
import com.shashank.memebase.usecases.domain.ToolbarHandler


class ToolbarHandlerImpl : ToolbarHandler {
    private lateinit var translateAnimation: TranslateAnimation

    override fun setToolBarText(toolbar: Toolbar, text: String) {
        toolbar.title = text
        setToolbarTextViewsMarquee(toolbar)
        animateDown(toolbar)
    }

    private fun setToolbarTextViewsMarquee(toolbar: Toolbar) {
        for (child in toolbar.children) {
            if (child is TextView) {
                setMarquee(child)
            }
        }
    }

    private fun setMarquee(textView: TextView) {
        textView.ellipsize = TextUtils.TruncateAt.MARQUEE
        textView.isSelected = true
        textView.marqueeRepeatLimit = -1
    }

    override fun setToolBarHeight(toolbar: Toolbar, appBar: AppBarLayout, context: Context, isBig: Boolean) {
        if(isBig){
            val va = ValueAnimator.ofInt(context.resources.getDimension(com.intuit.sdp.R.dimen._60sdp).toInt(), context.resources.getDimension(com.intuit.sdp.R.dimen._100sdp).toInt())
            va.duration = Constants.AnimationProperties.DURATION
            va.addUpdateListener { animation ->
                val value = animation.animatedValue as Int
                toolbar.layoutParams.height = value
                toolbar.requestLayout()
                appBar.layoutParams.height = value
                appBar.requestLayout()
            }
            va.start()
        }else{
            val va = ValueAnimator.ofInt(context.resources.getDimension(com.intuit.sdp.R.dimen._100sdp).toInt(), context.resources.getDimension(com.intuit.sdp.R.dimen._60sdp).toInt())
            va.duration = Constants.AnimationProperties.DURATION
            va.addUpdateListener { animation ->
                val value = animation.animatedValue as Int
                toolbar.layoutParams.height = value
                toolbar.requestLayout()
                appBar.layoutParams.height = value
                appBar.requestLayout()
            }
            va.start()
        }
    }

    private fun animateDown(toolbar: Toolbar) {
        translateAnimation = TranslateAnimation(0f, 0f, Constants.AnimationProperties.MAX_TRANSLATION_Y, 0f)
        translateAnimation.duration = Constants.AnimationProperties.DURATION
        toolbar.startAnimation(translateAnimation)
    }
}