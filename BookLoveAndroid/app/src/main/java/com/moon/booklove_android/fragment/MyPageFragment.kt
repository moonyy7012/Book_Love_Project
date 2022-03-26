package com.moon.booklove_android.fragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import androidx.viewpager.widget.ViewPager
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.moon.booklove_android.R
import com.moon.booklove_android.databinding.FragmentFavoriteBinding
import com.moon.booklove_android.databinding.FragmentMypageBinding


private const val TAG = "HomeFragment"
private const val NUM_PAGES = 3

class MyPageFragment  : Fragment(){
    private lateinit var binding: FragmentMypageBinding
    private lateinit var mPager: ViewPager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMypageBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mPager = view.findViewById(R.id.viewpager)
        val pagerAdapter = ScreenSlidePagerAdapter(childFragmentManager)
        mPager.adapter = pagerAdapter
//        val transaction = childFragmentManager.beginTransaction().replace(R.id.viewpager, EditGenderFragment())
//        transaction.commit()
    }

//    fun openFragment(num: Int){
//        val transaction = childFragmentManager.beginTransaction()
//        when(num){
//            1 -> transaction.replace(R.id.viewpager, EditGenderFragment())
//            2 -> transaction.replace(R.id.viewpager, EditAgeFragment())
//            3 -> transaction.replace(R.id.viewpager, EditInterestFragment())
//        }
//        transaction.commit()
//    }

    private inner class ScreenSlidePagerAdapter(fa: FragmentManager) : FragmentStatePagerAdapter(fa) {

        override fun getCount(): Int {
            return NUM_PAGES
        }

        override fun getItem(position: Int): Fragment {
            return when(position){ // 페이지 포지션에 따라 그에 맞는 프래그먼트를 보여줌
                0 -> EditGenderFragment()
                1 -> EditAgeFragment()
                2 -> EditInterestFragment()
                else -> EditGenderFragment()
            }
        }
    }

}