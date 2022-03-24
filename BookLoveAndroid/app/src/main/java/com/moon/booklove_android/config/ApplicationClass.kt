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
        var recommand: MutableList<String> = mutableListOf("같은 나이대의 사람들이 관심 있어요", "같은 성별의 사람들이 관심 있어요", "같은 장르의 책인데 한 번 보시겠어요?")
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
