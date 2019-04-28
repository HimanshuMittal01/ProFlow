package com.example.proflow.registerlogin

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.widget.Toast
import com.example.proflow.R
import com.example.proflow.messages.LatestMessagesActivity
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        login_button_login.setOnClickListener {
            performLogin()
        }

        back_to_register_textview.setOnClickListener {
            finish()
        }
    }

    private fun performLogin() {
        val email = email_edittext_login.text.toString()
        val password = password_edittext_login.text.toString()

        if (email.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Please enter text in email/pw", Toast.LENGTH_SHORT).show()
            return
        }

        Log.d("Login", "Attempt login with email/pw $email/***")

        FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password)
            .addOnCompleteListener {
                if (!it.isSuccessful) return@addOnCompleteListener

                //else if successful
                val intent = Intent(this, LatestMessagesActivity::class.java)
                startActivity(intent)
                Log.d("Login", "Successfully login. Your uid is: ${it.result!!.user.uid}")
            }
            .addOnFailureListener {
                Log.d("Login", "Login not successful. Error: ${it.message}")
                Toast.makeText(this, "Login not successful. Error: ${it.message}", Toast.LENGTH_SHORT).show()
            }
    }
}