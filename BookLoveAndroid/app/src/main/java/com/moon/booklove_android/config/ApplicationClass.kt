package com.moon.booklove_android.config

import android.app.Application
import com.kakao.sdk.common.KakaoSdk
import com.moon.booklove_android.adapter.BookCategoryAdapter
import com.moon.booklove_android.adapter.BookItemAdapter
import com.moon.booklove_android.data.model.User
import com.moon.booklove_android.config.util.PreferenceUtil
import com.moon.booklove_android.config.util.RetrofitInterceptor
import com.moon.booklove_android.data.dto.BookListInfoResDTO
import com.moon.booklove_android.data.dto.ListResult
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ApplicationClass : Application() {

    companion object{
        const val SERVER_URL = "http://192.168.0.9:8185/"
        lateinit var retrofit: Retrofit
        lateinit var prefs: PreferenceUtil
        lateinit var currentuser: User

        var interest: MutableList<String> = mutableListOf("가정/요리/뷰티", "건강/취미/레저","경제경영"
            , "고등학교참고서", "고전", "과학", "대학교재/전문서적", "만화", "사회과학", "소설/시/희곡", "수험서/자격증"
            , "어린이", "에세이", "여행", "역사", "예술/대중문화", "외국어", "유아", "인문학", "자기계발", "장르소설"
            , "전집/중고전집", "종교/역학", "좋은부모", "중학교참고서", "청소년", "초등학교참고서", "컴퓨터/모바일"
        )
        var bookCategoryAdapter: BookItemAdapter? = null
        var bookListAdapter: BookCategoryAdapter? = null
        var recommand: MutableList<String> = mutableListOf("같은 나이대의 사람들이 관심 있어요", "같은 성별의 사람들이 관심 있어요", "같은 장르의 책인데 한 번 보시겠어요?")
        var search: MutableList<String> = mutableListOf("책 제목으로 검색했어요!", "작가 이름으로 검색했어요!", "장르로 검색했어요!")
        var items = mutableListOf("아이템0","아이템1","아이템2","아이템3","아이템4")

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

        // 앱이 처음 생성되는 순간, retrofit 인스턴스를 생성
        initRetrofit()
    }
}
