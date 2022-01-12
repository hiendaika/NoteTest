package com.example.supernotes.coroutine

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.supernotes.R
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import java.io.IOException
import kotlin.system.measureTimeMillis

class TestActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test)
//        sayHello()
//        println("World!")

//        Module 5 Async Await
//        _5_assync_1()
//        sum2Number()
//        sum2NumberWithLazy()

//        Module 6 Coroutine Scope
//        testScope()
//        cancelParent()

//        MODULE 7: Exception
//            testException()
//        testSupervisorJob()
//        testSupervisorScope()

//        MODULE 8: FLOW
//        calculateTime()
//        coroutineVsSequence()
//        coroutineVsFlow()
//        testFlowIsColdStream()
        testFlowCancellation()
    }

    fun sayHello() = runBlocking {
        println("Hello")
        delay(5000)
    }

    //Async giup tra ve ket qua
    fun _5_assync_1() = runBlocking {
        val int: Deferred<Int> = async { printInt() }
        val string: Deferred<String> = async { return@async "HienDZ" }
        val unit: Deferred<Unit> = async { }
    }

    fun printInt(): Int {
        return 10
    }

    //VD tinh tong 2 so nhan duoc tu ket qua tra ve cua 2 function
    suspend fun printOne(): Int {
        delay(1000)
        return 10
    }

    suspend fun printTwo(): Int {
        delay(1000)
        return 12
    }

    fun sum2Number() = runBlocking {
        val time = measureTimeMillis {
            val one = async { printOne() }
            val two = async { printTwo() }
            Log.d("Module5", "Sum: ${one.await() + two.await()}")
        }
        Log.d("Module5", "Time: ${time}")
    }

    fun sum2NumberWithLazy() = runBlocking {
        val time = measureTimeMillis {
            val one = async(start = CoroutineStart.LAZY) { printOne() }
            val two = async(start = CoroutineStart.LAZY) { printTwo() }
//            one.start() // start thang one
//            two.start() // start thang two
            Log.d("Module5", "Sum: ${one.await() + two.await()}")
        }
        Log.d("Module5", "Time: ${time}")
    }


    //Module 6 Coroutine Scope
    //Custom Scope
    @Suppress("FunctionName")
    fun MainScope(): CoroutineScope = CoroutineScope(SupervisorJob() + Dispatchers.Main)

    //    Coroutine nested coroutine
    fun testScope() = runBlocking { // scope 1
        launch {       // coroutine 1
            delay(200L)
            Log.d("Module6", "Task from runBlocking")   // line code 1
        }

        coroutineScope { // coroutine 2   // scope 2
            launch {   // coroutine 3
                delay(500L)
                Log.d("Module6", "testScope:Task from nested launch") // line code 2
            }

            delay(100L)
            Log.d("Module6", "Task from coroutine scope") // line code 3
        }

        Log.d("Module6", "Coroutine scope is over") // line code 4
    }

    //Cancel parent coroutine
    fun cancelParent() = runBlocking<Unit> {
        val request = launch {
            launch {
                delay(100)
                Log.d("Module6", "job2: I am a child of the request coroutine")   // line code 1
                delay(1000)
                Log.d(
                    "Module6",
                    "job2: I will not execute this line if my parent request is cancelled"
                ) // line code 2
            }
        }
        delay(500)
        request.cancel() // cancel processing of the request
        delay(1000)
        Log.d("Module6", "main: Who has survived request cancellation?") // line code 3
    }

    //    GlobalScope
    fun testGlobalScope() = runBlocking<Unit> {
        val request = launch {
            // it spawns two other jobs, one with GlobalScope
            GlobalScope.launch {
                Log.d("Module6", "job1: GlobalScope and execute independently!")
                delay(1000)
                Log.d(
                    "Module6",
                    "job1: I am not affected by cancellation"
                )  // line code 1 này vẫn được in ra mặc dù bị delay 1000ms
            }
            // and the other inherits the parent context
            launch {
                delay(100)
                Log.d("Module6", "job2: I am a child of the request coroutine")
                delay(1000)
                Log.d(
                    "Module6",
                    "job2: I will not execute this line if my parent request is cancelled"
                )
            }
        }
        delay(500)
        request.cancel() // cancel processing of the request
        delay(1000) // delay a second to see what happens
        Log.d("Module6", "main: Who has survived request cancellation?")
    }


    //    MODULE 7: Exception
    // Test exception
    fun testException() = runBlocking {
        GlobalScope.launch {
            try {
                Log.d("Module7", "Throwing exception from launch")
                throw IndexOutOfBoundsException()
                Log.d("Module7", "Unreached")
            } catch (e: IndexOutOfBoundsException) {
                Log.d("Module7", "Caught IndexOutOfBoundsException")
            }
        }

        val deferred = GlobalScope.async {
            Log.d("Module7", "Throwing exception from async")
            throw ArithmeticException()
            Log.d("Module7", "Unreached")
        }
        try {
            deferred.await()
            Log.d("Module7", "Unreached")
        } catch (e: ArithmeticException) {
            Log.d("Module7", "Caught ArithmeticException")
        }
    }

    //Tong hop nhieu exception
    fun totalException() = runBlocking {
        val handler = CoroutineExceptionHandler { _, exception ->
            Log.d(
                "Module7",
                "Caught $exception with suppressed ${exception.suppressed.contentToString()}"
            )
        }
        val job = GlobalScope.launch(handler) {
            launch {
                try {
                    delay(Long.MAX_VALUE) // delay vô hạn
                } finally {
                    throw ArithmeticException()
                }
            }
            launch {
                try {
                    delay(Long.MAX_VALUE) // delay vô hạn
                } finally {
                    throw IndexOutOfBoundsException()
                }
            }
            launch {
                delay(100)
                throw IOException()
            }
            delay(Long.MAX_VALUE)
        }
        job.join()
    }

    //    Supervisor Job
    fun testSupervisorJob() = runBlocking {
        val supervisor = SupervisorJob()
        with(CoroutineScope(coroutineContext + supervisor)) {
            // launch the first child -- its exception is ignored for this example (don<t do this in practice!)
            val firstChild = launch(CoroutineExceptionHandler { _, _ -> }) {
                Log.d("Module7", "First child is failing")
                throw AssertionError("First child is cancelled")
            }
            // launch the second child
            val secondChild = launch {
                firstChild.join()
                // Cancellation of the first child is not propagated to the second child
                Log.d(
                    "Module7",
                    "First child is cancelled: ${firstChild.isCancelled}, but second one is still active"
                )
                try {
                    delay(Long.MAX_VALUE)
                } finally {
                    // But cancellation of the supervisor is propagated
                    Log.d("Module7", "Second child is cancelled because supervisor is cancelled")
                }
            }
            // wait until the first child fails & completes
            firstChild.join()
            Log.d("Module7", "Cancelling supervisor")
            supervisor.cancel()
            secondChild.join()
        }
    }

    //    Supervisor Scope
    fun testSupervisorScope() = runBlocking {
        val handler = CoroutineExceptionHandler { _, exception ->
            Log.d("Module7", "Caught $exception")
        }
        supervisorScope {
            val first = launch(handler) {
                Log.d("Module7", "Child throws an exception")
                throw AssertionError()
            }
            val second = launch {
                delay(100)
                Log.d("Module7", "Scope is completing")
            }
        }
        Log.d("Module7", "Scope is completed")
    }


    //    MODULE 8: FLOW
    /*
    * So sanh Collection, Sequences, Flow bang cach xay dung function foo
    * Do thoi gian thuc thi ham foo.
    * */
