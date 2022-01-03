package com.example.supernotes.coroutine

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.supernotes.R
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking

class TestActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test)
        sayHello()
        println("World!")
    }

    fun sayHello() = runBlocking {
        println("Hello")
        delay(5000)
    }
}