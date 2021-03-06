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




  







  


