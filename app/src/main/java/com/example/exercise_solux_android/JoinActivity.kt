package com.example.exercise_solux_android

import android.app.AlertDialog
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
        setContentView(binding.root)

        val login_intent = Intent(this, LoginActivity::class.java) //가입 화면
        val PW = binding.PW.text.toString()
        val PW2 = binding.PW2.text.toString()

//        binding.Authentication.setOnClickListener {
//            val email = binding.Email.text.toString()
//            val verificationCode = generateRandomCode()
//
//        }


        binding.PW.addTextChangedListener(object : TextWatcher { // 비밀번호 재확인 일치여부
            override fun afterTextChanged(p0: Editable?) {
                if (PW2.equals(PW)
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

            }
        })

        binding.backButton.setOnClickListener() {
            startActivity(login_intent)
        }

        binding.btnJoin.setOnClickListener() {
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
            }else{
                Toast.makeText(this@JoinActivity, "이메일과 비밀번호를 정확히 입력해주세요.", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun signUpWithEmail(email: String, password: String) {
        // 이 부분에서 이메일 인증 성공 여부와 인증 코드 검증을 확인한 후에 회원가입 처리를 진행해야 합니다.
        // 즉, showCodeInputDialog()에서 확인한 결과를 이곳으로 전달하여 처리해야 합니다.
        // 이메일 인증 성공과 인증 코드 검증 결과를 변수 등을 사용하여 저장한 후에 사용할 수 있습니다.
        val isEmailConfirmed = true // 이 부분에 이메일 인증 성공 여부 저장
        val isCodeValid = true // 이 부분에 인증 코드 검증 결과 저장

        if (isEmailConfirmed && isCodeValid) {
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
                        mStore.collection(FirebaseID.email).document(user?.uid ?: "")
                            .set(userMap, SetOptions.merge())
                        val intent = Intent(this@JoinActivity, LoginActivity::class.java)
                        startActivity(intent)
                        finish()
                    } else {
                        Toast.makeText(
                            this@JoinActivity,
                            "회원가입에 실패하였습니다. 다시 시도해 주세요.",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
        }
    }

    //메일 인증 구현
    private fun showCodeInputDialog(email: String, password: String, verificationCode: String) {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("이메일 인증 코드 입력")
        val input = EditText(this)
        builder.setView(input)

        builder.setPositiveButton("확인") { dialog, _ ->
            val code = input.text.toString().trim()
            // 여기에서 입력받은 인증 코드를 검증하고, 회원가입 처리를 진행합니다.
            // 인증 코드 검증에 성공하면 회원가입 처리를 진행하면 됩니다.
            if (validateCode(code, verificationCode)) {
                // 인증 코드 검증 성공 시 회원가입 처리 진행
                signUpWithEmail(email, password)
            } else {
                // 인증 코드 검증 실패 시 에러 메시지를 표시하거나 재시도 유도 등의 처리
                Toast.makeText(applicationContext, "인증 코드가 올바르지 않습니다.", Toast.LENGTH_SHORT).show()
            }
            dialog.dismiss()
        }

        builder.setNegativeButton("취소") { dialog, _ ->
            dialog.cancel()
        }

        builder.show()
    }
    private fun generateRandomCode(): String {
        val charPool: List<Char> = ('0'..'9') + ('A'..'Z') + ('a'..'z')
        return (1..6)
            .map { kotlin.random.Random.nextInt(0, charPool.size) }
            .map(charPool::get)
            .joinToString("")
    }
    // 인증 코드 검증 로직을 구현합니다.
    private fun validateCode(code: String, verificationCode: String): Boolean {
        // 이메일로 보낸 인증 코드와 사용자가 입력한 코드를 비교하여 일치 여부를 반환합니다.
        // 일치하면 true, 일치하지 않으면 false를 반환하면 됩니다.
        return code == verificationCode
    }
}