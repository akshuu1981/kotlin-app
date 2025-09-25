package com.akshat.mykotlinapp.activity

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.akshat.mykotlinapp.BlogPreferences
import com.akshat.mykotlinapp.activity.MainActivity
import com.akshat.mykotlinapp.databinding.ActivityLoginBinding
import com.google.android.material.textfield.TextInputLayout

class LoginActivity: AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding // 1
    private val preferences: BlogPreferences by lazy {
        BlogPreferences(this)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (preferences.isLoggedIn()) {
            startMainActivity()
            finish()
            return
        }

        binding = ActivityLoginBinding.inflate(layoutInflater) // 1
        setContentView(binding.root) // 2

        binding.textUsernameLayout.editText
            ?.addTextChangedListener(createTextWatcher(binding.textUsernameLayout))

        binding.textPasswordInput.editText
            ?.addTextChangedListener(createTextWatcher(binding.textPasswordInput))

        binding.loginButton.setOnClickListener {
            val username = binding.loginInput.text.toString()
            val password = binding.passwordInput.text.toString()
            if (username.isEmpty()) {
                binding.textUsernameLayout.error = "Username must not be empty"
                return@setOnClickListener
            }
            if (password.isEmpty()) {
                binding.textPasswordInput.error = "Password must not be empty"
                return@setOnClickListener

            }
//            if (username != "admin" && password != "admin") {
//                showErrorDialog()
//                return@setOnClickListener
//            }
                // Perform login logic here
                Log.d("LoginActivity", "Username: $username, Password: is masked")
            performLogin()

        }
    }

    private fun performLogin() {
        preferences.setLoggedIn(true)
        binding.progress.visibility = View.VISIBLE
        binding.loginButton.isEnabled = false
        binding.textUsernameLayout.isEnabled = false
        binding.textPasswordInput.isEnabled = false

        Handler(mainLooper).postDelayed({
            binding.progress.visibility = View.GONE
            binding.loginButton.isEnabled = true
            binding.textUsernameLayout.isEnabled = true
            binding.textPasswordInput.isEnabled = true
            startMainActivity()
        }, 2000)
    }

    private fun startMainActivity() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun showErrorDialog() {
        AlertDialog.Builder(this)
            .setTitle("Login Failed")
            .setMessage("Username or password is not correct. Please try again.")
            .setPositiveButton("OK") { dialog, which -> dialog.dismiss() }
            .show()
    }

    private fun createTextWatcher(textInput: TextInputLayout): TextWatcher? {
        return object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence,
                                           start: Int, count: Int, after: Int) {
                // not needed
            }

            override fun onTextChanged(s: CharSequence,
                                       start: Int, before: Int, count: Int) {
                textInput.error = null
            }

            override fun afterTextChanged(s: Editable) {
                // not needed
            }
        }
    }
}