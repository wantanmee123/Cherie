package com.example.cherie

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_login.email
import kotlinx.android.synthetic.main.activity_login.password

private lateinit var auth: FirebaseAuth

class Login : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        auth = FirebaseAuth.getInstance()

        loginText.setOnClickListener{
            startActivity(Intent(this,Register::class.java))
            finish()
        }

        btnLogin.setOnClickListener{
            toLogin()
        }


    }

    private fun toLogin(){
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

        auth.signInWithEmailAndPassword(email.text.toString(), password.text.toString())
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    val user = auth.currentUser
                    updateUI(user)
                } else {

                    updateUI(null)
                }

            }

    }

    public override fun onStart() {
        super.onStart()
        // Check if user is signed in (non-null) and update UI accordingly.
        val currentUser = auth.currentUser
        updateUI(currentUser)
    }

    private fun updateUI(currentUser: FirebaseUser?){

        if(currentUser != null){
            if(currentUser.isEmailVerified){
                startActivity(Intent(this, Home::class.java))
                finish()
            }
            else{
                Toast.makeText(baseContext, "Please verify your email",
                    Toast.LENGTH_SHORT).show()
            }

        }

        if(currentUser == null){
            Toast.makeText(baseContext, "Missing field(s)/Login failed",
                Toast.LENGTH_SHORT).show()
        }
    }


}