package com.example.bmicalculator

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class BMIViewModel: ViewModel() {

    private val _bmi2digits = MutableLiveData<Float?>()
     val bmi2digits:LiveData<Float?>
     get() = _bmi2digits

    fun calculateBmi(weight:String,height:String){
        val bmi = weight.toFloat() / ((height.toFloat() / 100) * (height.toFloat() / 100))
        // get results in 2 d.p
        _bmi2digits.value = String.format("%.2f", bmi).toFloat()
    }
}