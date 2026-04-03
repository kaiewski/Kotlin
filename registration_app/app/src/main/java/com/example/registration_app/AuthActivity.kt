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

class AuthActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_auth)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val userLogin: EditText = findViewById(R.id.userLoginAuth)
        val userPassword: EditText = findViewById(R.id.userPasswordAuth)
        val buttonAuth: Button = findViewById(R.id.buttonAuth)
        val linkToReg: Button = findViewById(R.id.linkToReg)

        linkToReg.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java) // specify the page to navigate to
            startActivity(intent) // Navigate to a page using a variable intent
        }

        buttonAuth.setOnClickListener {
            val login = userLogin.text.toString().trim()
            val password = userPassword.text.toString().trim()

            if (login == "" || password == "") {
                Toast.makeText(this, "Заполните все поля!", Toast.LENGTH_LONG)
            }
            else {
                val db = DBHelper(this, null)
                val isAuth = db.getUser(login, password)

                if (isAuth) {
                    Toast.makeText(this, "$login успешно авторизовано!", Toast.LENGTH_LONG)
                    userLogin.text.clear()
                    userPassword.text.clear()
                }
                else {
                    Toast.makeText(this, "$login не найден!!", Toast.LENGTH_LONG)
                }
            }
        }
    }
}