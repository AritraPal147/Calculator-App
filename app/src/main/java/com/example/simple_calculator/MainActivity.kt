package com.example.simple_calculator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log.d
import android.util.Log.e
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast

class MainActivity : AppCompatActivity() {

    private var tvInput: TextView? = null
    var lastNumeric: Boolean = true
    var dotPresent: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        tvInput = findViewById(R.id.textView)
    }

    /*
    If onClickListener would be used:
    private var btnOne: Button? = null

    (in onCreate() function)
    btnOne = findViewById(R.id.btnOne)
    btnOne?.setOnClickListener{
        tvInput?.append("1")
    }
    This code would have to be repeated for each button within the app
    onClickListener is best practice
     */

    fun onDigit(view: View){
        if (tvInput?.text.toString() == "0")       // Checks if the text on screen is zero or not
            tvInput?.text = ""          // Removes the 0 and replaces it with empty string
        tvInput?.append((view as Button).text)
        // Converts the view into a button and gets its text
        lastNumeric = true
    }

    fun onClear(view: View){
        tvInput?.text = "0"
        val v= tvInput?.text
        dotPresent = false
    }
    
    fun onDecimalPoint(view: View){
        for (i in tvInput?.text.toString()){        // Checking if "." is present in text View or not
            if (i == '.')
                dotPresent = true
        }

        if (isOperatorAdded(tvInput?.text.toString()) && lastNumeric){
            dotPresent = false
        }

        if (!dotPresent) {
            if (!lastNumeric)
                tvInput?.append("0")
            tvInput?.append(".")
            lastNumeric = false
        }
    }

    fun onOperator(view: View){
        // let will execute if tvInput is NOT null
        tvInput?.text.let {
            // it stores the text of tvInput, i.e., it = tvInput?.text
            if (lastNumeric && !isOperatorAdded(it.toString())) {
                tvInput?.append((view as Button).text)
                lastNumeric = false
            }
        }
    }

    private fun removeZeroAfterDot(result: String): String{
        var value = result
        val splitValue = value.split(".")
        val afterPoint = splitValue[1]
        if (afterPoint == "0")
            return splitValue[0]
        return value
    }

    // To check whether the last element on the text view is an operator or not
    private fun isOperatorAdded(value: String): Boolean{
        return if(value.startsWith("-")) {
            false                      // If value starts with "-", return false
        }

        else{          // Returns true or false depending on whether the string contains operators
            value.contains("/") || value.contains("*")
                    || value.contains("+")
                    || value.contains("-")
        }
    }

    fun onEqual(view: View){
        if (lastNumeric){
            var tvValue = tvInput?.text.toString()
            var prefix = ""
            var result = ""

            try{
                if (tvValue.startsWith("-")) {  // If tvValue starts with "-"
                    prefix = "-"
                    tvValue = tvValue.substring(1)       // removes "-" from tvValue
                }

                if (tvValue.contains("-")) {
                    val splitValue = tvValue.split("-")
                    // Splits string at "-" and stores it in an array
                    var firstOperand = splitValue[0]
                    var secondOperand = splitValue[1]

                    if (prefix.isNotEmpty()){
                        firstOperand = prefix + firstOperand
                    }

                    result = (firstOperand.toDouble() - secondOperand.toDouble()).toString()
                }

                else if (tvValue.contains("+")) {
                    val splitValue = tvValue.split("+")
                    // Splits string at "-" and stores it in an array
                    var firstOperand = splitValue[0]
                    var secondOperand = splitValue[1]

                    if (prefix.isNotEmpty()){
                        firstOperand = prefix + firstOperand
                    }

                    result = (firstOperand.toDouble() + secondOperand.toDouble()).toString()
                }

                else if (tvValue.contains("*")) {
                    val splitValue = tvValue.split("*")
                    // Splits string at "-" and stores it in an array
                    var firstOperand = splitValue[0]
                    var secondOperand = splitValue[1]

                    if (prefix.isNotEmpty()){
                        firstOperand = prefix + firstOperand
                    }

                    result = (firstOperand.toDouble() * secondOperand.toDouble()).toString()
                }

                else if (tvValue.contains("/")) {
                    val splitValue = tvValue.split("/")
                    // Splits string at "-" and stores it in an array
                    var firstOperand = splitValue[0]
                    var secondOperand = splitValue[1]

                    if (prefix.isNotEmpty()){
                        firstOperand = prefix + firstOperand
                    }

                    result = (firstOperand.toDouble() / secondOperand.toDouble()).toString()
                }

                if (result != "") {
                    result = removeZeroAfterDot(result)
                    tvInput?.text = result
                }

            }catch(e: java.lang.ArithmeticException){
            e.printStackTrace()             // prints error into the logcat
            }
        }
    }
}