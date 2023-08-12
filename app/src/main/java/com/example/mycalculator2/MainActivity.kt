package com.example.mycalculator2

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private var calcResult: TextView? = null
    private var lastNumeric = false
    private var lastDot = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        calcResult = findViewById(R.id.calcResult)
    }


    fun onDigit(view: View) {
        val data = view as Button
        calcResult?.append(data.text)
        lastNumeric = true
        lastDot = false
    }

    fun onClear(view: View) {
        calcResult?.text = ""
        lastDot = false
        lastNumeric = false
    }

    fun onDecimal(view: View) {
        if (lastNumeric && !lastDot) {
            calcResult?.text?.let {
                if (!it.toString().contains(".")) {
                    calcResult?.append(".")
                    lastDot = true
                    lastNumeric = false
                }
            }
        }
    }

    fun onOperator(view: View) {
        calcResult?.text?.let {
            if (lastNumeric && !isOperatorAdded(it.toString())) {
                calcResult?.append((view as Button).text)
                lastNumeric = false
                lastDot = false
            }
        }
    }

    @SuppressLint("SetTextI18n")
    fun onEqual(view: View) {
        if (lastNumeric) {
            var calcText = calcResult?.text.toString()
            var prefix = ""

            try {
                if (calcText.startsWith("-")) {
                    prefix = "-"
                    calcText = calcText.substring(1)
                }
                if (calcText.contains("-")) {
                    var (one, two) = calcText.split("-")
                    if (prefix.isEmpty()) one = prefix + one
                    calcResult?.text =
                        removeZeroAfterDot((one.toDouble() - two.toDouble()).toString())
                } else if (calcText.startsWith("+")) {
                    prefix = "-"
                    calcText = calcText.substring(1)
                }
                if (calcText.contains("+")) {
                    var (one, two) = calcText.split("+")
                    if (prefix.isEmpty()) one = prefix + one
                    calcResult?.text =
                        removeZeroAfterDot((one.toDouble() + two.toDouble()).toString())
                } else if (calcText.startsWith("/")) {
                    prefix = "-"
                    calcText = calcText.substring(1)
                }
                if (calcText.contains("/")) {
                    var (one, two) = calcText.split("/")
                    if (prefix.isEmpty()) one = prefix + one
                    calcResult?.text =
                        removeZeroAfterDot((one.toDouble() / two.toDouble()).toString())
                } else if (calcText.startsWith("*")) {
                    prefix = "-"
                    calcText = calcText.substring(1)
                }
                if (calcText.contains("*")) {
                    var (one, two) = calcText.split("*")
                    if (prefix.isEmpty()) one = prefix + one
                    calcResult?.text =
                        removeZeroAfterDot((one.toDouble() * two.toDouble()).toString())
                }


            } catch (e: ArithmeticException) {
                e.printStackTrace()
            }
        }
    }

    private fun removeZeroAfterDot(result: String): String {
        var value = result
        if (result.contains(".0")) {
            value = result.substring(0, result.length - 2)
        }
        return value
    }

    private fun isOperatorAdded(value: String): Boolean {
        return if (value.startsWith("-")) {
            false
        } else {
            value.contains("/") ||
                    value.contains("*") ||
                    value.contains("+") ||
                    value.contains("-")
        }
    }

}




