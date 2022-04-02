package com.moon.booklove_android.view.main.myPage

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import androidx.viewpager.widget.ViewPager
import com.moon.booklove_android.config.ApplicationClass.Companion.currentuser
import com.moon.booklove_android.databinding.FragmentMypageBinding
import com.moon.booklove_android.view.main.myPage.editAge.EditAgeFragment
import com.moon.booklove_android.view.main.myPage.editCategory.EditCategoryFragment
import com.moon.booklove_android.view.main.myPage.editGender.EditGenderFragment
import com.moon.booklove_android.view.main.myPage.presenter.MyPagePresenterImpl

private const val NUM_PAGES = 3

class MyPageFragment  : Fragment(), MyPageView{

    private lateinit var presenter: MyPagePresenterImpl
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

        presenter = MyPagePresenterImpl(this)

        init()

        val pagerAdapter = ScreenSlidePagerAdapter(childFragmentManager)
        binding.viewpager.adapter = pagerAdapter

    }

    override fun init() {
        if(currentuser.type=="KAKAO"){
            binding.loginTypeTextView.text = "KAKAO"
        }else{
            binding.loginTypeTextView.text = "NORMAL"
        }

        binding.idTextView.text = currentuser.userId
        binding.nameTextView.setText(currentuser.nickname)

        binding.nameUpdateButton.setOnClickListener {
            presenter.userUpdateInfo(binding.nameTextView.text.toString(), requireContext())
        }
    }

    private inner class ScreenSlidePagerAdapter(fa: FragmentManager) : FragmentStatePagerAdapter(fa) {

        override fun getCount(): Int {
            return NUM_PAGES
        }

        override fun getItem(position: Int): Fragment {
            return when(position){ // 페이지 포지션에 따라 그에 맞는 프래그먼트를 보여줌
                0 -> EditGenderFragment()
                1 -> EditAgeFragment()
                2 -> EditCategoryFragment()
                else -> EditGenderFragment()
            }
        }
    }
}