package com.moon.booklove_android.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.tabs.TabLayoutMediator
import com.moon.booklove_android.R
import com.moon.booklove_android.databinding.ActivityCollectBinding
import com.moon.booklove_android.fragment.CollectAgeFragment
import com.moon.booklove_android.fragment.CollectGenderFragment
import com.moon.booklove_android.fragment.CollectInterestFragment

class CollectActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityCollectBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.someId2.text = intent.getStringExtra("name").toString()

        val transaction = supportFragmentManager.beginTransaction().replace(R.id.frameLayout, CollectGenderFragment())
        transaction.commit()

    }

    fun openFragment(num: Int){
        val transaction = supportFragmentManager.beginTransaction()
        when(num){
            1 -> transaction.replace(R.id.frameLayout, CollectGenderFragment())
            2 -> transaction.replace(R.id.frameLayout, CollectAgeFragment())
            3 -> transaction.replace(R.id.frameLayout, CollectInterestFragment())
        }
        transaction.commit()
    }

}