package com.moon.booklove_android.fragment

import android.R
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.moon.booklove_android.adapter.BookAdapter
import com.moon.booklove_android.adapter.BookCategoryAdapter
import com.moon.booklove_android.config.ApplicationClass.Companion.items
import com.moon.booklove_android.config.getGenre
import com.moon.booklove_android.config.getSearch
import com.moon.booklove_android.databinding.FragmentGenreBinding


private const val TAG = "HomeFragment"

class GenreFragment  : Fragment(){
    private lateinit var binding: FragmentGenreBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentGenreBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val myAdapter = ArrayAdapter(requireContext(), R.layout.simple_spinner_dropdown_item, items)

        binding.spinner.adapter = myAdapter

        binding.spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View?,
                position: Int,
                id: Long
            ) {

                //아이템이 클릭 되면 맨 위부터 position 0번부터 순서대로 동작하게 됩니다.
                when (position) {
                    0 -> {
                        val bookAdapter = BookAdapter()
                        bookAdapter.submitList(getGenre())
                        binding.myGridView.adapter = bookAdapter
                    }
                    1 -> {
                        val bookAdapter = BookAdapter()
                        bookAdapter.submitList(getGenre())
                        binding.myGridView.adapter = bookAdapter
                    }
                    2 -> {
                        val bookAdapter = BookAdapter()
                        bookAdapter.submitList(getGenre())
                        binding.myGridView.adapter = bookAdapter
                    }
                    3 -> {
                        val bookAdapter = BookAdapter()
                        bookAdapter.submitList(getGenre())
                        binding.myGridView.adapter = bookAdapter
                    }
                    4 -> {
                        val bookAdapter = BookAdapter()
                        bookAdapter.submitList(getGenre())
                        binding.myGridView.adapter = bookAdapter
                    }
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>) {

            }
        }


    }
}