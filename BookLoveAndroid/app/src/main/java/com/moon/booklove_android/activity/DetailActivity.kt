package com.moon.booklove_android.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.moon.booklove_android.adapter.BookAdapter
import com.moon.booklove_android.adapter.BookCategoryAdapter
import com.moon.booklove_android.config.getAuthorBooks
import com.moon.booklove_android.config.getRecomm
import com.moon.booklove_android.databinding.ActivityDetailBinding

class DetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val bookAdapter = BookAdapter()
        //리스트 데이터 갱신(카테고리 리스트)
        bookAdapter.submitList(getAuthorBooks())
        binding.recyclerView.adapter = bookAdapter

    }

}