package com.shashank.memebase.usecases.domain

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager


interface FragmentInflater {
    fun setFragmentManager(fragmentManager: FragmentManager)

    fun inflateFragment(fragment: Fragment, viewID: Int)

    fun removeFragment(fragment : Fragment)
}