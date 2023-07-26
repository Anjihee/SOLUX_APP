package com.example.exercise_solux_android

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.*
import androidx.activity.ComponentActivity
import com.example.exercise_solux_android.databinding.ActivityJoinBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions

class JoinActivity : ComponentActivity() {
    private lateinit var binding: ActivityJoinBinding
    private val mAuth: FirebaseAuth = FirebaseAuth.getInstance() //회원가입 파이어베이스 연동
    private val mStore: FirebaseFirestore = FirebaseFirestore.getInstance()

    object FirebaseID { //파이어베이스 회원가입 정보
        const val email = "email"
        const val password = "password"
        const val nickname = "nickname"
        const val gender = "gender"
        // 여기에 다른 필드 이름들을 추가할 수 있습니다.
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityJoinBinding.inflate(layoutInflater)
        setContentView(R.layout.activity_join)

        val btnJoin = findViewById<Button>(R.id.btnJoin)

        binding.PW.addTextChangedListener(object : TextWatcher { //입력이 끝났을 때
            //비밀번호 일치하는지 확인
            override fun afterTextChanged(p0: Editable?) {
                if (binding.PW2.getText().toString()
                        .equals(binding.PW.getText().toString())
                ) {
                    binding.textPWConfirm.setText("비밀번호가 일치합니다.")
                    binding.textPWConfirm.setTextColor(Color.parseColor("#97BF04"))
                    // 가입하기 버튼 활성화
                    binding.btnJoin.isEnabled = true
                } else {
                    binding.textPWConfirm.setText("비밀번호가 일치하지 않습니다.")
                    binding.textPWConfirm.setTextColor(Color.parseColor("#FF0000"))
                    // 가입하기 버튼 비활성화
                    binding.btnJoin.isEnabled = false
                }
            }

            //입력하기 전
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            //텍스트 변화가 있을 시
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                if (binding.PW2.getText().toString()
                        .equals(binding.PW.getText().toString())
                ) {
                    binding.textPWConfirm.setText("비밀번호가 일치합니다.")
                    binding.textPWConfirm.setTextColor(Color.parseColor("#97BF04"))
                    // 가입하기 버튼 활성화
                    binding.btnJoin.isEnabled = true
                } else {
                    binding.textPWConfirm.setText("비밀번호가 일치하지 않습니다.")
                    binding.textPWConfirm.setTextColor(Color.parseColor("#FF0000"))
                    // 가입하기 버튼 비활성화
                    binding.btnJoin.isEnabled = false
                }
            }
        })

        btnJoin.setOnClickListener() {
            if (binding.textEmailConfirm.getText()
                    .equals("인증되었습니다.") && binding.textPWConfirm.getText()
                    .equals("비밀번호가 일치합니다.") && binding.Nickname.getText()
                    .isNotEmpty() && (binding.Female.isChecked() || binding.Male.isChecked())
            ) {
                mAuth.createUserWithEmailAndPassword(
                    binding.Email.text.toString(),
                    binding.PW.text.toString()
                )
                    .addOnCompleteListener(this) { task ->
                        if (task.isSuccessful) {
                            // Sign in success, update UI with the signed-in user's information
                            val user = mAuth.currentUser
                            var gender = ""
                            if (binding.Female.isChecked()) {
                                gender = "Female"
                            } else if (binding.Male.isChecked()) {
                                gender = "Male"
                            }
                            val userMap = hashMapOf(
                                FirebaseID.email to user?.email,
                                FirebaseID.password to binding.PW,
                                FirebaseID.nickname to binding.Nickname,
                                FirebaseID.gender to gender
                            )

                            // Create a document with the current user's Uid as the document name.
                            // This is necessary to avoid conflicts between user contents and user IDs.
                            mStore.collection(FirebaseID.email).document(user?.uid ?: "")
                                .set(userMap, SetOptions.merge())

                            // Successful registration, navigate to the login activity
                            val intent = Intent(this@JoinActivity, LoginActivity::class.java)
                            startActivity(intent)
                            finish()
                        } else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(this@JoinActivity, "회원가입 실패", Toast.LENGTH_SHORT).show()
                        }
                    }
            }
        }
    }
}