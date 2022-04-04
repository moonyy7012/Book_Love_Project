package com.moon.booklove_android.view.main.recent.similarbook

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.moon.booklove_android.adapter.RecentBookAdapter
import com.moon.booklove_android.config.ApplicationClass
import com.moon.booklove_android.config.ApplicationClass.Companion.bookRecentAdapter
import com.moon.booklove_android.config.getGenre
import com.moon.booklove_android.databinding.FragmentSimilarbookBinding
import com.moon.booklove_android.view.main.recent.similarbook.presenter.SimilarBookPresenterImpl

class SimilarBookFragment : Fragment(), SimilarBookView{

    private lateinit var presenter: SimilarBookPresenterImpl
    private lateinit var binding: FragmentSimilarbookBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSimilarbookBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        presenter = SimilarBookPresenterImpl(this)
        presenter.getBookListSimilar(requireContext())

    }

    override fun bindInfo(){
        binding.outerRecyclerView.adapter = bookRecentAdapter
    }
}