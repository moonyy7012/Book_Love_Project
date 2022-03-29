package com.moon.booklove_android.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.content.res.AppCompatResources.getDrawable
import androidx.appcompat.widget.AppCompatButton
import androidx.fragment.app.Fragment
import com.moon.booklove_android.R
import com.moon.booklove_android.activity.CollectActivity
import com.moon.booklove_android.config.ApplicationClass
import com.moon.booklove_android.config.ApplicationClass.Companion.currentuser
import com.moon.booklove_android.config.toast
import com.moon.booklove_android.databinding.FragmentCollectAgeBinding
import com.moon.booklove_android.databinding.FragmentEditAgeBinding
import com.moon.booklove_android.dto.ReissuanceResDTO
import com.moon.booklove_android.dto.SingleResult
import com.moon.booklove_android.dto.UserInfoUpdateReqDTO
import com.moon.booklove_android.service.TokenService
import com.moon.booklove_android.service.UserService
import com.moon.booklove_android.util.RetrofitCallback

class EditAgeFragment  : Fragment(){
    private lateinit var binding: FragmentEditAgeBinding
    var isClicked = false
    var selectedAge = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentEditAgeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

       // binding.nextStepButton.setBackgroundResource(R.drawable.dark_box_rectangle)

        when(currentuser.age){
            10 -> binding.teenagerButton.setBackgroundResource(R.drawable.dark_box_rectangle)
            20 -> binding.twentiesButton.setBackgroundResource(R.drawable.dark_box_rectangle)
            30 -> binding.thirtiesButton.setBackgroundResource(R.drawable.dark_box_rectangle)
            40 -> binding.fourtiesButton.setBackgroundResource(R.drawable.dark_box_rectangle)
            50 -> binding.fiftiesButton.setBackgroundResource(R.drawable.dark_box_rectangle)
        }

        binding.teenagerButton.setOnClickListener {
            selectedAge = 10
            clickButton(binding.teenagerButton)
        }
        binding.twentiesButton.setOnClickListener {
            selectedAge = 20
            clickButton(binding.twentiesButton)
        }
        binding.thirtiesButton.setOnClickListener {
            selectedAge = 30
            clickButton(binding.thirtiesButton)
        }
        binding.fourtiesButton.setOnClickListener {
            selectedAge = 40
            clickButton(binding.fourtiesButton)
        }
        binding.fiftiesButton.setOnClickListener {
            selectedAge = 50
            clickButton(binding.fiftiesButton)
        }

        binding.nextStepButton.setOnClickListener {
            if(isClicked){
                currentuser.age = selectedAge
                updateUserInfo(UserInfoUpdateReqDTO(currentuser.age,currentuser.userCategoryList, currentuser.gender))

            }else{
                toast("연령대를 선택해주세요", requireContext())
            }
        }

    }

    private fun clickButton(clickedButton: AppCompatButton){
        isClicked = true
        binding.teenagerButton.setBackgroundResource(R.drawable.light_box_rectangle)
        binding.twentiesButton.setBackgroundResource(R.drawable.light_box_rectangle)
        binding.thirtiesButton.setBackgroundResource(R.drawable.light_box_rectangle)
        binding.fourtiesButton.setBackgroundResource(R.drawable.light_box_rectangle)
        binding.fiftiesButton.setBackgroundResource(R.drawable.light_box_rectangle)
        clickedButton.setBackgroundResource(R.drawable.dark_box_rectangle)
        binding.nextStepButton.setBackgroundResource(R.drawable.complete_rectangle)
    }

    private fun updateUserInfo(userInfoUpdateReqDTO: UserInfoUpdateReqDTO) {
        UserService().userInfoUpdate(userInfoUpdateReqDTO, object :
            RetrofitCallback<SingleResult<Any>> {
            override fun onSuccess(code: Int, responseData: SingleResult<Any>) {
                if (responseData.data==true) {
                    toast("연령대를 수정했습니다", requireContext())
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