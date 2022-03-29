package com.moon.booklove_android.view.main.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.moon.booklove_android.adapter.BookCategoryAdapter
import com.moon.booklove_android.config.getSearch
import com.moon.booklove_android.databinding.FragmentSearchBinding
import com.moon.booklove_android.view.main.search.presenter.SearchPresenterImpl

class SearchFragment  : Fragment(), SearchView{

    private lateinit var presenter: SearchPresenterImpl
    private lateinit var binding: FragmentSearchBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSearchBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        presenter = SearchPresenterImpl(this)

        val bookCategoryAdapter = BookCategoryAdapter()
        bookCategoryAdapter.submitList(getSearch())
        binding.outerRecyclerView.adapter = bookCategoryAdapter
    }
}


