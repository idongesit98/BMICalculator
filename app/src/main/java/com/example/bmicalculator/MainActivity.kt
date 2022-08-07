package com.example.bmicalculator

import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider

class MainActivity : AppCompatActivity() {
    private lateinit var weightText:EditText
    private lateinit var heightText: EditText
    private lateinit var sf: SharedPreferences
    private lateinit var editor: SharedPreferences.Editor

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        weightText = findViewById(R.id.etWeight )
        heightText = findViewById(R.id.etHeight)
        val calcButton = findViewById<Button>(R.id.btnCalculate)
        sf = getSharedPreferences("my_sf", MODE_PRIVATE)
        editor = sf.edit()

        calcButton.setOnClickListener {
            val weight = weightText.text.toString()
            val height = heightText.text.toString()
            if (validateInput(weight,height)) {
                val bmi = weight.toFloat() / ((height.toFloat() / 100) * (height.toFloat() / 100))
                // get results in 2 d.p
                val bmi2digits = String.format("%.2f", bmi).toFloat()
                displayResult(bmi2digits)
            }
        }
    }
    override fun onPause() {
        super.onPause()
        val weight = weightText.text.toString()
        val height = heightText.text.toString().toInt()
        editor.apply {
            putString("sf_weight", weight)
            putInt("sf_height", height)
            commit()
        }
    }

    override fun onResume() {
        super.onResume()
        val weight = sf.getString("sf_weight", null)
        val height = sf.getInt("sf_height", 0)

        if (weight!=null){
            weightText.setText(weight.toString())
        }

        if (height!=0){
            heightText.setText(height.toString())
        }

    }

    private fun validateInput(weight: String?, height:String?):Boolean{
        return when{
            weight.isNullOrEmpty() -> {
                Toast.makeText(this,"Weight is empty", Toast.LENGTH_LONG).show()
                false
            }
            height.isNullOrEmpty()->{
                Toast.makeText(this,"Height is empty", Toast.LENGTH_LONG).show()
                false
            }
            else ->{
                true
            }
        }

    }

    private fun displayResult(bmi:Float){
        val resultIndex = findViewById<TextView>(R.id.tvIndex)
        val resultDescription = findViewById<TextView>(R.id.tvResult)
        val info = findViewById<TextView>(R.id.tvInfo)

        resultIndex.text =bmi.toString()
        info.text = "(Normal range is 18.5 - 24.9)"

        var resultText = ""
        var color = 0

        when {
            bmi<18.50 ->{
                resultText = "Underweight"
                color = R.color.under_weight
            }
            bmi in 18.50..24.99->{
                resultText = "Healthy"
                color = R.color.normal
            }
            bmi in 25.00..29.99->{
                resultText = "Overweight"
                color = R.color.over_weight
            }
            bmi > 29.99 ->{
                resultText = "Obese"
                color = R.color.over_weight
            }
        }
// Change colour for the text
        resultDescription.setTextColor(ContextCompat.getColor(this,color))
        resultDescription.text = resultText

    }
}