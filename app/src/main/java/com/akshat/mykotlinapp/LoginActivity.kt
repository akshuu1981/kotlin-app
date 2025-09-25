package com.akshat.mykotlinapp

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.akshat.mykotlinapp.databinding.ActivityLoginBinding
import com.google.android.material.textfield.TextInputLayout

class LoginActivity: AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding // 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

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
            }
            if (password.isEmpty()) {
                binding.textPasswordInput.error = "Password must not be empty"
            }
            // Perform login logic here
            Log.d("LoginActivity", "Username: $username, Password: is masked")
        }
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