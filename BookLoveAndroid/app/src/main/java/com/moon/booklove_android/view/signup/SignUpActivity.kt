package com.moon.booklove_android.view.signup

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.moon.booklove_android.config.toast
import com.moon.booklove_android.databinding.ActivitySignupBinding
import com.moon.booklove_android.data.dto.*
import com.moon.booklove_android.view.signup.presenter.SignUpPresenterImpl

class SignUpActivity : AppCompatActivity(), SignUpView {

    private lateinit var presenter:SignUpPresenterImpl
    private lateinit var binding:ActivitySignupBinding
    private var checkId = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySignupBinding.inflate(layoutInflater)
        setContentView(binding.root)

        presenter = SignUpPresenterImpl(this@SignUpActivity)

        binding.signupButton.setOnClickListener {
            signUp()
        }

        binding.checkIDButton.setOnClickListener {
            presenter.checkID(binding.idEditText.text.toString(),this@SignUpActivity)
        }
    }

    override fun idCheck(check: Boolean) {
        checkId = check
    }

    override fun signUp() {
        if(binding.idEditText.text.toString()=="" || binding.nickNameEditText.text.toString()=="" ||
            binding.pwEditText.text.toString()=="" || binding.pwConfEditText.text.toString()==""){
            toast("회원정보를 입력해주세요.",applicationContext)
        }else if(checkId) {
            toast("아이디 중복을 확인하세요.",applicationContext)
        }else if(binding.pwEditText.text.toString() != binding.pwConfEditText.text.toString()) {
            toast("비밀번호와 비밀번호 확인이 다릅니다.",applicationContext)
        }else{
            val normalSignUpReqDTO = NormalSignUpReqDTO(binding.idEditText.text.toString()
                ,binding.pwEditText.text.toString(), binding.nickNameEditText.text.toString())
            presenter.normalSignUp(normalSignUpReqDTO, this@SignUpActivity)
        }
    }

    override fun signUpComplete() {
        finish()
    }
}