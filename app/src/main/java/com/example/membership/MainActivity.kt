package com.example.membership

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AlertDialog
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_sub.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory




class MainActivity : AppCompatActivity() {

    public fun sceneConvert() {
        val intent = Intent(this, SubActivity::class.java)
        startActivity(intent)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)




        var retrofit = Retrofit.Builder()
                .baseUrl("http://192.168.0.2:8000")
                .addConverterFactory(GsonConverterFactory.create())
                .build()

        var postInfo = retrofit.create(PostInfoService::class.java)
        var loginService = retrofit.create(LoginService::class.java)

        location_btn.setOnClickListener{
            var locationWe = location.text.toString()

        }

        btnLogin.setOnClickListener {
            var textId = textID.text.toString()
            var textPw = textPW.text.toString()
            var success = true

            loginService.requestLogin(textId, textPw).enqueue(object: Callback<Login> {
                override fun onFailure(call: Call<Login>, t: Throwable) {
                    t.message?.let { it1 -> Log.d("DEBUG", it1) }
                    var dialog = AlertDialog.Builder(this@MainActivity)
                    dialog.setTitle("실패!")
                    dialog.setMessage("통신에 실패했습니다.")
                    dialog.show()
                }

                override fun onResponse(call: Call<Login>, response: Response<Login>) {
                    success = true;
                    var login = response.body()

                    var dialog = AlertDialog.Builder(this@MainActivity)
                    dialog.setTitle("알람!")
                    dialog.setMessage("로그인 성공")
                    dialog.show()

                    sceneConvert()
                }
            })


        }
    }
}