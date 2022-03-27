package com.moon.booklove_android.activity

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.moon.booklove_android.config.ApplicationClass
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
                Toast.makeText(this,"회원정보를 입력해주세요.",Toast.LENGTH_SHORT).show()
            }else if(binding.pwEditText.text.toString() != binding.pwConfEditText.text.toString()) {
                Toast.makeText(this,"비밀번호와 비밀번호 확인이 다릅니다.",Toast.LENGTH_SHORT).show()
            }else if(!checkId) {
                Toast.makeText(this,"아이디 중복을 확인하세요.",Toast.LENGTH_SHORT).show()
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
                    finish()
                } else {
                    Toast.makeText(this@SignUpActivity, "문제가 발생하였습니다. 다시 시도해주세요.",
                        Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(code: Int) {
                Toast.makeText(this@SignUpActivity, "문제가 발생하였습니다. 다시 시도해주세요.", Toast.LENGTH_SHORT)
                    .show()
            }

            override fun onError(t: Throwable) {
                Toast.makeText(this@SignUpActivity, "문제가 발생하였습니다. 다시 시도해주세요.", Toast.LENGTH_SHORT)
                    .show()
            }
        })
    }

    private fun checkID(userId: String) {
        UserService().checkID(userId,object : RetrofitCallback<SingleResult<Any>> {
            override fun onSuccess(code: Int, responseData: SingleResult<Any>) {
                if (responseData.output==1) {
                    checkId = true
                } else {
                    Toast.makeText(this@SignUpActivity, "문제가 발생하였습니다. 다시 시도해주세요.",
                        Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(code: Int) {
                Toast.makeText(this@SignUpActivity, "문제가 발생하였습니다. 다시 시도해주세요.", Toast.LENGTH_SHORT)
                    .show()
            }

            override fun onError(t: Throwable) {
                Toast.makeText(this@SignUpActivity, "문제가 발생하였습니다. 다시 시도해주세요.", Toast.LENGTH_SHORT)
                    .show()
            }
        })
    }

}