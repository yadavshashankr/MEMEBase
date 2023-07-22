package com.shashank.memebase.usecases

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.shashank.memebase.usecases.domain.FragmentInflater

class FragmentInflaterImpl : FragmentInflater {
    private lateinit var fragmentManager: FragmentManager

    override fun setFragmentManager(fragmentManager: FragmentManager) {
        this.fragmentManager = fragmentManager
    }

    override fun inflateFragment(fragment: Fragment, viewID: Int) {
        fragmentManager.beginTransaction().replace(viewID, fragment, javaClass.name).commitAllowingStateLoss()
    }

    override fun removeFragment(fragment : Fragment) {
        fragmentManager.beginTransaction().remove(fragment).commitAllowingStateLoss()
    }
}