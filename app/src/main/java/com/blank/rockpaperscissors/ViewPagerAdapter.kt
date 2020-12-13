package com.blank.rockpaperscissors

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

class ViewPagerAdapter(
    fa: FragmentActivity ,
    listener: (CharSequence) -> Unit
) :
    FragmentStateAdapter(fa) {
    private val dataFragments = mutableListOf(
        LandingFragment.newInstance(
            "Bermain suit melawan sesama\npemain." ,
            "1" ,
            listener
        ) ,
        LandingFragment.newInstance(
            "Bermain suit melawan\nkomputer." ,
            "2" ,
            listener
        ) ,
        LandingFragment.newInstance("Tulis namamu di bawah" , "3" , listener)
    )

    override fun getItemCount(): Int = 3

    override fun createFragment(position: Int): Fragment =
        dataFragments[position]
}