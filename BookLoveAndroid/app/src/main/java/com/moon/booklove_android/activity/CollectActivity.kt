package com.moon.booklove_android.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.tabs.TabLayoutMediator
import com.moon.booklove_android.R
import com.moon.booklove_android.databinding.ActivityCollectBinding
import com.moon.booklove_android.fragment.CollectGenderFragment

class CollectActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityCollectBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val transaction = supportFragmentManager.beginTransaction().replace(R.id.frameLayout, CollectGenderFragment())
        transaction.commit()

    }


}