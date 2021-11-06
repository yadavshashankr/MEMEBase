package com.example.memebase.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
import androidx.lifecycle.ViewModelProvider
import com.example.memebase.R
import com.example.memebase.models.userModels.Users
import com.example.memebase.utils.Tools
import com.example.memebase.utils.Tools.Companion.removeWhiteSpaces
import com.example.memebase.viewModels.LoginActivityViewModel

class LoginActivity : AppCompatActivity() {

    private var etUserId: EditText? = null
    private var etUserPassword: EditText? = null
    private var etUserName: EditText? = null
    private var tvRegSign: TextView? = null
    private var btnSubmit: Button? = null
    private var llSignIn: LinearLayout? = null
    private var llRegister: LinearLayout? = null

    var viewModel: LoginActivityViewModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        initViewIds().also {


            tvRegSign?.setOnClickListener {

                if (tvRegSign?.text!!.equals(getString(R.string.signUp))){
                    bringSignUp()
                }else{
                    bringSignIn()
                }

            }

            btnSubmit?.setOnClickListener {

            if (tvRegSign?.text!!.toString().equals(resources.getString(R.string.logIn))){

                viewModel?.insertUserInfo(this, Users(0, removeWhiteSpaces(etUserId?.text.toString()),
                    removeWhiteSpaces(etUserPassword?.text.toString()),
                    etUserName?.text.toString()))


            }else{



                viewModel?.getLoginDetails(applicationContext, removeWhiteSpaces(etUserId?.text.toString()),
                    removeWhiteSpaces(etUserPassword?.text.toString()))?.observe(this, {

                    if (it == null) {
                        Toast.makeText(this, "Not found", Toast.LENGTH_SHORT).show()
                    } else {

                        bringInMainActivity()

                    }

                })

            }



            }



        }
    }

    private fun bringInMainActivity() {
        slideViews().run {
            startActivity(Intent(this@LoginActivity, MainActivity::class.java))
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
        }

    }

    private fun slideViews() {
        llSignIn?.animate()?.translationY(1000f)?.duration = 1000
        btnSubmit?.animate()?.translationY(1000f)?.duration = 1000
        tvRegSign?.animate()?.translationX(1000f)?.duration = 1000
    }

    private fun bringSignIn() {
        llRegister?.animate()?.translationX(-1000f)?.duration = 1000
        llSignIn?.animate()?.translationX(0f)?.duration = 1000
        tvRegSign?.text = getString(R.string.signUp)
    }

    private fun bringSignUp() {
        llRegister?.animate()?.translationX(0f)
        llSignIn?.animate()?.translationX(1000f)
        tvRegSign?.text = getString(R.string.logIn)
    }

    fun initViewIds(){
        etUserId = findViewById(R.id.et_userId)
        etUserPassword = findViewById(R.id.et_userPassword)
        etUserName = findViewById(R.id.et_userName)
        tvRegSign = findViewById(R.id.tv_regSign)
        btnSubmit = findViewById(R.id.btn_submit)
        llSignIn = findViewById(R.id.ll_signIn)
        llRegister = findViewById(R.id.ll_register)
        bringSignIn()

        viewModel = ViewModelProvider(this).get(LoginActivityViewModel::class.java)
    }

    override fun onResume() {
        super.onResume()
        llSignIn?.animate()?.translationY(0f)?.duration = 200
        btnSubmit?.animate()?.translationY(0f)?.duration = 200
        tvRegSign?.animate()?.translationX(0f)?.duration = 200
    }

}