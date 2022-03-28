package com.moon.booklove_android.activity

import android.os.Bundle
import android.view.View
import android.widget.RelativeLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.Navigation
import androidx.navigation.Navigation.findNavController
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupWithNavController
import com.moon.booklove_android.R
import com.moon.booklove_android.adapter.MainFragmentStatePagerAdapter
import com.moon.booklove_android.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding:ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

//        val homeFragment =
//            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
//        navController = navHostFragment.navController
//        binding.mainBottomNavigation.setupWithNavController(Navigation.findNavController(binding.))
        binding.mainBottomNavigation.setupWithNavController( findNavController(R.id.vp_ac_main_frag_pager))
        configureBottomNavigation()

    }

    private fun configureBottomNavigation(){
//        binding.vpAcMainFragPager.adapter = MainFragmentStatePagerAdapter(supportFragmentManager, 5)
//
//        binding.tlAcMainBottomMenu.setupWithViewPager(binding.vpAcMainFragPager)
//
//        val bottomNaviLayout: View = this.layoutInflater.inflate(R.layout.bottom_navigation_tab, null, false)
//
//        binding.tlAcMainBottomMenu.getTabAt(0)!!.customView = bottomNaviLayout.findViewById(R.id.btn_bottom_navi_home_tab) as RelativeLayout
//        binding.tlAcMainBottomMenu.getTabAt(1)!!.customView = bottomNaviLayout.findViewById(R.id.btn_bottom_navi_search_tab) as RelativeLayout
//        binding.tlAcMainBottomMenu.getTabAt(2)!!.customView = bottomNaviLayout.findViewById(R.id.btn_bottom_navi_list_tab) as RelativeLayout
//        binding.tlAcMainBottomMenu.getTabAt(3)!!.customView = bottomNaviLayout.findViewById(R.id.btn_bottom_navi_favorite_tab) as RelativeLayout
//        binding.tlAcMainBottomMenu.getTabAt(4)!!.customView = bottomNaviLayout.findViewById(R.id.btn_bottom_navi_mypage_tab) as RelativeLayout

    }
}