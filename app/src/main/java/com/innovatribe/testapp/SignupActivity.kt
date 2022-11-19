package com.innovatribe.testapp

import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase


class SignupActivity : AppCompatActivity()  {

    private lateinit var auth: FirebaseAuth

    lateinit var name: EditText
    private lateinit var etEmail : EditText
    private lateinit var etPassword : EditText
    private lateinit var signupBtn : Button



    override fun onCreate(savedInstanceState: Bundle?) {
        auth = Firebase.auth
        auth = FirebaseAuth.getInstance()

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)
         name = findViewById(R.id.name)
         etEmail = findViewById(R.id.email)
         etPassword  = findViewById(R.id.etPassword)
        val tvSignIn = findViewById<TextView>(R.id.not_new_login)


        signupBtn = findViewById(R.id.signupBtn)


        tvSignIn.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }


        signupBtn.setOnClickListener {
            signUp()
        }

    }
   private fun signUp() {

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

        auth.createUserWithEmailAndPassword(email,password)
            .addOnCompleteListener { task->
                if (task.isSuccessful){

                    val intent = Intent(this, MainActivity::class.java)
                    intent.putExtra("name",name.text)
                    intent.putExtra("email",email)
                    startActivity(intent)
                    Toast.makeText(this, "Account created successfully", Toast.LENGTH_SHORT).show()

                }else{
                    Toast.makeText(this, task.exception.toString(), Toast.LENGTH_SHORT).show()

                }
            }

    }


    }