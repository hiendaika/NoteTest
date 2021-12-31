# NoteTest
I. Gioi thieu coroutine va lap trinh bat dong bo.
1. Dat van de
- Làm thế nào để ứng dụng không bị block UI, tắc nghẽn khiến cho user ko thể thao tác tiếp tục được.
  Thực tế việc này rất dễ xảy ra khi chạy 1 tác vụ nặng trên main thread.
- De giai quyet van de tren ta su dung ky thuat lap trinh bat dong bo:
+ Threading.
+ Thread + Callback/AsycnTask/Handler.
+ Reactive Extension (Rx).
+ Coroutines.

2. Mot so giai phai xu ly bat dong bo.
   a. Threading:
- Thuc hien cac tac vu nang trong 1 thread rieng k phai la mainthread
  Exp:
  thread(true) {
  executeLongTask()
  }
- Nhuoc diem khi su dung thread:
+ Chi phi cho viec tao 1 thread kha dat.
+ Lam dung thread se anh huong den hieu suat cua ung dung, trai nghiem nguoi dung.
+ So thread la huu han, khi so thread da dc dung da het, trong khi can tao 1 thread moi de
  thuc hien cac tac vu khac => dieu nay se gay ra hien tuong tac nghen co chai.
+ Kho su dung thread, debug kho, deadlock va race condition de gap phai neu khong nam ro thread.
+ De giao diem dc giua thread va UI thread ta can ket hop voi callback.

b. Thread + Callbacks / AsyncTask/ Handler.
- Exp su dung callback trong kotlin:
  override fun onCreate(savedInstanceState: Bundle?) {
  super.onCreate(savedInstanceState)
  setContentView(R.layout.activity_main)
  thread(true) {
  executeLongTask { taskDone ->
  textViewTaskName.text = taskDone
  }
  }
  }

  private fun executeLongTask(taskDone: (name: String) -> Unit) {
  taskDone.invoke("Viblo Report")
  }
+ Code se phuc tap hon neu ta su dung cac doan ma callback long nhau
  fun register(newUser: User) {
  val username = newUser.getUsername()
  val password = newUser.getPassword()

        api.register(newUser, object : Callback<Boolean>() {
            fun onResponse(success: Boolean) {
                if (success) {
                    api.login(AuthData(username, password), object : Callback<Token>() {
                        fun onResponse(token: Token) {
                            api.getUser(token, object : Callback<UserDetail>() {
                                fun onResponse(userDetail: UserDetail) {
                                    // cuối cùng cũng đến Tây Thiên, get được userDetail rồi =))
                                }
                            })
                        }
                    })
                }
            }
        })
  }

=> Code viet theo dang nay se mat tham my va kha roi.
Co the viet don gian hon nhu sau

    fun register(newUser: User) {
        val username = newUser.getUsername()
        val password = newUser.getPassword()

        api.register(newUser, { success ->
            if (success) {
                login(username, password)
            }
        })
    }

    private fun login(username: String, password: String) {
        api.login(AuthData(username, password), { token -> getUserDetail(token) })
    }

    private fun getUserDetail(token: Token) {
        api.getUser(token, { userDetail ->
            // get được userDetail
        })
    }
=> De gon dep hon ta co the su dung Rx.

c. Rx
- Voi y/c bai toan tren khi su dung Rx operator thi code se ngan gon nhu sau.
  fun register(newUser: User) {
  val username = newUser.getUsername()
  val password = newUser.getPassword()

        api.register(newUser)
            .filter({ success -> success })
            .flatMap({ success -> api.login(AuthData(username, password)) })
            .flatMap({ token -> api.getUser(token) })
            .subscribe({ userDetails ->
                // get được userDetail
            })
  }


II. Xay dung ung dung dau tien voi coroutine trong kotlin

