package com.example.billiardsscore

import android.content.ContentValues
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        start.setOnClickListener {
            val intent = Intent(this, PlayActivity::class.java)

            // if user's information changes, they move to next activity(= PlayActivity)
            intent.putExtra("pl1", "${pl1.text}")
            intent.putExtra("pl1_goal", "${pl1_goal.text}")
            intent.putExtra("pl2", "${pl2.text}")
            intent.putExtra("pl2_goal", "${pl2_goal.text}")

            intent.flags = Intent.FLAG_ACTIVITY_NO_HISTORY
            startActivity(intent)

            Toast.makeText(this, "환영합니다 ${pl1.text}님, ${pl2.text}님!", Toast.LENGTH_SHORT).show()
        }
    }
}