package com.example.calculator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import java.lang.ArithmeticException

class MainActivity : AppCompatActivity() {

    var lastNumeric : Boolean = false
    var lastDot : Boolean = false
    var equalClick : Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun onDigit(view: View){ //instead of onClickListener
        val btnText = (view as Button).text
        tvInput.append(btnText) //gets the value of the button and displays it in the input field
        lastNumeric = true
        equalClick = false
    }
     fun onClear(view: View){
         tvInput.text = ""
         lastNumeric = false
         lastDot = false
         equalClick = false
     }

    fun onDot(view: View){

        if(lastNumeric && !lastDot && !equalClick) {
            tvInput.append((view as Button).text)
            lastNumeric = false
            lastDot = true
        }
    }

    private fun actions(op: String, pref: String, text: String){
        var o = op
        var prefix = pref
        var input = text

        val splitValue = input.split(o)
        var first = splitValue[0]
        var second = splitValue[1]
        if(prefix.isNotEmpty()){
            first = prefix + first
        }
        when (o) {
            "-" -> {tvInput.text = removeZero((first.toDouble() - second.toDouble()).toString())}
            "*" -> {tvInput.text = removeZero((first.toDouble() * second.toDouble()).toString())}
            "+" -> {tvInput.text = removeZero((first.toDouble() + second.toDouble()).toString())}
            "/" -> {tvInput.text = removeZero((first.toDouble() / second.toDouble()).toString())}
        }
    }

    fun onEqual(view: View){
        equalClick = true
        if(lastNumeric){
            var tvValue = tvInput.text.toString()
            var prefix = ""

            try {
                if(tvValue.startsWith("-")){
                    prefix = "-"
                    tvValue = tvValue.substring(1)
                }
                when {
                    tvValue.contains("-") -> actions("-",prefix,tvValue)
                    tvValue.contains("*") -> actions("*",prefix,tvValue)
                    tvValue.contains("+") -> actions("+",prefix,tvValue)
                    tvValue.contains("/") -> actions("/",prefix,tvValue)
                }

            } catch (e: ArithmeticException){
                e.printStackTrace()
            }
        }
    }

    private fun removeZero(result: String) : String{
        var value = result
        if (value.contains(".0")){
            value = result.substring(0, result.length - 2)
        }
        return value
    }

    fun onOperator(view: View) {
        if(lastNumeric && !isOperatorAdded(tvInput.text.toString())){
            tvInput.append((view as Button).text)
            lastNumeric = false
            lastDot = false
        }
    }

    private fun isOperatorAdded(value: String) : Boolean{
        return if(value.startsWith("-")){
            false
        } else{
            value.contains("/") || value.contains("*") ||value.contains("+") ||
                    value.contains("-")
        }
    }
}