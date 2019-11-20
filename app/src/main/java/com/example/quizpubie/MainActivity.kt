package com.example.quizpubie

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.RadioButton
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*
import kotlin.collections.ArrayList
import kotlin.random.Random

class MainActivity : AppCompatActivity() {

    private var nomorSoal: TextView?= null
    private var pertanyaan: TextView?= null
    private var answer1: RadioButton?= null
    private var answer2: RadioButton?= null
    private var answer3: RadioButton?= null

    private var benar: String?= null
    private var rightAnswerCount = 0
    private var quizCount = 1
    internal var quizArray = ArrayList<ArrayList<String>>()

    internal var quizData = arrayOf(arrayOf("Drama korea yang berjudul 'Hotel Del Luna' diperankan oleh adalah ","IU","Suzy","Park Bo Young"),
        arrayOf("Tahun berapa lagu Avril Lavigne berjudul 'Head Above Water' dirilis adalah ","2019","2016","2004"),
        arrayOf("Nama badut seram di film 'IT' adalah ","Pennywise","Joker","Ondel-ondel"),
        arrayOf("Pemeran utama wanita didrama DOTS adalah ","Song Hye Kyo","Kim Ji Won","Song Ji Hyo"),
        arrayOf("Dibawah ini yang bukan member dari girl group 'Blackpink' adalah ","Monalisa","Jennie","Rose"),
        arrayOf("Lagu yang sedang trending yaitu lagu 'Senorita' dinyanyikan oleh adalah","Shawn Mendes","Justin Bieber","DJ Khalid"),
        arrayOf("Dibawah ini yang ikut berperan dalam film 'The Conjuring' adalah ","Patrick Wilson","Alexa Vega","Bill Gates"),
        arrayOf("Tahun berapa film Spy Kids 2 'The Island Of Lost Dreams' dirilis adalah ","2002","2016","2004"),
        arrayOf("Dibawah ini yang merupakan sutradara film 'Terlalu Tampan' adalah ","Sabrina Rochelle","Aris Nugraha","Aseo Kusdinar"),
        arrayOf("Penyanyi lagu ' I LOVE YOU 3000 adalah ","Stephanie Poetri","Taylor Swift","Troye Sivan"),
        arrayOf("Penyanyi lagu 'Hanya Rindu' adalah ","Andmesh","Jeremy Zucker","Jacob Sartorius"),
        arrayOf("Nama asli Bill Gates adalah ","William Henry Gates III","Wiliam","Henry Gates"),
        arrayOf("Pada tahun berapa Microsoft Word ditemukan? ","1983","1953","1963"),
        arrayOf("Pendiri merk Huawei adalah ","Lei Jun","Bill Gates","Ren Zhengfei"),
        arrayOf("Pendiri merk Samsung adalah ","Lee Byung-Chull","Lee Jae Yong","Lee Kun Hee"),
        arrayOf("Lagu Alan Walker apa yang dipakai di latar lobby room PUBG MOBILE?","On My Way","Faded","Here with me"),
        arrayOf("Siapa developer game MOBILE LEGENDS Bang Bang?","Moonton","Tencent","com2uS"),
        arrayOf("A whole new world adalah soundtrack dari film...","Aladdin","Belle","Frozen"),
        arrayOf("Siapa nama panggung dari Christopher Comstock?","Marshmello","Alan Wallker","DJ Khalid"),
        arrayOf("Kapan lagu Selena Gomez berjudul Back To You dirilis?","2018","2019","2017"))


    //List Pertanyaannya


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        nomorSoal = findViewById(R.id.nomerSoal)
        pertanyaan = findViewById(R.id.pertanyaan)
        answer1 = findViewById(R.id.answer1)
        answer2 = findViewById(R.id.answer2)
        answer3 = findViewById(R.id.answer3)

        for (i in quizData.indices){
            val tmpArray = ArrayList<String>()
            tmpArray.add(quizData[i][0])
            tmpArray.add(quizData[i][1])
            tmpArray.add(quizData[i][2])
            tmpArray.add(quizData[i][3])

            quizArray.add(tmpArray)
        }
        showNextQuiz()
    }


    private fun showNextQuiz() {
        nomerSoal!!.text = "Q$quizCount"
        val random = Random
        val randNum = random.nextInt(quizArray.size)

        val quiz = quizArray[randNum]

        pertanyaan!!.text = quiz[0]
        benar = quiz[1]

        quiz.removeAt(0)
        Collections.shuffle(quiz)

        answer1!!.text = quiz[0]
        answer2!!.text = quiz[1]
        answer3!!.text = quiz[2]
//        answer4!!.text = quiz[3]

        quizArray.removeAt(randNum)

    }

    fun onRadioClicked(view: View) {
        val answer = findViewById<RadioButton>(view.id)
        val btnText = answer.text.toString()

        val alertTitle: String

        if (btnText == benar) {
            alertTitle = "Jawaban Anda Benar!"
            rightAnswerCount++
        } else {
            alertTitle = "Jawaban Anda Salah!"
        }

        val builder = AlertDialog.Builder(this)
        builder.setTitle(alertTitle)
        builder.setMessage("Answer : " + benar!!)
        builder.setPositiveButton("OK") { dialog, which ->
            if (quizCount == QUIZ_COUNT) {
                val intent = Intent(this, ResultActivity::class.java)
                intent.putExtra("RIGHT_ANSWER_COUNT", rightAnswerCount);
                startActivity(intent)
            } else {
                quizCount++
                showNextQuiz()
            }
        }
        builder.setCancelable(false)
        builder.show()
    }

    companion object {
        private val QUIZ_COUNT = 20	// Ganti ini sesuai dengan jumlah pertanyaan
    }
}