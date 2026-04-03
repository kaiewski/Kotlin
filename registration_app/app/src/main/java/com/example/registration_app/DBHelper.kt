package com.example.registration_app

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.content.ContentValues

class DBHelper (val context: Context, val factory: SQLiteDatabase.CursorFactory?) :
    SQLiteOpenHelper(context, "appDB", factory, 1){
    override fun onCreate(db: SQLiteDatabase?) {
        val query = "CREATE TABLE Users(id INTEGER PRIMARY KEY AUTOINCREMENT DEFAULT NULL, login TEXT, email TEXT, password TEXT)" // Создание таблицы
        db!!.execSQL(query) // Correct database processing (!! - no null values)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db!!.execSQL("DROP TABLE IF EXISTS Users") // Clear database if Users
        onCreate(db) // Call onCreate() again to create a new database
    }

    fun addUser(user: User){
        val values = ContentValues() // create of a dictionary of primitive meanings
        values.put("login", user.login)
        values.put("email", user.email)
        values.put("password", user.password)

        val db = this.writableDatabase // Request to add data to database
        db.insert("Users", null, values) // Add information to the Users table
        db.close() // close database
    }

    // Retrieve a user from the database; Boolean -> returns True or False
    fun getUser(login: String, pass: String): Boolean{
        val db = this.readableDatabase // request to read our database
        val selectionArgs = arrayOf(login, pass) // create an array to store the username and password
        val result = db.rawQuery("SELECT * FROM Users WHERE login = ? AND password = ?", selectionArgs)
        return result.moveToFirst() // return True or False depending on the result
    }
}