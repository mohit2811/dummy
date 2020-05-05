package com.example.myapplication

import android.os.Bundle
import android.view.View
import android.widget.RadioGroup
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_in_line_sealed_infix_recursion_lambda.*

class InLineSealedInfixRecursionLambda : AppCompatActivity() {
    var type = "Lambda Expression"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_in_line_sealed_infix_recursion_lambda)

        radio_grp_funct.setOnCheckedChangeListener(RadioGroup.OnCheckedChangeListener { group, checkedId ->
            when (checkedId) {
                radio1_funct.id ->{ type = "Sealed Classes"
                    edit1.setHint("tap the button")
                    edit2.setHint("tap the button")}
                radio2_funct.id -> {type = "InLine Function"
                    edit1.setHint("Enter Number")
                    edit2.setHint("Enter to check if multiple")}
                radio3_funct.id -> {type = "InFlix Function"
                    edit1.setHint("Enter boolean value")
                    edit2.setHint("Enter boolean value")}
                radio4_funct.id -> {type = "Lambda Expression"
                    edit1.setHint("Enter number")
                    edit2.setHint("Enter number to multiply")}
                radio5_funct.id -> {
                    type = "Tail Recurssion"
                    edit1.setHint("Enter number")
                    edit2.setHint("result")}


            }
            label2_funct.text = type
        })


    }

    fun doSomething(view: View) {
        if (type.equals("Lambda Expression")) {
            var result = mul(
                Integer.parseInt(edit1.text.toString()),
                Integer.parseInt(edit2.text.toString())
            )
            label2_funct.text = type + " and result =" + result

        } else if (type.equals("Sealed Classes")) {
            label2_funct.text = type
            edit1.setText("The Selected Color ")
            val r = Color.Red()
            eval(r)
        } else if (type.equals("InLine Function")) {

            if (isMultipleOf(
                    Integer.parseInt(edit1.text.toString()),
                    Integer.parseInt(edit2.text.toString())
                )
            )
                label2_funct.text = type + "  yes it is multiple"
            else label2_funct.text = type + "  no it is not a multiple"
        } else if (type.equals("InFlix Function")) {


            val a = edit1.text.toString().toBoolean()
            val b =edit2.text.toString().toBoolean()
            var result1: Boolean
            var result2: Boolean
            result1 = a or b // a.or(b)
            label2_funct.text= result1.toString()+"   For OR operator"

            result2 = a and b // a.and(b)
            edit1.setText("For AND operator")
            edit2.setText(result2.toString())
        } else if (type.equals("Tail Recurssion")) {
            edit1.setText("Factorial of ${edit1.text.toString()}")
            edit2.setText("result"+factorial(Integer.parseInt(edit1.text.toString())))
        }

    }

    val mul: (Int, Int) -> Int =
        { a, b -> a * b }

    sealed class Color {
        class Red : Color()
        class Orange : Color()
        class Blue : Color()
    }

    fun eval(c: Color) =
        when (c) {
            is Color.Red -> {
                edit2.setText("Red")
            }
            is Color.Orange -> edit2.setText("Orange")
            is Color.Blue -> edit2.setText("Blue")
        }

    inline fun isMultipleOf(number: Int, multipleOf: Int): Boolean {
        return number % multipleOf == 0
    }

    tailrec fun factorial(n: Int, run: Int = 1): Int {
        return if (n == 1) run else factorial(n-1, run*n)
    }

}
