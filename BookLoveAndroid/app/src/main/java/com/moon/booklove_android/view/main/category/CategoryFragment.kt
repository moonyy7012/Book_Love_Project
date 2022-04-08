package com.moon.booklove_android.view.main.category

import android.R
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import com.moon.booklove_android.config.ApplicationClass.Companion.bookCategoryAdapter
import com.moon.booklove_android.config.ApplicationClass.Companion.interest
import com.moon.booklove_android.databinding.FragmentGenreBinding
import com.moon.booklove_android.view.main.category.presenter.CategoryPresenterImpl

class CategoryFragment  : Fragment(), CategoryView{

    private lateinit var presenter: CategoryPresenterImpl
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

        presenter = CategoryPresenterImpl(this)

        val myAdapter = ArrayAdapter(requireContext(), R.layout.simple_spinner_dropdown_item, interest)
        binding.spinner.adapter = myAdapter

        binding.spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                presenter.getBookListCategory(requireContext(), interest[position])
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
                presenter.getBookListCategory(requireContext(), interest[0])
            }
        }
    }

    override fun connectAdapter() {
        binding.myGridView.adapter = bookCategoryAdapter
    }
}