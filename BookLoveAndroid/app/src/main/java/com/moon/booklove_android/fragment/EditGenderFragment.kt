package com.moon.booklove_android.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatButton
import androidx.fragment.app.Fragment
import com.moon.booklove_android.R
import com.moon.booklove_android.config.ApplicationClass
import com.moon.booklove_android.config.ApplicationClass.Companion.currentuser
import com.moon.booklove_android.config.toast
import com.moon.booklove_android.databinding.FragmentEditGenderBinding
import com.moon.booklove_android.dto.ReissuanceResDTO
import com.moon.booklove_android.dto.SingleResult
import com.moon.booklove_android.dto.UserInfoUpdateReqDTO
import com.moon.booklove_android.service.TokenService
import com.moon.booklove_android.service.UserService
import com.moon.booklove_android.util.RetrofitCallback

class EditGenderFragment  : Fragment(){

    private lateinit var binding: FragmentEditGenderBinding
    private lateinit var nextButton: AppCompatButton
    lateinit var selectedGender: String
    private var isClicked = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentEditGenderBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //nextButton = view.findViewById(R.id.nextStepButton)

        if(currentuser.gender=="Man"){
            binding.maleButton.setBackgroundResource(R.drawable.dark_box_rectangle)
        }else{
            binding.femaleButton.setBackgroundResource(R.drawable.dark_box_rectangle)
        }

        binding.maleButton.apply {
            setOnClickListener {
                isClicked = true
                selectedGender = "Man"
                setBackgroundResource(R.drawable.dark_box_rectangle)
                binding.femaleButton.setBackgroundResource(R.drawable.light_box_rectangle)
                binding.nextStepButton.setBackgroundResource(R.drawable.complete_rectangle)
            }
        }

        binding.femaleButton.apply {
            setOnClickListener {
                isClicked = true
                selectedGender = "Woman"
                setBackgroundResource(R.drawable.dark_box_rectangle)
                binding.maleButton.setBackgroundResource(R.drawable.light_box_rectangle)
                binding.nextStepButton.setBackgroundResource(R.drawable.complete_rectangle)
            }
        }

        binding.nextStepButton.setOnClickListener {
            if(isClicked){
                currentuser.gender = selectedGender
                updateUserInfo(UserInfoUpdateReqDTO(currentuser.age,currentuser.userCategoryList, currentuser.gender))
            }else{
                toast("성별을 선택해주세요", requireContext())
            }
        }
    }

    private fun updateUserInfo(userInfoUpdateReqDTO: UserInfoUpdateReqDTO) {
        UserService().userInfoUpdate(userInfoUpdateReqDTO, object :
            RetrofitCallback<SingleResult<Any>> {
            override fun onSuccess(code: Int, responseData: SingleResult<Any>) {
                if (responseData.data==true) {
                    toast("성별을 수정했습니다", requireContext())
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