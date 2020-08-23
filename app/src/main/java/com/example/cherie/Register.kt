package com.example.cherie

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_register.*

private lateinit var auth: FirebaseAuth

class Register : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        auth = FirebaseAuth.getInstance()

        registerText.setOnClickListener{
            startActivity(Intent(this, Login::class.java))
            finish()
        }

        btnRegister.setOnClickListener{
            registerUser()
        }
    }

    private fun registerUser(){

        if(full_name.text.toString().isEmpty()){
            full_name.error = "Please enter Your Name"
            full_name.requestFocus()
            return
        }

        if(email.text.toString().isEmpty()){
            email.error = "Please enter email"
            email.requestFocus()
            return
        }

        if(!Patterns.EMAIL_ADDRESS.matcher(email.text.toString()).matches()){
            email.error = "Please Enter Valid Email"
            email.requestFocus()
            return
        }

        if(password.text.toString().isEmpty()){
            password.error = "Please Enter Password"
            password.requestFocus()
            return
        }

        if(phone.text.toString().isEmpty()){
            phone.error = "Please Enter Phone"
            phone.requestFocus()
            return
        }

        auth.createUserWithEmailAndPassword(email.text.toString(),password.text.toString())
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    val user = auth.currentUser
                    user!!.sendEmailVerification()
                        .addOnCompleteListener { task ->
                            if (task.isSuccessful) {
                                startActivity(Intent(this, Login::class.java))
                                finish()
                            }
                        }

                } else {
                    Toast.makeText(baseContext, "Register failed. Please try again after several time",
                        Toast.LENGTH_SHORT).show()
                }

            }

    }


}