package com.moon.booklove_android.fragment

import android.R
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import com.moon.booklove_android.adapter.BookAdapter
import com.moon.booklove_android.adapter.BookCategoryAdapter
import com.moon.booklove_android.adapter.FavoriteBookAdapter
import com.moon.booklove_android.config.ApplicationClass
import com.moon.booklove_android.config.getGenre
import com.moon.booklove_android.config.getSearch
import com.moon.booklove_android.databinding.FragmentFavoriteBinding



private const val TAG = "FavoriteFragment"

class FavoriteFragment  : Fragment(){
    private lateinit var binding: FragmentFavoriteBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFavoriteBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val bookAdapter = FavoriteBookAdapter()
        bookAdapter.submitList(getGenre())
        binding.outerRecyclerView.adapter = bookAdapter
    }

}