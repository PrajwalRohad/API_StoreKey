package com.example.api_storekey

import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)


        val store = Store.getInstance(applicationContext)

        findViewById<Button>(R.id.btn).setOnClickListener {
            val key = store.getKey()

            Log.d("KEY_TAG", "key: $key")
        }
    }
}