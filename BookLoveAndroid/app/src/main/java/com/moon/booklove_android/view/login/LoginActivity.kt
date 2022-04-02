package com.moon.booklove_android.view.login

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.common.model.ClientError
import com.kakao.sdk.common.model.ClientErrorCause
import com.kakao.sdk.user.UserApiClient
import com.moon.booklove_android.view.main.MainActivity
import com.moon.booklove_android.view.signup.SignUpActivity
import com.moon.booklove_android.view.collect.CollectActivity
import com.moon.booklove_android.config.ApplicationClass.Companion.initRetrofit
import com.moon.booklove_android.config.ApplicationClass.Companion.prefs
import com.moon.booklove_android.databinding.ActivityLoginBinding
import com.moon.booklove_android.view.login.presenter.LoginPresenterImpl

class LoginActivity : AppCompatActivity(), LoginView {

    private lateinit var presenter: LoginPresenterImpl

    // 카카오계정으로 로그인 공통 callback 구성
    // 카카오톡으로 로그인 할 수 없어 카카오계정으로 로그인할 경우 사용됨
    private val callback: (OAuthToken?, Throwable?) -> Unit = { token, error ->
        if (error != null) {
            Log.e("TAG", "카카오계정으로 로그인 실패", error)
        } else if (token != null) {
            Log.i("TAG", "카카오계정으로 로그인 성공 ${token.accessToken}")
        }
    }

    override fun onStart() {
        super.onStart()

    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        presenter = LoginPresenterImpl(this@LoginActivity)

        if(prefs.getUserId() != ""){
            presenter.autoNormalLogin(prefs.getUserId()!!, prefs.getLoginType()!!,this@LoginActivity)
        }

//        val keyHash = Utility.getKeyHash(this)//onCreate 안에 입력해주자
//        Log.d("Hash", keyHash)

        binding.loginButton.setOnClickListener {
            presenter.normalLogin(binding.idEditText.text.toString()
                , binding.passwordEditText.text.toString(),this@LoginActivity)
        }

        binding.kakaoIcon.setOnClickListener {
            kakaoLogin()
        }

        binding.signupTextView.setOnClickListener {
            goSignUpPage()
        }
    }

    override fun kakaoLogin(){
        // 카카오톡이 설치되어 있으면 카카오톡으로 로그인, 아니면 카카오계정으로 로그인
        if (UserApiClient.instance.isKakaoTalkLoginAvailable(this)) {
            UserApiClient.instance.loginWithKakaoTalk(this) { token, error ->
                if (error != null) {
                    Log.e("TAG", "카카오톡으로 로그인 실패 $error", error)
                    // 사용자가 카카오톡 설치 후 디바이스 권한 요청 화면에서 로그인을 취소한 경우,
                    // 의도적인 로그인 취소로 보고 카카오계정으로 로그인 시도 없이 로그인 취소로 처리 (예: 뒤로 가기)
                    if (error is ClientError && error.reason == ClientErrorCause.Cancelled) {
                        return@loginWithKakaoTalk
                    }
                    // 카카오톡에 연결된 카카오계정이 없는 경우, 카카오계정으로 로그인 시도
                    UserApiClient.instance.loginWithKakaoAccount(this, callback = callback)
                } else if (token != null) {
                    prefs.setJWTAccess(token.accessToken)
                    initRetrofit()
                    presenter.socialLogin(this@LoginActivity)
                }
            }
        } else {
            UserApiClient.instance.loginWithKakaoAccount(this, callback = callback)
        }
    }

    override fun loginComplete(res:String) {
        if(res=="first login"){
            val intent = Intent(applicationContext, MainActivity::class.java)
            finishAffinity()
            startActivity(intent)
        }else{
            val intent = Intent(applicationContext, CollectActivity::class.java)
            finishAffinity()
            startActivity(intent)
        }
    }

    override fun goSignUpPage() {
        val intent = Intent(applicationContext, SignUpActivity::class.java)
        startActivity(intent)
    }
}