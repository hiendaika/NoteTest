package com.example.supernotes.coroutine

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.supernotes.R
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking

class TestFlowActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test_flow)
//        createFlowUsingFlowOf()
        createFlowUsingAsFlowExtemsions()
    }

    //Cac cach tao ra flow
    //1. FlowOf
    //public fun <T> flowOf(vararg elements: T): Flow<T>
    fun createFlowUsingFlowOf() = runBlocking {
        val data = flowOf("Hello", 3.14, 10)
        data.collect { value -> Log.d("createFlowUsingFlowOf", "$value") }
    }

    //2. asFlow extendsion function
    fun createFlowUsingAsFlowExtemsions() = runBlocking {
        val list = listOf(1, "Data", 3.14)
        val data = list.asFlow()
        data.collect { value ->
            Log.d("createFlowUsingAsFlow", "$value")
        }
    }
}