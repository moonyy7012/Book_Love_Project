package com.moon.booklove_android.activity

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.moon.booklove_android.databinding.ActivitySignupBinding
import com.moon.booklove_android.model.User

class SignUpActivity : AppCompatActivity() {

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
            }else{
                var normaluser: User = User(-1,binding.idEditText.text.toString(),binding.pwEditText.text.toString(), "normal"
                    , binding.nickNameEditText.text.toString()
                    , ""
                    , "")

                finish()
            }

        }

    }


}