package com.innovatribe.testapp

import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.facebook.AccessToken
import com.facebook.CallbackManager
import com.facebook.FacebookCallback
import com.facebook.FacebookException
import com.facebook.login.LoginManager
import com.facebook.login.LoginResult
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FacebookAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import java.util.*

open class AuthMain : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    private lateinit var googleSignInClient: GoogleSignInClient


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_auth_main)




        val faceBookButton = findViewById<Button>(R.id.fb_btn)
        val googleSignInBtn = findViewById<Button>(R.id.googlebtn)
        val signupButton = findViewById<Button>(R.id.signup)
        val tvSignIn = findViewById<TextView>(R.id.not_new_login)

        tvSignIn.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }

        auth = FirebaseAuth.getInstance()

        // Facebbok



        // ...




        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.client_id))
            .requestEmail()
            .build()
        googleSignInClient = GoogleSignIn.getClient(this, gso)

        googleSignInBtn.setOnClickListener {
                googleSignIn()
        }

        faceBookButton.setOnClickListener {

        val intent = Intent(this,Facebook::class.java)
            startActivity(intent)
        }

        signupButton.setOnClickListener {
            val intent = Intent(this, SignupActivity::class.java)
            startActivity(intent)

        }


    }









    private fun googleSignIn() {
        val intent = googleSignInClient.signInIntent

        startActivityForResult(intent, RC_SIGN_IN)
    }

    companion object{
        const val RC_SIGN_IN = 1001

    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data!!)


        if (requestCode == RC_SIGN_IN) {

            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                val account = task.getResult(ApiException::class.java)
                account.idToken?.let { firebaseAuthWithGoogle(it) }
            } catch (e: ApiException) {
                Log.d("faileed", "google failed", e)
            }
        }
    }

    private fun firebaseAuthWithGoogle(idToken: String) {


        val credential = GoogleAuthProvider.getCredential(idToken, null)
        auth.signInWithCredential(credential)
            .addOnCompleteListener(this){task->
                if (task.isSuccessful) {
                    val user = auth.currentUser
                    updateUI(user)

                    Log.d("succc", task.toString())
                } else {
                    updateUI(null)
                    Toast.makeText(this, task.exception.toString(), Toast.LENGTH_SHORT).show()
                }

            }
    }

    private fun updateUI(user: FirebaseUser?) {

        val intent = Intent(this, MainActivity::class.java)

        if (user != null) {
            intent.putExtra("email", user.email)
            intent.putExtra("name", user.displayName)
            intent.putExtra("picUrl", user.photoUrl)
        }

        startActivity(intent)
        finish()
    }

}





