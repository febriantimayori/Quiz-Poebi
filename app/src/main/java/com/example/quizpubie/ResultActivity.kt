package com.example.quizpubie

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_result.*

class ResultActivity : Activity() {

    private var resultLabel: TextView?= null
    private var totalScoreLabel: TextView?= null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result)

        resultLabel = findViewById(R.id.resultLabel)
        totalScoreLabel = findViewById(R.id.totalScore)

        val score = intent.getIntExtra("RIGHT_ANSWER_COUNT", 0)
        resultLabel!!.text = "$score/5"
        val settings = getSharedPreferences("GameKuis", Context.MODE_PRIVATE)

        var totalScore = settings.getInt("totalScore", 0)
        totalScore += score

        resultLabel!!.text ="$score /20"
        totalScoreLabel!!.text = "Total Score : $totalScore"

        val editor = settings.edit()
        editor.putInt("totalScore",totalScore)
        editor.commit()

        playAgain.setOnClickListener{kembali()}

    }

    private fun kembali(){
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }

}
