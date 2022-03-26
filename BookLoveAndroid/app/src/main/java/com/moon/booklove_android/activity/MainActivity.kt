package com.moon.booklove_android.activity

import android.os.Bundle
import android.view.View
import android.widget.RelativeLayout
import androidx.appcompat.app.AppCompatActivity
import com.moon.booklove_android.R
import com.moon.booklove_android.adapter.MainFragmentStatePagerAdapter
import com.moon.booklove_android.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding:ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        configureBottomNavigation()

    }

    private fun configureBottomNavigation(){
        binding.vpAcMainFragPager.adapter = MainFragmentStatePagerAdapter(supportFragmentManager, 5)

        binding.tlAcMainBottomMenu.setupWithViewPager(binding.vpAcMainFragPager)

        val bottomNaviLayout: View = this.layoutInflater.inflate(R.layout.bottom_navigation_tab, null, false)

        binding.tlAcMainBottomMenu.getTabAt(0)!!.customView = bottomNaviLayout.findViewById(R.id.btn_bottom_navi_home_tab) as RelativeLayout
        binding.tlAcMainBottomMenu.getTabAt(1)!!.customView = bottomNaviLayout.findViewById(R.id.btn_bottom_navi_search_tab) as RelativeLayout
        binding.tlAcMainBottomMenu.getTabAt(2)!!.customView = bottomNaviLayout.findViewById(R.id.btn_bottom_navi_list_tab) as RelativeLayout
        binding.tlAcMainBottomMenu.getTabAt(3)!!.customView = bottomNaviLayout.findViewById(R.id.btn_bottom_navi_favorite_tab) as RelativeLayout
        binding.tlAcMainBottomMenu.getTabAt(4)!!.customView = bottomNaviLayout.findViewById(R.id.btn_bottom_navi_mypage_tab) as RelativeLayout

    }
}