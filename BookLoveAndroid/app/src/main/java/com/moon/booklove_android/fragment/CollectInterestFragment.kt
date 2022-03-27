package com.moon.booklove_android.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatButton
import androidx.core.view.get
import androidx.fragment.app.Fragment
import com.google.android.material.chip.Chip
import com.moon.booklove_android.R
import com.moon.booklove_android.activity.CollectActivity
import com.moon.booklove_android.activity.MainActivity
import com.moon.booklove_android.config.ApplicationClass.Companion.initRetrofit
import com.moon.booklove_android.config.ApplicationClass.Companion.interest
import com.moon.booklove_android.config.ApplicationClass.Companion.prefs
import com.moon.booklove_android.config.toast
import com.moon.booklove_android.databinding.FragmentCollectInterestBinding
import com.moon.booklove_android.dto.ReissuanceResDTO
import com.moon.booklove_android.dto.SingleResult
import com.moon.booklove_android.dto.UserInfoUpdateReqDTO
import com.moon.booklove_android.service.TokenService
import com.moon.booklove_android.service.UserService
import com.moon.booklove_android.util.RetrofitCallback

class CollectInterestFragment  : Fragment(){
    private lateinit var binding: FragmentCollectInterestBinding
    private lateinit var nextButton: AppCompatButton
    lateinit var selectedGender: String
    var selectedAgeRange: Int = 0
    var checkedInterestIdx = mutableListOf<Int>()
    var checkedInterestList = ArrayList<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (arguments != null) {
            selectedGender = requireArguments().getString("gender").toString()
            selectedAgeRange = requireArguments().getInt("ageRange")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCollectInterestBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        nextButton = (activity as CollectActivity).findViewById(R.id.nextStepButton)
        nextButton.text = "완료"

        init()

        nextButton.setOnClickListener {
            checkedInterestIdx = binding.chipGroup.checkedChipIds

            for(checkedCategory in checkedInterestIdx){
                 checkedInterestList.add(binding.chipGroup[checkedCategory-1].tag.toString())
            }
            updateUserInfo(UserInfoUpdateReqDTO(selectedAgeRange,checkedInterestList, selectedGender))
        }

    }

    private fun init(){
        for(chip in interest){
            binding.chipGroup.addView(
                Chip(context).apply{
                    text = chip
                    tag = chip
                    isCheckable = true
                    setChipBackgroundColorResource(R.color.light_purple)
                }
            )
        }
    }

    private fun updateUserInfo(userInfoUpdateReqDTO: UserInfoUpdateReqDTO) {
        UserService().userInfoUpdate(userInfoUpdateReqDTO, object : RetrofitCallback<SingleResult<Any>> {
            override fun onSuccess(code: Int, responseData: SingleResult<Any>) {
                if (responseData.data==true) {
                    toast("정보 저장 성공!", requireContext())
                    val mainIntent = Intent(requireContext(), MainActivity::class.java)
                    startActivity(mainIntent)
                } else {
                    toast("문제가 발생하였습니다. 다시 시도해주세요.", requireContext())
                }
            }

            override fun onFailure(code: Int) { toast( "문제가 발생하였습니다. 다시 시도해주세요.",requireContext()) }

            override fun onError(t: Throwable) { toast("문제가 발생하였습니다. 다시 시도해주세요.", requireContext()) }

            override fun onExpired(code: Int) {
                super.onExpired(code)
                TokenService().reissuance(object : RetrofitCallback<SingleResult<ReissuanceResDTO>> {
                    override fun onSuccess(code: Int, responseData: SingleResult<ReissuanceResDTO>) {
                        if (responseData.output==1) {
                            prefs.setJWTAccess(responseData.data.accessToken)
                            prefs.setJWTRefresh(responseData.data.refreshToken)
                            initRetrofit()
                            updateUserInfo(userInfoUpdateReqDTO)
                        } else toast("문제가 발생하였습니다. 다시 시도해주세요.", requireContext())
                    }

                    override fun onFailure(code: Int) { toast("유효하지 못한 접근입니다.", requireContext()) }

                    override fun onError(t: Throwable) { toast("유효하지 못한 접근입니다.", requireContext()) }

                    override fun onExpired(code: Int) {}
                })
            }
        })
    }

    companion object {

        private const val gender = "gender"
        private const val ageRange = "ageRange"

        @JvmStatic
        fun newInstance(param1: String, param2: Int) =
            CollectInterestFragment().apply {
                arguments = Bundle().apply {
                    putString(gender, param1)
                    putInt(ageRange, param2)
                }
            }
    }

}