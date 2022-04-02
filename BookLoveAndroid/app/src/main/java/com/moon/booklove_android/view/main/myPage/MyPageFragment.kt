package com.moon.booklove_android.view.main.myPage

import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.ViewGroup
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.moon.booklove_android.R
import com.moon.booklove_android.config.ApplicationClass.Companion.currentuser
import com.moon.booklove_android.config.ApplicationClass.Companion.prefs
import com.moon.booklove_android.databinding.FragmentMypageBinding
import com.moon.booklove_android.view.main.MainActivity
import com.moon.booklove_android.view.main.myPage.editAge.EditAgeFragment
import com.moon.booklove_android.view.main.myPage.editCategory.EditCategoryFragment
import com.moon.booklove_android.view.main.myPage.editGender.EditGenderFragment
import com.moon.booklove_android.view.main.myPage.presenter.MyPagePresenterImpl

private const val NUM_PAGES = 3

class MyPageFragment  : Fragment(), MyPageView{

    private lateinit var presenter: MyPagePresenterImpl
    private lateinit var binding: FragmentMypageBinding

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
            binding.passwordUpdateButton.visibility = GONE
        }else{
            binding.loginTypeTextView.text = "NORMAL"
            binding.passwordUpdateButton.setOnClickListener {
                val builder = AlertDialog.Builder(requireContext())
                builder.setTitle("비밀번호 변경")

                val v1 = layoutInflater.inflate(R.layout.dialog_password, null)
                builder.setView(v1)

                val listener = DialogInterface.OnClickListener { p0, p1 ->
                    val alert = p0 as AlertDialog
                    val prev_pw: EditText? = alert.findViewById(R.id.prev_pw)
                    val update_pw: EditText? = alert.findViewById(R.id.update_pw)
                    presenter.userUpdatePassword(prev_pw!!.text.toString(), update_pw!!.text.toString(), requireContext())
                }
                builder.setPositiveButton("확인", listener)
                builder.setNegativeButton("취소", null)
                builder.show()
            }
        }

        binding.idTextView.text = currentuser.userId
        binding.nameTextView.setText(currentuser.nickname)

        binding.nameUpdateButton.setOnClickListener {
            presenter.userUpdateNickName(binding.nameTextView.text.toString(), requireContext())
        }

        binding.logoutButton.setOnClickListener {
            logout()
        }
    }

    override fun logout() {
        prefs.setJWTAccess("")
        prefs.setJWTRefresh("")
        (activity as MainActivity).logout()
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