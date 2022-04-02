package com.moon.booklove_android.view.detail

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.moon.booklove_android.config.ApplicationClass.Companion.bookDetailAdapter
import com.moon.booklove_android.config.ApplicationClass.Companion.bookInfo
import com.moon.booklove_android.config.ApplicationClass.Companion.bookSimilarAdapter
import com.moon.booklove_android.databinding.ActivityDetailBinding
import com.moon.booklove_android.view.detail.presenter.DetailPresenterImpl

class DetailActivity : AppCompatActivity(), DetailView {
    private lateinit var presenter: DetailPresenterImpl
    private lateinit var binding: ActivityDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        presenter = DetailPresenterImpl(this)
        presenter.getBookInfo(this, getIntent().getLongExtra("bookId",0))

    }

    override fun bindInfo() {
        var book = bookInfo!!.data
        Glide.with(this).load("${book.cover}").into(binding.cover)
        binding.title.text = book.title
        binding.bookInfoRecyclerView.adapter = bookDetailAdapter
        binding.recyclerView.adapter = bookSimilarAdapter

    }

}