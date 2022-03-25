package com.moon.booklove_android.config

import android.app.Application
import com.kakao.sdk.common.KakaoSdk
import com.moon.booklove_android.util.PreferenceUtil
import com.moon.booklove_android.util.RetrofitInterceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ApplicationClass : Application() {

    companion object{
        const val SERVER_URL = "http://192.168.35.84:8185/"
        lateinit var retrofit: Retrofit
        var jwtaccess: String = ""
        lateinit var prefs: PreferenceUtil
//        lateinit var recyclerView: RecyclerView
//        lateinit var playerRecyclerViewAdapter: PlayerRecyclerViewAdapter

        var interest: MutableList<String> = mutableListOf("소설", "에세이")
        var checkedInterest = mutableListOf<Int>()
        fun initRetrofit(){
            retrofit = Retrofit.Builder()
                .baseUrl(SERVER_URL)
                .client(provideOkHttpClient(RetrofitInterceptor()))
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }

        fun provideOkHttpClient(interceptor: RetrofitInterceptor): OkHttpClient
                = OkHttpClient.Builder().run {
            addInterceptor(interceptor)
            build()
        }
    }

    override fun onCreate() {
        super.onCreate()

        KakaoSdk.init(this, "d4e7ef1673fec6a777393689a82748d7")

        prefs = PreferenceUtil(applicationContext)
        if (prefs.getJWTRefresh()!=null){
            jwtaccess = prefs.getJWTAccess()!!
        }

        // 앱이 처음 생성되는 순간, retrofit 인스턴스를 생성
        initRetrofit()

    }

//    private fun provideOkHttpClient(interceptor: RetrofitInterceptor): OkHttpClient
//            = OkHttpClient.Builder().run {
//        addInterceptor(interceptor)
//        build()
//    }
//
//    fun initRetrofit(){
//        retrofit = Retrofit.Builder()
//            .baseUrl(SERVER_URL)
//            .client(provideOkHttpClient(RetrofitInterceptor()))
//            .addConverterFactory(GsonConverterFactory.create())
//            .build()
//    }
}
