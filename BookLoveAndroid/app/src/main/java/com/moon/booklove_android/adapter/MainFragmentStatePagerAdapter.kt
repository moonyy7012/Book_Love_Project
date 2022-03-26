package com.moon.booklove_android.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.moon.booklove_android.fragment.*

class MainFragmentStatePagerAdapter(fm : FragmentManager, val fragmentCount : Int) : FragmentStatePagerAdapter(fm) {
    override fun getItem(position: Int): Fragment {
        when(position){
            0 -> return HomeFragment()
            1 -> return SearchFragment()
            2 -> return GenreFragment()
            3 -> return FavoriteFragment()
            4 -> return MyPageFragment()
            else -> return HomeFragment()
        }
    }

    override fun getCount(): Int = fragmentCount

}