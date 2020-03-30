package com.dkbrothers.apps.chatsnow.utils

import android.content.Intent
import android.util.Log
import android.widget.Toast
import com.crashlytics.android.Crashlytics
import com.dkbrothers.apps.chatsnow.R
import com.dkbrothers.apps.chatsnow.view.ui.HomeActivity
import com.dkbrothers.apps.chatsnow.view.ui.IntroActivity
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.database.*
import com.google.firebase.iid.FirebaseInstanceId
import loadPreference
import savePreference
import java.util.*

/**
 * Created by kevin
 *
 * Todo documentacion --> https://android-developers.googleblog.com/2017/11/moving-past-googleapiclient_21.html
 */
class LoginGoogle(val activity: IntroActivity) {

    private val TAG = "LoginGoogle"
    // [START declare_auth]
    private lateinit var mAuth: FirebaseAuth
    // [END declare_auth]
    private lateinit var mGoogleSignInClient: GoogleSignInClient

    private val colors = arrayOf(
        "#616161", "#37474F", "#1976D2", "#0D47A1", "#E91E63", "#edd3b2",
        "#D500F9", "#FF5252", "#880E4F", "#B388FF", "#1DE9B6", "#00B8D4"
        , "#827717", "#76FF03", "#EEFF41", "#FFFF00", "#DD2C00", "#6D4C41"
        , "#CCFF90", "#DCE775", "#00ACC1", "#C2185B", "#FF80AB",
        "#4A148C", "#651FFF", "#304FFE", "#2962FF", "#512DA8", "#1565C0", "#00BFA5", "#9E9D24"
    )

    private fun initAuth() { // [START config_signin]
// Configure Google Sign In
        val gso =
            GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(activity.getString(R.string.default_web_client_id))
                .requestEmail()
                .build()

        mGoogleSignInClient = GoogleSignIn.getClient(activity, gso)

        // [START initialize_auth]
        mAuth = FirebaseAuth.getInstance()
        // [END initialize_auth]
    }

    // [START auth_with_google]
    fun firebaseAuthWithGoogle(googleSignInAccount: GoogleSignInAccount) {
        mAuth.signInWithCredential(
            GoogleAuthProvider.getCredential(
                googleSignInAccount.idToken,
                null
            )
        )
            .addOnCompleteListener { task: Task<AuthResult?> ->
                if (task.isSuccessful) {
                    activity.savePreference(
                        "colorForChatTag",
                        colors[numRandom(
                            0,
                            colors.size - 1
                        )]
                    )
                    //Sign in success, update UI with the signed-in user's information
                    val user = mAuth.currentUser
                    Crashlytics.setUserIdentifier(user!!.uid)
                    activity.savePreference("uid",user.uid)
                    registerOrGetDatas(user)
                } else {
                    activity.hideContentLoadingProgressBar()
                    // If sign in fails, display a message to the user.
                    Crashlytics.log(
                        Log.ERROR, TAG,
                        "Google Sign In failed firebase"
                    )
                    Crashlytics.logException(task.exception)
                    Toast.makeText(
                        activity,
                        R.string.message_error_2,
                        Toast.LENGTH_LONG
                    ).show()
                    //updateUI(null);
                }
            }
    }

    // [END auth_with_google]
    private fun registerOrGetDatas(user: FirebaseUser) {
        Toast.makeText(
            activity, activity.getString(R.string.sincronizando_datos),
            Toast.LENGTH_LONG).show()
        val userData: MutableMap<String, Any?> =
            HashMap()
        val publicPerfil: MutableMap<String, Any?> =
            HashMap()


        userData["n"] = user.displayName
        userData["e"] = user.email
        userData["cC"] = activity.loadPreference("cCode", "W")
        userData["idT"] = FirebaseInstanceId.getInstance().token

        if (user.photoUrl != null) userData["p"] =
            user.photoUrl.toString() else userData["p"] = "not data"

        publicPerfil["n"] = user.displayName
        publicPerfil["isOnline"] = "false-"+Date().time
        publicPerfil["p"] = user.photoUrl.toString()
        publicPerfil["cC"] = activity.loadPreference(
            "cCode", "W")

        FirebaseDatabase.getInstance().reference.child("public-profile")
            .child(user.uid)
            .updateChildren(publicPerfil)

        FirebaseDatabase.getInstance().reference
            .child("users").child(user.uid)
            .updateChildren(userData)
            .addOnCompleteListener { task: Task<Void?> ->
                if (!task.isSuccessful) {
                    Crashlytics.log(
                        Log.ERROR, TAG, "Se no pudo guardar los datos del usuario en firebase")
                    Crashlytics.logException(task.exception)
                    Toast.makeText(activity,
                        R.string.message_error_2, Toast.LENGTH_SHORT).show()
                    activity.hideContentLoadingProgressBar()
                } else {
                    activity.savePreference("showHome", false)
                    Toast.makeText(activity, "Bienvenido", Toast.LENGTH_LONG).show()
                    activity.startActivity(Intent(activity, HomeActivity::class.java))
                    activity.finish()
                }
            }
    }

    // [START signin]
    fun signIn() {
        activity.showContentLoadingProgressBar()
        Toast.makeText(activity, activity.getString(R.string.loading), Toast.LENGTH_LONG).show()
        /*
        if (mGoogleApiClient!!.isConnected) {
            Auth.GoogleSignInApi.signOut(mGoogleApiClient).setResultCallback { status: Status ->
                //updateUI(null);
                if (!status.isSuccess) {
                    Crashlytics.log(
                        Log.ERROR, TAG, "Google SignOut failed:"
                                + status.status
                    )
                }
            }
        }
        */
        val signInIntent = mGoogleSignInClient.signInIntent
        activity.startActivityForResult(signInIntent,
            RC_SIGN_IN
        )
    }

    companion object {
        const val RC_SIGN_IN = 1925
    }

    //https://stackoverflow.com/questions/2785485/is-there-a-unique-android-device-id?rq=1
    init {
        initAuth()
    }

}