3. Blocking vs non-blocking / Normal function vs suspend function
a. Blocking:
   - Vi du doan code su dung normal function:
     fun functionA() { println("in ra A") }
     fun functionB() { println("in ra B") }
     fun main() {
         // chạy functionA và functionB
         functionA()
         functionB()
     }
     
   - Phan tich doan code tren:
    + FunctionA, functionB duoc goi trong main 
    + Mainthread se lan luot chay functionA xong sau do se chay functionB.
   + Cac dong lenh duoc chay 1 cach tuan tu tu tren xuong duoi. Nghia la cac dong lenh ben duoi phai cho cho den khi cac 
    dong lenh phia tren duoc thuc thi xong.
+ Neu cac thao tac lenh phia truoc ma thuc thi mat nhieu thoi gian nhu xu ly du lieu I/O hoac lam viec voi Network thi no se tro thanh
can tro voi cac lenh thuc thi di sau, mac du logic cua chung k lien quan gi den nhau (hoan toan co the xu ly doc lap).
+ Vi du ta co 2 function get thong tin may va get tat ca cac video trong may:
  fun main() {
      getVideos() // Giả sử hàm này chạy mất hết 2 phút
      getInfo() // phải đợi hàm getVideos chạy xong mới được chạy trong khi hàm này chẳng liên quan gì đến getVideos
      updateUiInfo()
  }
=> Nhu vay ng dung can phai doi it nhat 2 phut moi co the lay duoc thong tin va update. 

b. Non-blocking
- Cac dong lenh k nhat thiet luc nao cung phai thuc hien tuan tu(sequential) va dong bo(synchronous) voi nhau.
- Cac dong lenh phia sau duoc goi ngay sau khi cac dong lenh phia truoc duoc goi ma khong can doi cho toi khi cac dong lenh phia truoc chay xong.
- De thuc hien Non-blocking co the su dung nhieu cach khac nhau nhung co ban van la dung nhieu thread khac nhau trong cung 1 process, hoac 
tham chi co the nhieu process khac nhau de thuc hien.
=> Coroutine co the chay non-blocking. Non-blocking nhung k can phai dua vao viec tao nhieu thread. Trong 1 thread co the chay nhieu coroutine
cung co the chay mo hinh non-blocking.
  
c. Suspend function
- Suspend function co kha nang ngung hay gian doan viec thuc thi 1 lat(trang thai ngung la trang thai suspend) va co the tiep tuc thuc thi
khi can thiet.
- Suspend function duoc danh dau bang tu khoa suspend:
  suspend fun sayHello() {
      delay(1000L)
      println("Hello!")
  }
- Chi co the goi suspend function ben trong 1 suspend function khac. 
+ delay la 1 suspend function va duoc goi trong 1 suspend function la sayHello.

4. Run blocking voi Coroutine.
- De coroutine co the chay blocking ta su dung tu khoa runBlocking{} - tuong tu nhu block launch{}. Ben trong runBlocking{}
cung la 1 coroutine dc ta va chay.
  runBlocking { // chạy một coroutine
      println("Hello")
      delay(5000)
  }
  println("World")
  => Ket qua in ra
  Output: 22:00:20 I/System.out: Hello
  22:00:25 I/System.out: World
  Tu World dc in sau tu Hello khoang 5s <=> dieu nay nghia la mainthread da bi block trong khoang 5s, sau khi delay 5s no moi chay xuong dong 
lenh in ra tu world.
Co the viet lai nhu sau:
  private fun main() = runBlocking {
    println("Hello")
    delay(5000)
  }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    main()
    println("World")
  }

5. Coroutine la thread nho gon.
- VD: Chung minh coroutine nhe so voi thread. Tao function chay 100000 coroutine // va ta tinh tong thoi gian chay xong
  val time = measureTimeMillis { main() }
  Log.d("hehehe", "time = $time ms")
  fun main() = runBlocking {
    repeat(100_000) { // launch 100_000 coroutines
      launch {
        Log.d("hehehe", "hello")
      }
    }
  }
=> Output la 7129 ms

