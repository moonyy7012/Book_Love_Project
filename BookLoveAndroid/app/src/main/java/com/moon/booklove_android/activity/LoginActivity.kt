package com.moon.booklove_android.activity

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.moon.booklove_android.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.loginButton.setOnClickListener {
            //GET으로 정보수집 완료한 유저인지 여부 가져와서 true이면 아래 intent를 실행
//            val mainIntent = Intent(this@LoginActivity, MainActivity::class.java)
//            startActivity(mainIntent)

            //false이면 아래 intent를 실행
            val collectIntent = Intent(this@LoginActivity, CollectActivity::class.java)
            startActivity(collectIntent)
        }

        binding.signupTextView.setOnClickListener {
            val intent = Intent(this@LoginActivity, SignUpActivity::class.java)
            startActivity(intent)
        }

    }


}