package com.moon.booklove_android.view.detail

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.moon.booklove_android.adapter.BookAdapter
import com.moon.booklove_android.config.ApplicationClass
import com.moon.booklove_android.config.ApplicationClass.Companion.bookInfo
import com.moon.booklove_android.config.getAuthorBooks
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
//        val bookAdapter = BookAdapter()
//        //리스트 데이터 갱신(카테고리 리스트)
//        bookAdapter.submitList(getAuthorBooks())
//        binding.recyclerView.adapter = bookAdapter

    }

    override fun bindInfo() {
        var book = bookInfo!!.data
        Glide.with(this).load("${book.cover}").into(binding.cover)
        binding.title.text = book.title
        binding.author.text = book.author
        binding.publisher.text = book.publisher
        binding.category.text = book.categoryName
        binding.price.text = book.priceStandard.toString()
        binding.link.text = book.link


    }

}