package com.moon.booklove_android.view.main.recent

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayout.OnTabSelectedListener
import com.moon.booklove_android.databinding.FragmentRecentBinding
import com.moon.booklove_android.view.main.recent.recentbook.RecentBookFragment
import com.moon.booklove_android.view.main.recent.similarbook.SimilarBookFragment

class RecentFragment  : Fragment(){

    private lateinit var binding: FragmentRecentBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentRecentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val fragment0 = RecentBookFragment()
        val fragment1 = SimilarBookFragment()

        parentFragmentManager.beginTransaction().add(binding.frame.id, fragment0).commit()

        binding.tabs.addOnTabSelectedListener(object : OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                val position = tab.position
                var selected: Fragment
                if (position == 0) {
                    selected = fragment0
                } else {
                    selected = fragment1
                }
                parentFragmentManager.beginTransaction().replace(binding.frame.id, selected)
                    .commit()
            }

            override fun onTabUnselected(tab: TabLayout.Tab) {}
            override fun onTabReselected(tab: TabLayout.Tab) {}
        })
    }
}