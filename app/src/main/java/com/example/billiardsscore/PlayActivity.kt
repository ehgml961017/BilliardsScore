package com.example.billiardsscore

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.core.content.ContextCompat
import kotlinx.android.synthetic.main.activity_play.*
import kotlinx.android.synthetic.main.activity_play.pl1_goal
import kotlinx.android.synthetic.main.activity_play.pl2_goal
import java.util.*

class PlayActivity : AppCompatActivity() {
    var isPl1Order:Boolean = false
    var isPl2Order:Boolean = false

    var pl1Score = 0
    var pl2Score = 0

    var pl1Goal:Int = 0
    var pl2Goal:Int = 0

    var time = 500
    var timerTask: Timer? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_play)

        // setting display
        pl1_name.text = intent.getStringExtra("pl1")
        pl1_goal.text = intent.getStringExtra("pl1_goal")
        pl2_name.text = intent.getStringExtra("pl2")
        pl2_goal.text = intent.getStringExtra("pl2_goal")

        pl1Goal = pl1_goal.text.toString().toInt()
        pl2Goal = pl2_goal.text.toString().toInt()
    }

    override fun onResume() {
        super.onResume()

        // if a player display is touched, the player get turn first
        player1Layout.setOnClickListener {
            if(playerCheck()) {
                Toast.makeText(this, "${pl1_name.text} start", Toast.LENGTH_SHORT).show()
                isPl1Order = true
                start()
            }
        }

        player2Layout.setOnClickListener {
            if(playerCheck()){
                Toast.makeText(this, "${pl2_name.text} start", Toast.LENGTH_SHORT).show()
                isPl2Order = true
                start()
            }
        }

        // only the player turn, display activates
        pl1_minus.setOnClickListener {
            if(isPl1Order && (pl1Score < pl1Goal)) {
                if (pl1Score > 0) {
                    pl1_score.text = "${--pl1Score}"
                }
            }
            if(pl1Score >= pl1Goal){
                EndGame(pl1_name.text.toString())
            }
        }
        pl1_plus.setOnClickListener {
            if(isPl1Order && (pl1Score < pl1Goal)) {
                pl1_score.text = "${++pl1Score}"
            }
            if(pl1Score >= pl1Goal){
                EndGame(pl1_name.text.toString())
            }
        }

        pl2_minus.setOnClickListener {
            if(isPl2Order && (pl2Score < pl2Goal)) {
                if (pl2Score > 0) {
                    pl2_score.text = "${--pl2Score}"
                }
            }
            if(pl2Score >= pl2Goal){
                EndGame(pl2_name.text.toString())
            }
        }
        pl2_plus.setOnClickListener {
            if(isPl2Order && (pl2Score < pl2Goal)) {
                pl2_score.text = "${++pl2Score}"
            }
            if(pl2Score >= pl2Goal){
                EndGame(pl2_name.text.toString())
            }
        }

    }

    private fun playerCheck(): Boolean {
        return !isPl1Order && !isPl2Order
    }

    private fun EndGame(winner:String){
        val intent = Intent(this, ResultActivity::class.java)
        intent.putExtra("winner", winner)
        intent.flags = Intent.FLAG_ACTIVITY_NO_HISTORY
        startActivity(intent)
    }

    private fun start(){
        timerTask = kotlin.concurrent.timer(period = 10){
            time--
            if(time >= 0) {
                val min = time / 6000
                val sec = (time % 6000) / 100
                val milli = time % 100

                runOnUiThread {
                    if (isPl1Order) pl1_rest_time.text = "$min:$sec:$milli"
                    else pl2_rest_time.text = "$min:$sec:$milli"
                }
            }
            if(time < 0){
                // waiting for sub thread's text printing
                Thread.sleep(50)
                reset()
            }
        }
        if(isPl1Order) player1Layout.setBackgroundColor(Color.YELLOW)
        else player2Layout.setBackgroundColor(Color.YELLOW)
    }

    private fun reset(){
        timerTask?.cancel()
        time = 500
        if(isPl1Order){
            player1Layout.setBackgroundColor(ContextCompat.getColor(this,R.color.tranparent))
        }
        else{
            player2Layout.setBackgroundColor(ContextCompat.getColor(this,R.color.tranparent))
        }

        swapOrder()
        start()
    }

    private fun swapOrder(){
        val tmp = isPl1Order
        isPl1Order = isPl2Order
        isPl2Order = tmp
    }
 }
