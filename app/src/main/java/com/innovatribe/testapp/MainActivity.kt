package com.innovatribe.testapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity() {

    private lateinit var mAuth: FirebaseAuth


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        mAuth = FirebaseAuth.getInstance()

        val logoutBtn = findViewById<Button>(R.id.logout)
        val tvName = findViewById<TextView>(R.id.name)
        val tvEmail = findViewById<TextView>(R.id.email)
        val imgView = findViewById<ImageView>(R.id.imgView)

        tvName.text = intent.getStringExtra("name")
        tvEmail.text = intent.getStringExtra("email")
        val picUrl = intent.getStringExtra("picUrl")

        Glide.with(this)
            .load(picUrl)
            .into(imgView);



        logoutBtn.setOnClickListener {
            mAuth.signOut()
            startActivity(Intent(this,AuthMain::class.java))
        }
    }
}