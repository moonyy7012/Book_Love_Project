package com.moon.booklove_android.view.main.search

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.moon.booklove_android.config.ApplicationClass
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

        binding.searchView.apply {
            queryHint = "검색어를 입력해주세요."
            setOnQueryTextListener(object :
                androidx.appcompat.widget.SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean {
                    // 검색 버튼 누를 때 호출
                    if(query != null){
                        presenter.getBookListSearch(requireContext(), query)
                    }
                    return true
                }

                override fun onQueryTextChange(newText: String?): Boolean { return true }
            })
        }
    }

    override fun connectAdapter() {
        binding.outerRecyclerView.adapter = ApplicationClass.bookListAdapter
    }
}


