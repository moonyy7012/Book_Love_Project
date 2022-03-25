package com.moon.booklove_android.activity

import android.app.Activity
import android.app.Application
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.auth.api.Auth
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.common.model.ClientError
import com.kakao.sdk.common.model.ClientErrorCause
import com.kakao.sdk.common.util.Utility
import com.kakao.sdk.user.UserApiClient
import com.moon.booklove_android.R
import com.moon.booklove_android.config.ApplicationClass.Companion.initRetrofit
import com.moon.booklove_android.config.ApplicationClass.Companion.prefs
import com.moon.booklove_android.databinding.ActivityLoginBinding
import com.moon.booklove_android.dto.*
import com.moon.booklove_android.service.UserService
import com.moon.booklove_android.util.RetrofitCallback

class LoginActivity : AppCompatActivity() {

    private val googleSignInIntent by lazy {
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id)).requestEmail().build()
        GoogleSignIn.getClient(this, gso).signInIntent
    }

    // 카카오계정으로 로그인 공통 callback 구성
    // 카카오톡으로 로그인 할 수 없어 카카오계정으로 로그인할 경우 사용됨
    private val callback: (OAuthToken?, Throwable?) -> Unit = { token, error ->
        if (error != null) {
            Log.e("TAG", "카카오계정으로 로그인 실패", error)
        } else if (token != null) {
            Log.i("TAG", "카카오계정으로 로그인 성공 ${token.accessToken}")
        }
    }

    companion object {
        const val RESULT_CODE = 10
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

//        val keyHash = Utility.getKeyHash(this)//onCreate 안에 입력해주자
//        Log.d("Hash", keyHash)

        binding.loginButton.setOnClickListener {
            val user = NormalLoginReqDTO(binding.idEditText.text.toString(), binding.passwordEditText.text.toString())
            normalLogin(user)
        }

        binding.signupTextView.setOnClickListener {
            val intent = Intent(this@LoginActivity, SignUpActivity::class.java)
            startActivity(intent)
        }

        binding.googleIcon.setOnClickListener {
            startActivityForResult(googleSignInIntent, RESULT_CODE)
        }

        binding.kakaoIcon.setOnClickListener {
            kakaoLogin()
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK && requestCode == RESULT_CODE) {
            val result = Auth.GoogleSignInApi.getSignInResultFromIntent(data!!)
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            result?.let {
                if (it.isSuccess) {
                    val account = task.getResult(ApiException::class.java)!!
                    prefs.setJWTAccess(account.idToken.toString())
                    initRetrofit()
                    socialSignUp()
                } else {
                    Log.e("Value", "error")
                }
            }
        }
    }

    private fun kakaoLogin(){
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
//                        Log.i("TAG", "카카오톡으로 로그인 성공 ${token.accessToken}")
//                        Log.d("TAGGG", "카카오톡으로 로그인 성공 ${token}")
//                        //lateinit var kakaouser: SocialLoginReqDTO
//
//                        UserApiClient.instance.me { user, error ->
//                            if (error != null) {
//                                Log.e("TAG", "사용자 정보 요청 실패", error)
//                            }
//                            else if (user != null) {
//                                //kakaouser = SocialLoginReqDTO(-1,"","", "kakao"
//                                    //, user.kakaoAccount?.profile?.nickname.toString()
//                                    //, user.kakaoAccount?.ageRange.toString())
//                            }
//                        }
                    prefs.setJWTAccess(token.accessToken)
                    initRetrofit()
                    socialSignUp()
                }
            }
        } else {
            UserApiClient.instance.loginWithKakaoAccount(this, callback = callback)
        }
    }

    private fun socialSignUp() {
        UserService().socialSignUp(object : RetrofitCallback<SingleResult<SocialLoginResDTO>> {
            override fun onSuccess(code: Int, responseData: SingleResult<SocialLoginResDTO>) {
                if (responseData.data.id > 0L) {
                    Toast.makeText(this@LoginActivity, "회원 가입 성공!", Toast.LENGTH_SHORT).show()
                    //바로 로그인
                    prefs.setJWTAccess(responseData.data.jwtAccess)
                    prefs.setJWTRefresh(responseData.data.jwtRefresh)
                    val intent = Intent(this@LoginActivity, CollectActivity::class.java)
                    startActivity(intent)
                } else {
                    Toast.makeText(this@LoginActivity, "문제가 발생하였습니다. 다시 시도해주세요.",
                        Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(code: Int) {
                Toast.makeText(this@LoginActivity, "문제가 발생하였습니다. 다시 시도해주세요.", Toast.LENGTH_SHORT)
                    .show()
            }

            override fun onError(t: Throwable) {
                Toast.makeText(this@LoginActivity, "문제가 발생하였습니다. 다시 시도해주세요.", Toast.LENGTH_SHORT)
                    .show()
            }
        })
    }

    private fun normalLogin(user: NormalLoginReqDTO) {
        UserService().normalLogin(user, object : RetrofitCallback<SingleResult<NormalLoginResDTO>> {
            override fun onSuccess(code: Int, responseData: SingleResult<NormalLoginResDTO>) {
                if (responseData.data.id > 0L) {
                    Toast.makeText(this@LoginActivity, "회원 가입 성공!", Toast.LENGTH_SHORT).show()
                    //바로 로그인
                    prefs.setJWTAccess(responseData.data.jwtAccess)
                    prefs.setJWTRefresh(responseData.data.jwtRefresh)
                    val intent = Intent(this@LoginActivity, CollectActivity::class.java)
                    startActivity(intent)
                } else {
                    Toast.makeText(this@LoginActivity, "문제가 발생하였습니다. 다시 시도해주세요.1",
                        Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(code: Int) {
                Toast.makeText(this@LoginActivity, "문제가 발생하였습니다. 다시 시도해주세요.2", Toast.LENGTH_SHORT)
                    .show()
            }

            override fun onError(t: Throwable) {
                Toast.makeText(this@LoginActivity, "문제가 발생하였습니다. 다시 시도해주세요.3", Toast.LENGTH_SHORT)
                    .show()
            }
        })
    }

    //    private fun firebaseLogin(googleAccount: GoogleSignInAccount) {
//
//        val credential = GoogleAuthProvider.getCredential(googleAccount.idToken, null)
//
//        firebaseAuth.signInWithCredential(credential).addOnCompleteListener {
//            if (it.isSuccessful) {
////                var googleuser: SocialLoginReqDTO = SocialLoginReqDTO(-1,"","", "google"
////                    , it.result?.user?.displayName.toString()
////                    , "")
//                it.result?.user?.displayName //사용자 이름
//                val intent = Intent(this@LoginActivity, CollectActivity::class.java)
//                intent.putExtra("name", it.result?.user?.displayName)
//                startActivity(intent)
//            } else {
//                //error 처리
//            }
//        }.addOnFailureListener {
//            //error 처리
//        }
//    }
}