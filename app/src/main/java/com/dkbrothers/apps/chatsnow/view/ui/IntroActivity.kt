package com.dkbrothers.apps.chatsnow.view.ui

import android.content.Intent
import android.os.Bundle
import android.text.method.LinkMovementMethod
import android.util.Log
import android.view.View
import android.widget.CheckBox
import android.widget.TextView
import android.widget.Toast
import com.crashlytics.android.Crashlytics
import com.dkbrothers.apps.chatsnow.R
import com.dkbrothers.apps.chatsnow.utils.LoginGoogle
import com.dkbrothers.apps.chatsnow.utils.toHtmlText
import com.google.android.gms.auth.api.Auth

class IntroActivity : BaseActivity() {

    private var loginGoogle: LoginGoogle? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_intro)
        initViews()
    }

    private fun initViews(){
        loginGoogle = LoginGoogle( this)
        initContentLoadingProgressBar()
        val checkBox = findViewById<CheckBox>(R.id.chb_term_and_cond)
        findViewById<View>(R.id.sign_in_button)
            .setOnClickListener {
                if (checkBox.isChecked) {
                    loginGoogle?.signIn()
                } else {
                    Toast.makeText(
                        this@IntroActivity, R.string.message_12, Toast.LENGTH_LONG).show()
                }
            }
        val tvTC = findViewById<TextView>(R.id.txt_term_cond)
        tvTC.text = getString(R.string.p_de_p).toHtmlText()
        tvTC.movementMethod = LinkMovementMethod.getInstance()

    }


    public override fun onActivityResult(
        requestCode: Int,
        resultCode: Int,
        data: Intent?
    ) {
        super.onActivityResult(requestCode, resultCode, data)
        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == LoginGoogle.RC_SIGN_IN) {
            val result = Auth.GoogleSignInApi.getSignInResultFromIntent(data)
            if (result.isSuccess) { //Google Sign In was successful, authenticate with Firebase
                val account = result.signInAccount
                Toast.makeText(applicationContext, getString(R.string.verificando), Toast.LENGTH_SHORT).show()
                //para registrar un nuevo usuario premium
                loginGoogle!!.firebaseAuthWithGoogle(account!!)
            } else { //Google Sign In failed, update UI appropriately
                hideContentLoadingProgressBar()
                Crashlytics.log(Log.ERROR, TAG,
                    "Google Sign In failed-statusCode:" + result.status.statusCode)
                Toast.makeText(
                    this@IntroActivity,
                    R.string.message_error_3, Toast.LENGTH_LONG
                ).show()
                // [START_EXCLUDE]
//updateUI(null);
// [END_EXCLUDE]
            }
        }
    }

    companion object {
        private const val TAG = "IntroActivity"
    }

}