III. Bai 3 Coroutine Context va Dispatcher
1. Coroutine Context.
- Moi coroutine trong Kotlin deu co 1 context duoc the hien bang 1 instance cua interface CoroutineContext. Context nay la 
1 tap hop cac element cau hinh cho coroutine.
a. Element 
- Cac loai Element trong coroutine gom:
* Job: Nam giu thong tin ve lifecycle cua coroutine.
* Dispatcher: Quyet ding thread ma coroutine se chay tren do. Co cac loai dispatcher sau:
- Dispatchers.Main: Chay tren UI thread.
- Dispatchers.IO: chay tren background thread cua thread pool. Thuong dung khi read, write files, database, Networking.
- Dispatchers.Default: Chay tren background thread cua thread pool thuong dung khi sorting list, parse Json, DiffUtils.
- newSingleThreadContext(""name_thread"): Chay tren 1 thread do ta tu dat ten.
- newFixThreadPoolContext(3,"name_thread"): Su dung 3 thread trong shared background thread pool
=> Job va Dispatchers la 2 element chinh trong CoroutineContext. Ngoai ra con co 1 so element khac nhu:
- CoroutineName("name"): Dat ten cho coroutine.
- NonCancellable: K the cancel ke ca khi goi cancel coroutine

b. Toan tu plus(+) de them cac element vao coroutineContext.
-  Su dung toan tu + de them cac element cho coroutineContext nhu sau:
   // set context khi sử dụng runBlocking { } để start coroutine
   runBlocking(Dispatchers.IO + Job()) {
   }

// hoặc set context khi sử dụng launch { } để start coroutine
  GlobalScope.launch(newSingleThreadContext("demo_thread") + CoroutineName("demo_2") + NonCancellable) {
  }

c. Default Context
- Neu k set coroutine context cho coroutine thi mac dinh no se nhan Dispatchers.Default lam dispatcher va tao ra 1 Job() de quan ly 1 coroutine
  GlobalScope.launch {
    // tương đương với GlobalScope.launch (Dispatchers.Default + Job()) { }
  }

d. Get coroutine context qua bien coroutineContext
- get coroutine context thong qua property: coroutineContext trong moi coroutine.
  fun main() = runBlocking<Unit> {
    println("My context is: $coroutineContext")
  }
- Them cac element vao coroutineContext bang cach su dung toan tu cong +
  fun main() = runBlocking<Unit> {
    println("A context with name: ${coroutineContext + CoroutineName("test")}")
  }
  
2. Ham withContext
- La 1 suspend function cho phep coroutine chay code trong block vs 1 context cu the do ta quy dinh.
  fun main() {
  newSingleThreadContext("thread1").use { ctx1 ->
  // tạo một context là ctx1 chứ chưa launch coroutine.
  // ctx1 sẽ có 1 element là dispatcher quyết định coroutine sẽ chạy trên 1 thread tên là thread1
  println("ctx1 - ${Thread.currentThread().name}")

   		newSingleThreadContext("thread2").use { ctx2 ->
             // tạo một context là ctx2 chứ vẫn chưa launch coroutine
             // ctx2 sẽ có 1 element là dispatcher quyết định coroutine sẽ chạy trên 1 thread tên là thread2
       		println("ctx2 - ${Thread.currentThread().name}")
            
            // bắt đầu chạy coroutine với context là ctx1
       		runBlocking(ctx1) {
                    // coroutine đang chạy trên context ctx1 và trên thread thread1
           			println("Started in ctx1 - ${Thread.currentThread().name}")
                    
                    // sử dụng hàm withContext để chuyển đổi context từ ctx1 qua ctx2
           			withContext(ctx2) {
                        // coroutine đang chạy với context ctx2 và trên thread thread2
               			println("Working in ctx2 - ${Thread.currentThread().name}")
           			}
                    
                    // coroutine đã thoát ra block withContext nên sẽ chạy lại với context ctx1 và trên thread thread1
           			println("Back to ctx1 - ${Thread.currentThread().name}")
       		}
   		}
        
  		println("out of ctx2 block - ${Thread.currentThread().name}")
   	}

  println("out of ctx1 block - ${Thread.currentThread().name}")
  }
=> Output ra
  ctx1 - main
  ctx2 - main
  Started in ctx1 - thread1
  Working in ctx2 - thread2
  Back to ctx1 - thread1
  out of ctx2 block - main
  out of ctx1 block - main
- Cong dung cua ham withContext se duoc su dung hau het trong cac du an. Cu the ta se get data duoi background thread va can UI thread de 
update UI.
  GlobalScope.launch(Dispatchers.IO) {
    // do background task
    withContext(Dispatchers.Main) {
      // update UI
    }
  }

3. Cac loai Dispatcher trong Coroutine
a. Dispatchers va Threads
- VD cac loai dispatchers
  fun main() = runBlocking<Unit> {
    launch(Dispatchers.Unconfined) { // not confined -- will work with main thread
      println("Unconfined            : I<m working in thread ${Thread.currentThread().name}")
    }
    launch(Dispatchers.Default) { // will get dispatched to DefaultDispatcher
      println("Default               : I<m working in thread ${Thread.currentThread().name}")
    }
    launch(newSingleThreadContext("MyOwnThread")) { // will get its own new thread
      println("newSingleThreadContext: I<m working in thread ${Thread.currentThread().name}")
    }    
  }
=> Output
  Unconfined            : I<m working in thread main
  Default               : I<m working in thread DefaultDispatcher-worker-1
  newSingleThreadContext: I<m working in thread MyOwnThread
=> Ket qua in ra khi chay Default va newSingleThreadContext chinh xac nhung gi da de cap con Unconfined lai in ra ket qua Main 
  thread giong Dispatchers.Main. 
  
- Unconfined Dispatchers khac Main
  fun main() = runBlocking {
    launch(Dispatchers.Unconfined) { // chưa được confined (siết lại) nên nó sẽ chạy trên main thread
      println("Unconfined      : I<m working in thread ${Thread.currentThread().name}")
      delay(1000)
      // hàm delay() sẽ làm coroutine bị suspend sau đó resume lại
      println("Unconfined      : After delay in thread ${Thread.currentThread().name}")
    }
  }
=> Output la:
  Unconfined      : I<m working in thread main
  Unconfined      : After delay in thread kotlinx.coroutines.DefaultExecutor
  Ban dau coroutine chay tren Main thread nhung sau khi delay 1s thi chay tiep tren background thread 
+ Boi vi dispatcher Unconfined nay chay 1 coroutine k gioi han bat ky thread cu the nao.
+ Ban dau coroutine chua dc confined thi no se chay tren current thread - Current thread la main thread nen no se chay tren mainthread.
+ Cho den khi no bi suspend (su dung delay de suspend).
+ Sau khi coroutine do bi resume thi no se khong chay tren current thread nua ma chay tren background thread.

4. Dat ten cho coroutine
- Su dung element CoroutineName de dat ten cho coroutine
  GlobalScope.launch(CoroutineName("demo_2")) {
    // coroutine được đặt tên là demo_2
  }
  
IV. Job, Join, Cancellation va Timeout
1. Job - 1 element trong coroutine
- Giu nhiem vu nam giu thong tin lifecycle cua coroutine, cancel coroutine. 
- Moi khi launch 1 coroutine thi no tra ve 1 doi tuong Job nay.

2. Function Join - Doi coroutine thuc thi xong
- Su dung Job de thuc hien 1 so method co san trong coroutine. Vd ham join().
- Khi 1 coroutine goi ham Join() thi tien trinh phai doi coroutine nay chay xong task roi moi chay tiep.
- VD:
  fun main() = runBlocking {
    val job = GlobalScope.launch { // launch a new coroutine and keep a reference to its Job
      delay(5000L)
      println("World!")
    }
    println("Hello,")
    job.join() // wait until child coroutine completes
    println("Kotlin")
  }
=> Output:
  22:07:20 I/System.out: Hello
  22:07:25 I/System.out: World
  22:07:25 I/System.out: Kotlin
+ Dau tien in ra Hello, sau do coroutine dc goi. Coroutine nay thuc thi delay 5s roi print ra tu world. 
+ Sau khi coroutine chay xong thi tiep tuc chay xuong doan code de in ra tu Kotlin

3. Function Cancel - Huy bo 1 coroutine
- De dung va huy bo 1 coroutine dang chay ta su dung ham cancel cua Job.
  fun main() = runBlocking {
    val job = launch {
      repeat(1000) { i ->
        println("I<m sleeping $i ...")
        delay(500L)
      }
    }
    delay(1300L) // delay a bit
    println("main: I<m tired of waiting!")
    job.cancel() // cancels the job
    println("main: Now I can quit.")    
  }
=> Output:
  I<m sleeping 0 …
  I<m sleeping 1 …
  I<m sleeping 2 …
  main: I<m tired of waiting!
  main: Now I can quit.
+ In ra cau I<m sleeping sau 500ms khoang 1k lan
+ Delay khoang 1300s roi cancel coroutine. Vay la sau 1300ms no in ra dc 3 dong.

4. Chu y khi loai bo 1 coroutine.
4.1. Huy bo coroutine la 1 su hop tac
- VD cancel 1 coroutine
  fun main() = runBlocking {
    val startTime = System.currentTimeMillis()
    val job = launch(Dispatchers.Default) {
      var nextPrintTime = startTime
      var i = 0
      while (i < 5) {
        if (System.currentTimeMillis() >= nextPrintTime) {
          println("job: I<m sleeping ${i++} ...")
          nextPrintTime += 500L
        }
      }
    }
    delay(1300L) // delay a bit
    println("main: I<m tired of waiting!")
    job.cancel() // cancels the job
    println("main: Now I can quit.")
  }

=> Output
  job: I<m sleeping 0 ...
  job: I<m sleeping 1 ...
  job: I<m sleeping 2 ...
  main: I<m tired of waiting!
  main: Now I can quit.
  job: I<m sleeping 3 ...
  job: I<m sleeping 4 ...
+ Doan code trong coroutine xu ly viec in ra cau I<m sleeping sau moi 500ms khoang 5 lan.
+ Doan code sau do xu ly viec cancel coroutine.
+ le ra output ra chi dc 3 dong nhu vd tren (vi thoi gian delay chi 1300ms) nhung no van print ra 5 lan
+ huy bo coroutine co tinh hop tac (co-operative).
+ Khi 1 coroutine bi cancel thi no se chi set lai 1 property co ten la isActive trong doi tuong Job tu true -> false (job.isActive = false)
con tien trinh cua no dang chay thi van chay cho den het ma k bi dung lai.
+ Doan code trong phan 3 tien trinh cua coroutine duoc huy bo thanh cong. Do la do ham delay(500L) ngoai chuc nang delay no con co
check chuc nang coroutine con song hay k, neu k con song(job.isActive == false) thi no se huy bo tien trinh cua coroutine do ngay va luon.
+ Tat ca cac ham cua suspend function trong package kotlinx.coroutine deu co chuc nang nay.

- Su dung property isActive de check coroutine con song hay k
  fun main() = runBlocking {
    val startTime = System.currentTimeMillis()
    val job = launch(Dispatchers.Default) {
      var nextPrintTime = startTime
      var i = 0
      while (isActive) {   // Điều kiện i < 5 đã được thay bằng isActive để ngăn chặn coroutine khi nó đã bị hủy
        if (System.currentTimeMillis() >= nextPrintTime) {
          println("job: I<m sleeping ${i++} ...")
          nextPrintTime += 500L
        }
      }
    }
    delay(1300L) // delay a bit
    println("main: I<m tired of waiting!")
    job.cancel() // cancels the job
    println("main: Now I can quit.")
  }
=> Output
  job: I<m sleeping 0 ...
  job: I<m sleeping 1 ...
  job: I<m sleeping 2 ...
  main: I<m tired of waiting!
  main: Now I can quit.
+ Check dk isActive(kiem tra coroutine con song hay k) de no huy viec print ra cac dong Im...

4.2. Su dung khoi finally de close resource ngay ca khi coroutine da bi huy bo.
- Neu tien trinh cua 1 coroutine bi huy bo ngay lap tuc no se tim den khoi finally de chay code trong do.
- Co the su dung dac diem nay de tranh thu close het cac resource truoc khi coroutine do chinh thuc bi khai tu
  fun main() = runBlocking {
    val job = launch {
      try {
        repeat(1000) { i ->
        println("I<m sleeping $i ...")
        delay(500L)
        }
      } finally {
        // Tranh thủ close resource trong này đi nha :D
        println("I<m running finally")
      }
    }
    delay(1300L) // delay a bit
    println("main: I<m tired of waiting!")
    job.cancel() // cancels the job
    println("main: Now I can quit.")
  }
=> Output
  I<m sleeping 0 ...
  I<m sleeping 1 ...
  I<m sleeping 2 ...
  main: I<m tired of waiting!
  main: Now I can quit.
  I<m running finally
+ Cuoi cung van in ra I<m running finally

4.3. Coroutine van co the chet trong khoi finally
- De khoi delay() trong khoi finally
  fun main() = runBlocking {
    val job = launch {
      try {
        repeat(1000) { i ->
          println("I<m sleeping $i ...")
          delay(500L)
        }
      } finally {
        println("I<m running finally")
        delay(1000L)                      // hàm delay được thêm vào khối finally
        println("Print me please!")
      }
    }
    delay(1300L) // delay a bit
    println("main: I<m tired of waiting!")
    job.cancel() // cancels the job
    println("main: Now I can quit.")
  }
=> Output
  I<m sleeping 0 ...
  I<m sleeping 1 ...
  I<m sleeping 2 ...
  main: I<m tired of waiting!
  main: Now I can quit.
  I<m running finally
+ Khi de delay(1000L) trong finally thi no check xem coroutine da dong hay chua nen no se dung tien trinh neu nhu coroutine k con song.
Do do no k chay dong lenh in ra "Print...."
  
4.4 Lam cho coroutine chay mai
- Su dung 1 element cua coroutine context la NonCancellable
  fun main() = runBlocking {
      val job = launch {
          try {
              repeat(1000) { i ->
              println("I<m sleeping $i ...")
              delay(500L)
              }
          } finally {
              withContext(NonCancellable) {  // Nhờ có em NonCancellable mà anh được phép chạy bất chấp đấy
                  println("I<m running finally")
                  delay(1000L)
                  println("I<m non-cancellable")
              }
          }
      }
      delay(1300L) // delay a bit
      println("main: I<m tired of waiting!")
      job.cancel() // cancels the job
      println("main: Now I can quit.")    
  }

=> Output
    I<m sleeping 0 ...
    I<m sleeping 1 ...
    I<m sleeping 2 ...
    main: I<m tired of waiting!
    main: Now I can quit.
    I<m running finally
    I<m non-cancellable
+ Chu y NonCancellable va ham withContext() 
+ Ham withContext co tac dung dieu chinh lai context cua coroutine.
+ Coroutine khi moi dc tao ra thi no co default la Cancellable(co the huy dc) nhung sau do neu ta muon dieu chinh no lai 
thanh NonCancellable(k the huy duoc) thi withContext se giup ta thuc hien viec do.
+ withContext() co the coi nhu 1 coroutine thuc thi task duoi background thread(Dispatchers.IO) va sau do khi task chay xong 
thi cho no chay tiep tren mainthread(Dispatchers.Main) de update UI.
+ NonCancellable la 1 element trong tap Context cua coroutine. No co tac dung giup coroutine song mai, k thu gi co the dong no cho 
den khi no hoan thanh task






  


