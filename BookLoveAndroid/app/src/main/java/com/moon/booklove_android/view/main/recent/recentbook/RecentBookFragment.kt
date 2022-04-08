package com.moon.booklove_android.view.main.recent.recentbook

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.moon.booklove_android.config.ApplicationClass.Companion.bookRecentAdapter
import com.moon.booklove_android.databinding.FragmentRecentbookBinding
import com.moon.booklove_android.view.main.recent.recentbook.presenter.RecentBookPresenterImpl

class RecentBookFragment : Fragment(), RecentBookView{

    private lateinit var presenter: RecentBookPresenterImpl
    private lateinit var binding: FragmentRecentbookBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentRecentbookBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        presenter = RecentBookPresenterImpl(this)
        presenter.getBookListRecent(requireContext())

    }

    override fun bindInfo() {
        binding.outerRecyclerView.adapter = bookRecentAdapter
    }
}