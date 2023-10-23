package com.example.lesson1igor

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.KeyEvent
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity


class ThirdActivity:AppCompatActivity()
{
    private var students:HashMap<Long,Student> = hashMapOf()
    companion object
    {
        fun createIntent(context: Context) = Intent(context, ThirdActivity::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.third_act)
        val studentList = findViewById<TextView>(R.id.tvTA)
        val editText = findViewById<EditText>(R.id.etTA)

        editText.setOnKeyListener { _, keyCode, keyEvent ->
            if (keyEvent.action == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_ENTER){
                val student = editText.text.toString().split(" ")
                try {
                    students[System.currentTimeMillis()] = Student(
                        id = System.currentTimeMillis(),
                        name = student[0],
                        surname = student[1],
                        grade = student[2],
                        birthdayYear = student[3]
                    )
                }catch (e:IndexOutOfBoundsException){
                    Toast.makeText(this, "invalid data", Toast.LENGTH_LONG).show()
                }
                editText.text.clear()
                return@setOnKeyListener true
            }
            false
        }

        findViewById<Button>(R.id.getStudents).setOnClickListener{
            if (!students.isNullOrEmpty()) {
                studentList.text=""
                for (student in students)
                {
                    studentList.append("id = ${student.value.id}\nname = ${student.value.name}\n${student.value.surname}\ngrade = ${student.value.grade} \nbirthday year = ${student.value.birthdayYear}\n")
                }
            }
        }
    }
}

