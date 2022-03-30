package com.moon.booklove_android.view.collect

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.moon.booklove_android.R
import com.moon.booklove_android.view.collect.collectAge.CollectAgeFragment
import com.moon.booklove_android.view.collect.collectCategory.CollectCategoryFragment
import com.moon.booklove_android.view.collect.collectGender.CollectGenderFragment
import com.moon.booklove_android.databinding.ActivityCollectBinding

class CollectActivity : AppCompatActivity(), CollectView {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityCollectBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val transaction = supportFragmentManager.beginTransaction().replace(R.id.frameLayout, CollectGenderFragment())
        transaction.commit()
    }

    override fun openFragment(num: Int, gender:String, ageRange:Int){
        val transaction = supportFragmentManager.beginTransaction()
        when(num){
            1 -> transaction.replace(R.id.frameLayout, CollectAgeFragment.newInstance(gender, ageRange))
            2 -> transaction.replace(R.id.frameLayout, CollectCategoryFragment.newInstance(gender, ageRange))
        }
        transaction.commit()
    }
}