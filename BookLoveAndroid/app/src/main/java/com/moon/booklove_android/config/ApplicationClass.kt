package com.moon.booklove_android.config

import android.app.Application
import com.kakao.sdk.common.KakaoSdk
import com.moon.booklove_android.adapter.*
import com.moon.booklove_android.data.model.User
import com.moon.booklove_android.config.util.PreferenceUtil
import com.moon.booklove_android.config.util.RetrofitInterceptor
import com.moon.booklove_android.data.dto.*
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ApplicationClass : Application() {

    companion object{
        const val SERVER_URL = "http://192.168.35.235:8185/"
        lateinit var retrofit: Retrofit
        lateinit var prefs: PreferenceUtil
        lateinit var currentuser: User

        var interest: MutableList<String> = mutableListOf("가정/요리/뷰티", "건강/취미/레저","경제경영"
            , "고등학교참고서", "고전", "과학", "대학교재/전문서적", "만화", "사회과학", "소설/시/희곡", "수험서/자격증"
            , "어린이", "에세이", "여행", "역사", "예술/대중문화", "외국어", "유아", "인문학", "자기계발", "장르소설"
            , "전집/중고전집", "종교/역학", "좋은부모", "중학교참고서", "청소년", "초등학교참고서", "컴퓨터/모바일"
        )
        var bookCategoryAdapter: BookItemAdapter? = null
        var recommand: MutableList<String> = mutableListOf("같은 성별/나이대의 사람들이 관심 있어요", "좋아하는 카테고리의 책들이에요", "베스트셀러 도서를 만나보세요", "신간 도서를 만나보세요")
        var bookListAdapter: BookCategoryAdapter? = null
        var search: MutableList<String> = mutableListOf("책 제목으로 검색했어요!", "작가 이름으로 검색했어요!", "장르로 검색했어요!")
        var bookInfo : SingleResult<BookInfoResDTO>?= null
        var bookSimilarAdapter: BookItemAdapter? = null
        var bookDetailAdapter: BookDetailAdapter?=null
        var bookInfoHeader: MutableList<String> = mutableListOf("저자 : ", "출판사 : ", "분류 : ", "가격 : ", "구매링크 : ")
        var bookRecommandAdapter: BookRecommAdapter?=null
        var bookRecommData: List<BookRecomm>?=null
        var homeBannerAdapter: HomeBannerAdapter?=null
        var homeBannerData: MutableList<BookInfoResDTO>?=null

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
