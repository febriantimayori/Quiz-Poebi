package com.example.quizpubie

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class HomeActivity : AppCompatActivity(), View.OnClickListener{

//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_home)
//    }
//}

    private var mDatabase: DatabaseReference? = null
    private var mAuth: FirebaseAuth? = null
    private var edtEmail: EditText? = null
    private var edtPass: EditText? = null
    private var btnMasuk: Button? = null
    private var btnDaftar: Button? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        mDatabase = FirebaseDatabase.getInstance().reference
        mAuth = FirebaseAuth.getInstance()

//        edtEmail = findViewById(R.id.txt_username)
//        edtPass = findViewById(R.id.txt_password)
//        btnMasuk = findViewById(R.id.btn_login)
//        btnDaftar = findViewById(R.id.btn_register)

        btnMasuk!!.setOnClickListener(this)
        btnDaftar!!.setOnClickListener(this)
    }

    private fun signIn() {
        Log.d(TAG, "signIn")
        if (!validateForm()) {
            return
        }

        val email = edtEmail!!.text.toString()
        val pass = edtPass!!.text.toString()

        mAuth!!.signInWithEmailAndPassword(email, pass).addOnCompleteListener(this) { task ->
            Log.d(TAG, "signIn:onComplete:" + task.isSuccessful)

            if (task.isSuccessful) {
                onAuthSuccess(task.result!!.user!!)
            } else {
                Toast.makeText(this@HomeActivity, "Sign in Failed!", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun signUp() {
        Log.d(TAG, "signUp")
        if (!validateForm()) {
            return
        }

        val email = edtEmail!!.text.toString()
        val pass = edtPass!!.text.toString()

        mAuth!!.createUserWithEmailAndPassword(email, pass).addOnCompleteListener(this) { task ->
            Log.d(TAG, "createUser:onComplete:" + task.isSuccessful)
            if (task.isSuccessful) {
                onAuthSuccess(task.result!!.user!!)
            } else {
                Toast.makeText(this@HomeActivity, "Sign up Failed!", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun onAuthSuccess(user: FirebaseUser) {
        val username = usernameFromEmail(user.email!!)
        writeNewAdmin(user.uid, username, user.email!!)

        startActivity(Intent(this@HomeActivity, MainActivity::class.java))
        finish()
    }

    private fun usernameFromEmail(email: String): String {
        return if (email.contains("@")) {
            email.split("@".toRegex()).dropLastWhile({ it.isEmpty() }).toTypedArray()[0]
        } else {
            email
        }
    }

    private fun validateForm(): Boolean {
        val res = true
        if (TextUtils.isEmpty(edtPass!!.text.toString())) {
            edtPass!!.error = "Required!"
        } else {
            edtPass!!.error = null
        }

        return res
    }

    private fun writeNewAdmin(userId: String, name: String, email: String) {
        val admin = Admin(name, email)

        mDatabase!!.child("admins").child(userId).setValue(admin)
    }

    override fun onClick(v: View) {
        val i = v.id
        if (i == R.id.btn_login) {
            signIn()
        } else if (i == R.id.btn_register) {
            signUp()
        }
    }

    companion object {

        private val TAG = "LoginActivity"
    }
}

