package com.innovatribe.testapp

import android.content.ContentValues.TAG
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.util.Patterns
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.core.text.isDigitsOnly
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class LoginActivity : AppCompatActivity() {

        private lateinit var auth: FirebaseAuth
        private lateinit var etEmail : EditText
        private lateinit var etPassword : EditText
        private lateinit var signinBtn : Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)


        auth = Firebase.auth

        etEmail = findViewById(R.id.emailLogin)
        etPassword  = findViewById(R.id.etLoginPassword)
        val tvSignup = findViewById<TextView>(R.id.tv_new_signup)



        signinBtn = findViewById(R.id.signInBtn)

        signinBtn.setOnClickListener {
            signIn()
        }

        tvSignup.setOnClickListener {
            val intent = Intent(this, SignupActivity::class.java)
            startActivity(intent)
        }

    }
        private fun signIn() {

            val email = etEmail.text.toString()
            val password = etPassword.text.toString()
            if (email.isEmpty()){
                etEmail.error = "Email is required"
                etEmail.requestFocus()
                return
            }
            if (password.isEmpty()){
                etPassword.error = "Password is required"
                etPassword.requestFocus()
                return
            }
            if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                etEmail.error = "Email is not valid"
                etEmail.requestFocus()
                return
            }

            if (password.length<8){
                etPassword.error = "Password must 8 character long"
                etPassword.requestFocus()
                return
            }
            if (password.isDigitsOnly()){
                etPassword.error = "Password must be mix of letters and numbers"
                etPassword.requestFocus()
                return
            }

    auth.signInWithEmailAndPassword(email, password)
    .addOnCompleteListener(this) { task ->
        if (task.isSuccessful) {

            // Sign in success, update UI with the signed-in user's information
            Log.d(TAG, "signInWithEmail:success")
            val user = auth.currentUser
            updateUI(user)
        } else {
            // If sign in fails, display a message to the user.
            Log.w(TAG, "signInWithEmail:failure", task.exception)
            Toast.makeText(baseContext, "Authentication failed.",
                Toast.LENGTH_SHORT).show()

        }
    }
}

    private fun updateUI(user: FirebaseUser?) {
        if (user!=null){
            val intent = Intent(this, MainActivity::class.java)
            intent.putExtra("name", user.displayName)
            intent.putExtra("email", user.email)
            startActivity(intent)

        }

    }

    }
