package com.example.biometricsample

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.biometric.BiometricPrompt
import kotlinx.android.synthetic.main.activity_main.*
import java.util.concurrent.Executors

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.addFlags(View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION or View.SYSTEM_UI_FLAG_LAYOUT_STABLE)
        setContentView(R.layout.activity_main)

        val executor = Executors.newSingleThreadExecutor()
        val biometricPrompt = BiometricPrompt(this, executor, object : BiometricPrompt.AuthenticationCallback() {
            override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {
                super.onAuthenticationSucceeded(result)

                val mainHandler = Handler(Looper.getMainLooper())
                val myRunnable = Runnable {
                    AlertDialog.Builder(this@MainActivity)
                        .setTitle("Success")
                        .show()
                }
                mainHandler.post(myRunnable)


            }

            override fun onAuthenticationError(errorCode: Int, errString: CharSequence) {
                super.onAuthenticationError(errorCode, errString)

                val mainHandler = Handler(Looper.getMainLooper())
                val myRunnable = Runnable {
                    AlertDialog.Builder(this@MainActivity)
                        .setTitle("Failed")
                        .show()
                }
                mainHandler.post(myRunnable)
            }
        })

        val biometricPromptDialog = BiometricPrompt.PromptInfo.Builder()
            .setTitle("Let us verify you.")
            .setNegativeButtonText("Cancel")
            .build()

        authenticate.setOnClickListener {
            biometricPrompt.authenticate(biometricPromptDialog)
        }
    }
}