//    Su dung collection
    suspend fun fooCollection(): List<Int> {
        val list = mutableListOf<Int>()
        for (i in 1..3) {
            delay(1000)
            list.add(i)
        }

        return list
    }

    //Su dung Sequences
    fun fooSequences(): Sequence<Int> = sequence {
        for (i in 1..3) {
            Thread.sleep(1000)
            yield(i)
        }
    }

    //Su dung Flow
    fun fooFlow(): Flow<Int> = flow {
        //Flow builder giup tao ra 1 doi tuong flow
        // Code ben trong la suspend nen ta co the goi suspend function trong nay
        for (i in 1..3) {
            Log.d("Module8", "Flow started")
            delay(1000)
            //Emit cac gia tri tu flow
            emit(i)
        }
    }

    //    tinh toan thoi gian thuc hien
    fun calculateTime() = runBlocking {
        val time = measureTimeMillis {
//            fooCollection().forEach { value -> Log.d("Module8", "Time: $value") }
//            fooSequences().forEach { value -> Log.d("Module8", "Time: $value") }
            fooFlow().collect { value -> Log.d("Module8", "Time: $value") }
        }
        Log.d("Module8", "calculateTime: $time")
    }


    //Flow vs Sequence
    //FooSequence va coroutine se cung chay //
    fun coroutineVsSequence() = runBlocking {
        // Chay 1 coroutine de kiem tra xem mainthread co bi block hay k
        launch {
            //Kiem tra thread dang chay
            Log.d("Module8", Thread.currentThread().name)
            for (k in 1..3) {
                delay(1000)
                Log.d("Module8", "I<m blocked $k")
            }
        }
        val time = measureTimeMillis {
            fooSequences().forEach { value -> Log.d("Module8", "$value") }
        }
        Log.d("Module8", "$time s")
    }

    fun coroutineVsFlow() = runBlocking {
        //Chay coroutine de kiem tra main thread co bi block hay k
        launch {
            Log.d("Module8", Thread.currentThread().name)
            for (i in 1..3) {
                delay(1000)
                Log.d("Module8", "I'm blocked $i")
            }
        }
        //Collect flow
        val time = measureTimeMillis {
            fooFlow().collect { value ->
                Log.d("Module8", "$value")
            }
        }
        Log.d("Module8", "$time s")
    }

    //Flow la nguon du lieu lanh
    // Khi goi ham collect thi flow moi dc chay
    fun flowIsColdStream(): Flow<Int> = flow {
        Log.d("Module8", "Flow started")
        for (i in 1..3) {
            delay(1000)
            emit(i)
        }

    }

    fun testFlowIsColdStream() = runBlocking {
        Log.d("Module8", "Test Flow is Cold Stream....")
        var flowIsColdStream: Flow<Int> = flowIsColdStream()
        Log.d("Module8", "Flow is Collecting....")
        flowIsColdStream.collect { value -> Log.d("Module8", "Value: $value") }
        Log.d("Module8", "Calling Flow Again...")
        flowIsColdStream.collect { value -> Log.d("Module8", "Value: $value") }
    }

    //VD Flow cancellation.
    fun fooFlowCancel(): Flow<Int> = flow {
        for (i in 1..3) {
            delay(2000)
            Log.d("Module8", "Emit $i")
            emit(i)
        }
    }

    fun testFlowCancellation() = runBlocking {
        withTimeoutOrNull(5000) {
            //Timeout sau 5s
            fooFlowCancel().collect { value ->
                Log.d("Module8", "$value")
            }
        }
        Log.d("Module8", "Done")
    }
}