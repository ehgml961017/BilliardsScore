package com.example.billiardsscore

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_play.*
import kotlinx.android.synthetic.main.activity_play.pl1_goal
import kotlinx.android.synthetic.main.activity_play.pl2_goal

class PlayActivity : AppCompatActivity() {

    var isPl1Order:Boolean = false
    var isPl2Order:Boolean = false

    // timer
    var pl1Time:Int = 0
    var pl2Time:Int = 0

    var pl1Score = 0
    var pl2Score = 0

    var pl1Goal:Int = 0
    var pl2Goal:Int = 0

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
                isPl2Order = false
//                timer(period = 1000){
//                    pl1Time
//                }

                player1Layout.setBackgroundColor(Color.YELLOW)
            }
        }

        player2Layout.setOnClickListener {
            if(playerCheck()){
                Toast.makeText(this, "${pl2_name.text} start", Toast.LENGTH_SHORT).show()
                isPl2Order = true
                isPl1Order = false
                player2Layout.setBackgroundColor(Color.YELLOW)
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
        if(!isPl1Order && !isPl2Order){
            return true
        } else{
            return false
        }
    }

    private fun EndGame(winner:String){
        val intent = Intent(this, ResultActivity::class.java)
        intent.putExtra("winner", winner)
        intent.flags = Intent.FLAG_ACTIVITY_NO_HISTORY
        startActivity(intent)
    }

}
