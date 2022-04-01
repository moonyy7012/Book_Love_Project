package com.moon.booklove_android.view.main.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.moon.booklove_android.adapter.BookCategoryAdapter
import com.moon.booklove_android.config.ApplicationClass
import com.moon.booklove_android.config.getRecomm
import com.moon.booklove_android.databinding.FragmentHomeBinding
import com.moon.booklove_android.view.main.home.presenter.HomePresenterImpl

class HomeFragment  : Fragment(), HomeView{

    private lateinit var presenter:HomePresenterImpl
    private lateinit var binding: FragmentHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        presenter = HomePresenterImpl(this)
        presenter.getBookListMain(requireContext())

    }

    override fun bindInfo() {
        binding.outerRecyclerView.adapter = ApplicationClass.bookRecommandAdapter
    }

}