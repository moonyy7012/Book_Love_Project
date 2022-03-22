package com.moon.booklove_android.activity

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
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
import com.moon.booklove_android.R
import com.moon.booklove_android.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {

    private lateinit var firebaseAuth: FirebaseAuth

    private val googleSignInIntent by lazy {
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id)).requestEmail().build()
        GoogleSignIn.getClient(this, gso).signInIntent
    }

    companion object {
        const val RESULT_CODE = 10
    }

    override fun onStart() {
        super.onStart()
        // Check if user is signed in (non-null) and update UI accordingly.
        val currentUser = firebaseAuth.currentUser
        //firebaseLogin(currentUser)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.loginButton.setOnClickListener {
            //GET으로 정보수집 완료한 유저인지 여부 가져와서 true이면 아래 intent를 실행
//            val mainIntent = Intent(this@LoginActivity, MainActivity::class.java)
//            startActivity(mainIntent)

            //false이면 아래 intent를 실행
            val collectIntent = Intent(this@LoginActivity, CollectActivity::class.java)
            startActivity(collectIntent)
        }

        binding.signupTextView.setOnClickListener {
            val intent = Intent(this@LoginActivity, SignUpActivity::class.java)
            startActivity(intent)

        firebaseAuth = Firebase.auth
        binding.googleIcon.setOnClickListener {
            startActivityForResult(googleSignInIntent, RESULT_CODE)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == Activity.RESULT_OK && requestCode == RESULT_CODE) {
            val result = Auth.GoogleSignInApi.getSignInResultFromIntent(data!!)
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            result?.let {
                if (it.isSuccess) {
                    it.signInAccount?.displayName //이름
                    it.signInAccount?.email //이메일
                    Log.e("Value", it.signInAccount?.email!!)
                    val account = task.getResult(ApiException::class.java)!!
                    Log.d("Value", "firebaseAuthWithGoogle:" + account.id)
                    // 기타 등등
                    firebaseLogin(account)
                } else {
                    Log.e("Value", "error")
                    // 에러 처리
                }
            }
        }
    }

    private fun firebaseLogin(googleAccount: GoogleSignInAccount) {

        val credential = GoogleAuthProvider.getCredential(googleAccount.idToken, null)

        firebaseAuth.signInWithCredential(credential).addOnCompleteListener {
            if (it.isSuccessful) {
                it.result?.user?.displayName //사용자 이름
                val intent = Intent(this@LoginActivity, CollectActivity::class.java)
                intent.putExtra("name", it.result?.user?.displayName)
                startActivity(intent)
            } else {
                //error 처리
            }
        }.addOnFailureListener {
            //error 처리
        }
    }

}