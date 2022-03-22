package com.moon.booklove_android.config

import android.app.Application

class ApplicationClass : Application() {

    companion object{
//        const val SERVER_URL = "https://i6d208.p.ssafy.io:8185/"
//        lateinit var retrofit: Retrofit
//        lateinit var prefs: PreferenceUtil
//        lateinit var recyclerView: RecyclerView
//        lateinit var playerRecyclerViewAdapter: PlayerRecyclerViewAdapter

        var interest: MutableList<String> = mutableListOf("소설", "에세이")
        var checkedInterest = mutableListOf<Int>()
    }

    override fun onCreate() {
        super.onCreate()

        // 앱이 처음 생성되는 순간, retrofit 인스턴스를 생성
//        retrofit = Retrofit.Builder()
//            .baseUrl(SERVER_URL)
//            .addConverterFactory(GsonConverterFactory.create())
//            .build()

//        prefs = PreferenceUtil(applicationContext)
    }
}
