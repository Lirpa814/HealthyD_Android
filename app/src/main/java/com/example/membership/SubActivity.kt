package com.example.membership

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AlertDialog
import kotlinx.android.synthetic.main.activity_sub.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class SubActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sub)

        var retrofit = Retrofit.Builder()
            .baseUrl("http://192.168.0.2:8000")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        var getInfoService = retrofit.create(GetInfoService::class.java)
        var postInfoService = retrofit.create(PostInfoService::class.java)

        btnConfirm.setOnClickListener {
            var textName = textName.text.toString()

            getInfoService.getInfo(textName).enqueue(object: Callback<Info> {
                override fun onFailure(call: Call<Info>, t: Throwable) {
                    t.message?.let { it1 -> Log.d("DEBUG", it1) }
                    var dialog = AlertDialog.Builder(this@SubActivity)
                    dialog.setTitle("회원정보없음")
                    dialog.setMessage("회원정보가 없습니다. 가입을 진행해 주세요.")
                    dialog.show()


                }
                override fun onResponse(call: Call<Info>, response: Response<Info>) {
                    var info = response.body()

                    if (info?.name.equals("회원정보를 찾을 수 없습니다.")) {
                        var dialog = AlertDialog.Builder(this@SubActivity)
                        dialog.setTitle("알림!")
                        dialog.setMessage(info?.name + "\n가입을 진행해주세요.")
                        dialog.show()
                    }
                    else {
                        var dialog = AlertDialog.Builder(this@SubActivity)
                        dialog.setTitle("알림!")
                        dialog.setMessage("가입된 회원입니다. 정보를 불러왔습니다.")
                        dialog.show()

                        textGender.setText(info?.gender)
                        textHeight.setText(info?.height)
                        textWeight.setText(info?.weight)
                    }
                }
            })
        }

        btnRegist.setOnClickListener {
            var textName = textName.text.toString()
            var textGender = textGender.text.toString()
            var textHeight = textHeight.text.toString()
            var textWeight = textWeight.text.toString()

            postInfoService.postInfo(textName, textGender, textHeight, textWeight).enqueue(object: Callback<Info> {
                override fun onFailure(call: Call<Info>, t: Throwable) {
                    TODO("Not yet implemented")
                }

                override fun onResponse(call: Call<Info>, response: Response<Info>) {
                    var msg = response.body()

                    var dialog = AlertDialog.Builder(this@SubActivity)
                    dialog.setTitle("알람!")
                    dialog.setMessage(msg?.name)
                    dialog.show()
                }
            })
        }
    }
}