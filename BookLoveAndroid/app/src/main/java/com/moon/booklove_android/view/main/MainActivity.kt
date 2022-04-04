package com.moon.booklove_android.view.main

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.moon.booklove_android.R
import com.moon.booklove_android.config.toast
import com.moon.booklove_android.databinding.ActivityMainBinding
import com.moon.booklove_android.view.login.LoginActivity

class MainActivity : AppCompatActivity() {

    private lateinit var binding:ActivityMainBinding
    private var waitTime = 0L

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.mainBottomNavigation.setupWithNavController(findNavController(R.id.vp_ac_main_frag_pager))

    }

    override fun onBackPressed() {
        if(System.currentTimeMillis() - waitTime >=1500 ) {
            waitTime = System.currentTimeMillis()
            toast("한번 더 뒤로가시면 종료됩니다.",applicationContext)
        } else {
            finish()
        }
    }

    fun logout() {
        toast("로그아웃 했습니다.",applicationContext)
        val intent = Intent(applicationContext, LoginActivity::class.java)
        finishAffinity()
        startActivity(intent)
    }
}