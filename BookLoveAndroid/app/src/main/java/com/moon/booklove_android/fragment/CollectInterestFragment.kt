package com.moon.booklove_android.fragment

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.widget.AppCompatButton
import androidx.core.view.get
import androidx.core.view.iterator
import androidx.fragment.app.Fragment
import com.google.android.material.chip.Chip
import com.moon.booklove_android.R
import com.moon.booklove_android.activity.CollectActivity
import com.moon.booklove_android.activity.MainActivity
import com.moon.booklove_android.config.ApplicationClass
import com.moon.booklove_android.config.ApplicationClass.Companion.interest
import com.moon.booklove_android.databinding.FragmentCollectInterestBinding
import com.moon.booklove_android.dto.SingleResult
import com.moon.booklove_android.dto.SocialLoginResDTO
import com.moon.booklove_android.dto.UserInfoUpdateReqDTO
import com.moon.booklove_android.enum.enumAgeRange
import com.moon.booklove_android.service.UserService
import com.moon.booklove_android.util.RetrofitCallback


private const val TAG = "CollectInterestFragment"

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
        nextButton.setText("완료")

        init()

        nextButton.setOnClickListener {
            checkedInterestIdx = binding.chipGroup.checkedChipIds

            for(checkedCategory in checkedInterestIdx){
                 checkedInterestList.add(binding.chipGroup[checkedCategory-1].tag.toString())
            }
            Log.d(TAG, "checkedInterestList: ${checkedInterestList}")
            Log.d(TAG, "checkedInterestIdx: ${checkedInterestIdx}")

            //companion object에 저장한 세 항목(성별, 연령대, 관심사)을 Rest Api로 여기서 post
            Log.d(TAG, "111 selectedGender: ${selectedGender}")
            Log.d(TAG, "111 selectedAgeRange: ${selectedAgeRange}")
            Log.d(TAG, "111 checkedInterestList: ${checkedInterestList}")

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
                Log.d(TAG, responseData.output.toString())
                if (responseData.data==true) {
                    Toast.makeText(requireContext(), "정보 저장 성공!", Toast.LENGTH_SHORT).show()
                    //바로 로그인
                    Log.i("TAGG", responseData.data.toString())
                    val mainIntent = Intent(requireContext(), MainActivity::class.java)
                    startActivity(mainIntent)
                } else {
                    Log.i("TAGG", responseData.toString())
                    Toast.makeText(requireContext(), "문제가 발생하였습니다. 다시 시도해주세요.",
                        Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(code: Int) {
                Log.i("TAGG", ""+code+" ")
                Toast.makeText(requireContext(), "문제가 발생하였습니다. 다시 시도해주세요.", Toast.LENGTH_SHORT)
                    .show()
            }

            override fun onError(t: Throwable) {
                Log.i("TAGG",t.toString())
                Toast.makeText(requireContext(), "문제가 발생하였습니다. 다시 시도해주세요.", Toast.LENGTH_SHORT)
                    .show()
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