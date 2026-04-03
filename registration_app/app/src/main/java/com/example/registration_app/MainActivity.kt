package com.example.registration_app

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Get all values from objects on page
        val userLogin: EditText = findViewById(R.id.userLogin)
        val userEmail: EditText = findViewById(R.id.userMail)
        val userPassword: EditText = findViewById(R.id.userPassword)
        val buttonReg: Button = findViewById(R.id.buttonReg)
        val linkToAuth: Button = findViewById(R.id.linkToAuth)

        // Handle click on button "Log in"
        linkToAuth.setOnClickListener {
            val intent = Intent(this, AuthActivity::class.java)
            startActivity(intent)
        }

        //  Handle click on text button Registration
        buttonReg.setOnClickListener {
            // trim() - like strip() in python

            val login = userLogin.text.toString().trim()
            val email = userEmail.text.toString().trim()
            val password = userPassword.text.toString().trim()

            if (login == "" || email == "" || password == "")
                Toast.makeText(this, "Заполните все поля!", Toast.LENGTH_LONG).show()

            else {
                val user = User(login, email, password) // create an new User
                val db = DBHelper(this, null)  // create an Data Base class
                db.addUser(user) // add new user to the Data Base
                Toast.makeText(this, "Пользотвель $login добавлен!", Toast.LENGTH_LONG).show()

                // clear input fields
                userLogin.text.clear()
                userEmail.text.clear()
                userPassword.text.clear()
            }
        }
    }
}