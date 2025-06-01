package com.example.railfalldetector

private lateinit var binding: ActivityLoginBinding
package com.example.railfalldetector

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
private lateinit var binding: ActivityLoginBinding
binding = ActivityMainBinding.inflate(layoutInflater)
setContentView(binding.root)
import com.example.railfalldetector.databinding.ActivityLoginBinding
import com.google.firebase.auth.FirebaseAuth

private lateinit var binding: ActivityLoginBinding
binding = ActivityMainBinding.inflate(layoutInflater)
setContentView(binding.root)
import com.example.railfalldetector.databinding.ActivityLoginBinding
import android.os.Bundle
class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
private lateinit var binding: ActivityLoginBinding
binding = ActivityMainBinding.inflate(layoutInflater)
setContentView(binding.root)
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()

private lateinit var binding: ActivityLoginBinding
binding = ActivityMainBinding.inflate(layoutInflater)
setContentView(binding.root)
        binding.btnLogin.setOnClickListener {
private lateinit var binding: ActivityLoginBinding
binding = ActivityMainBinding.inflate(layoutInflater)
setContentView(binding.root)
            val email = binding.etEmail.text.toString()
private lateinit var binding: ActivityLoginBinding
binding = ActivityMainBinding.inflate(layoutInflater)
setContentView(binding.root)
            val password = binding.etPassword.text.toString()
            auth.signInWithEmailAndPassword(email, password).addOnCompleteListener {
                if (it.isSuccessful) {
private lateinit var binding: ActivityLoginBinding
binding = ActivityMainBinding.inflate(layoutInflater)
setContentView(binding.root)
import com.example.railfalldetector.databinding.ActivityLoginBinding
import android.os.Bundle
                    startActivity(Intent(this, MainActivity::class.java))
                    finish()
                } else {
                    Toast.makeText(this, "Giriş başarısız: ${it.exception?.message}", Toast.LENGTH_LONG).show()
                }
            }
        }

private lateinit var binding: ActivityLoginBinding
binding = ActivityMainBinding.inflate(layoutInflater)
setContentView(binding.root)
        binding.btnRegister.setOnClickListener {
private lateinit var binding: ActivityLoginBinding
binding = ActivityMainBinding.inflate(layoutInflater)
setContentView(binding.root)
            val email = binding.etEmail.text.toString()
private lateinit var binding: ActivityLoginBinding
binding = ActivityMainBinding.inflate(layoutInflater)
setContentView(binding.root)
            val password = binding.etPassword.text.toString()
            auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener {
                if (it.isSuccessful) {
                    Toast.makeText(this, "Kayıt başarılı", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this, "Kayıt başarısız: ${it.exception?.message}", Toast.LENGTH_LONG).show()
                }
            }
        }
    }
}
