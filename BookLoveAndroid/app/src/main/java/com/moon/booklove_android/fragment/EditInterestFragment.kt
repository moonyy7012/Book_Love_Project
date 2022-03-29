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
import androidx.fragment.app.Fragment
import com.google.android.material.chip.Chip
import com.moon.booklove_android.R
import com.moon.booklove_android.activity.CollectActivity
import com.moon.booklove_android.activity.MainActivity
import com.moon.booklove_android.config.ApplicationClass
import com.moon.booklove_android.config.ApplicationClass.Companion.currentuser
import com.moon.booklove_android.config.ApplicationClass.Companion.interest
import com.moon.booklove_android.config.toast
import com.moon.booklove_android.databinding.FragmentCollectInterestBinding
import com.moon.booklove_android.databinding.FragmentEditInterestBinding
import com.moon.booklove_android.dto.ReissuanceResDTO
import com.moon.booklove_android.dto.SingleResult
import com.moon.booklove_android.dto.UserInfoUpdateReqDTO
import com.moon.booklove_android.service.TokenService
import com.moon.booklove_android.service.UserService
import com.moon.booklove_android.util.RetrofitCallback


private const val TAG = "CollectInterestFragment"

class EditInterestFragment  : Fragment(){
    var isClicked = false
    private lateinit var binding: FragmentEditInterestBinding
    var checkedInterestList = ArrayList<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentEditInterestBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        checkedInterestList = currentuser.userCategoryList
        init()

        binding.nextStepButton.setOnClickListener {
            if(isClicked){
                currentuser.userCategoryList = checkedInterestList
                updateUserInfo(UserInfoUpdateReqDTO(currentuser.age,currentuser.userCategoryList, currentuser.gender))
            }else{
                toast("관심사를 선택해주세요", requireContext())
            }
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
                    if(currentuser.userCategoryList.contains(chip)){
                        isChecked = true
                        isCheckedIconVisible = true
                    }
                    setOnClickListener {
                        if(!isClicked){
                            isClicked = true
                            binding.nextStepButton.setBackgroundResource(R.drawable.complete_rectangle)
                        }
                        if(!isChecked){
                            checkedInterestList.remove(chip)
                        }else{
                            checkedInterestList.add(chip)
                        }
                    }
                }
            )
        }

    }

    private fun updateUserInfo(userInfoUpdateReqDTO: UserInfoUpdateReqDTO) {
        UserService().userInfoUpdate(userInfoUpdateReqDTO, object :
            RetrofitCallback<SingleResult<Any>> {
            override fun onSuccess(code: Int, responseData: SingleResult<Any>) {
                if (responseData.data==true) {
                    toast("관심사를 수정했습니다", requireContext())
                    isClicked = false
                    checkedInterestList = arrayListOf()
                    binding.nextStepButton.setBackgroundResource(R.drawable.dark_box_rectangle)
                } else {
                    toast("문제가 발생하였습니다. 다시 시도해주세요.", requireContext())
                }
            }

            override fun onFailure(code: Int) { toast( "문제가 발생하였습니다. 다시 시도해주세요.",requireContext()) }

            override fun onError(t: Throwable) { toast("문제가 발생하였습니다. 다시 시도해주세요.", requireContext()) }

            override fun onExpired(code: Int) {
                super.onExpired(code)
                TokenService().reissuance(object :
                    RetrofitCallback<SingleResult<ReissuanceResDTO>> {
                    override fun onSuccess(code: Int, responseData: SingleResult<ReissuanceResDTO>) {
                        if (responseData.output==1) {
                            ApplicationClass.prefs.setJWTAccess(responseData.data.accessToken)
                            ApplicationClass.prefs.setJWTRefresh(responseData.data.refreshToken)
                            ApplicationClass.initRetrofit()
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

}