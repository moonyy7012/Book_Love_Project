package com.moon.booklove_android.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.moon.booklove_android.config.toast
import com.moon.booklove_android.databinding.ActivitySignupBinding
import com.moon.booklove_android.dto.*
import com.moon.booklove_android.service.UserService
import com.moon.booklove_android.util.RetrofitCallback

class SignUpActivity : AppCompatActivity() {

    private var checkId = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = ActivitySignupBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.signupButton.setOnClickListener {
            if(binding.idEditText.text.toString()=="" || binding.nickNameEditText.text.toString()=="" ||
                binding.pwEditText.text.toString()=="" || binding.pwConfEditText.text.toString()==""){
                toast("회원정보를 입력해주세요.",applicationContext)
            }else if(!checkId) {
                toast("아이디 중복을 확인하세요.",applicationContext)
            }else if(binding.pwEditText.text.toString() != binding.pwConfEditText.text.toString()) {
                toast("비밀번호와 비밀번호 확인이 다릅니다.",applicationContext)
            }else{
                val normalSignUpReqDTO: NormalSignUpReqDTO
                = NormalSignUpReqDTO(binding.idEditText.text.toString()
                    ,binding.pwEditText.text.toString(), binding.nickNameEditText.text.toString())
                normalSignUp(normalSignUpReqDTO)
            }
        }

        binding.checkIDButton.setOnClickListener {
            checkID(binding.idEditText.text.toString())
        }
    }

    private fun normalSignUp(normalSignUpReqDTO: NormalSignUpReqDTO) {
        UserService().normalSignUp(normalSignUpReqDTO,object : RetrofitCallback<SingleResult<Any>> {
            override fun onSuccess(code: Int, responseData: SingleResult<Any>) {
                if (responseData.output==1) {
                    toast("회원가입이 완료되었습니다.",applicationContext)
                    finish()
                } else {
                    toast("문제가 발생하였습니다. 다시 시도해주세요.",applicationContext)
                }
            }

            override fun onFailure(code: Int) { toast("문제가 발생하였습니다. 다시 시도해주세요.",applicationContext) }

            override fun onError(t: Throwable) { toast("문제가 발생하였습니다. 다시 시도해주세요.",applicationContext) }

            override fun onExpired(code: Int) {}
        })
    }

    private fun checkID(userId: String) {
        UserService().checkID(userId,object : RetrofitCallback<SingleResult<Any>> {
            override fun onSuccess(code: Int, responseData: SingleResult<Any>) {
                if (responseData.output==1) {
                    checkId = true
                    toast( "사용 가능한 아이디 입니다.",applicationContext)
                } else {
                    checkId = false
                    toast( "중복 되는 아이디 입니다. ",applicationContext)
                }
            }

            override fun onFailure(code: Int) { toast("문제가 발생하였습니다. 다시 시도해주세요.",applicationContext) }

            override fun onError(t: Throwable) { toast("문제가 발생하였습니다. 다시 시도해주세요.",applicationContext) }

            override fun onExpired(code: Int) {}
        })
    }

}