package com.moon.booklove_android.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.moon.booklove_android.databinding.ActivityCollectBinding

class CollectActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityCollectBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.someId2.text = intent.getStringExtra("name").toString()
    }


}