package com.example.lesson1igor

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import java.lang.Exception
import java.util.TreeSet

class SecondActivity:AppCompatActivity()
{
    private var students: TreeSet<String>? = null

    companion object {
        fun createIntent(context: Context) = Intent(context, SecondActivity::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.second_act)
        val editText = findViewById<EditText>(R.id.etSA)
        val studentList=findViewById<TextView>(R.id.tvSA)

        students = TreeSet()

        findViewById<Button>(R.id.addStudent).setOnClickListener {
            try{
                if(!editText.text.toString().trim().equals("")) {
                    students?.add(editText.text.toString())
                    editText.text.clear()
                }
                else
                {
                    Toast.makeText(this, "Enter name", Toast.LENGTH_SHORT).show()
                }
            }
            catch (e:Exception)
            {
                Toast.makeText(this, e.message, Toast.LENGTH_SHORT).show()
            }

        }

        findViewById<Button>(R.id.getStudentsSA).setOnClickListener{
            if (!students.isNullOrEmpty()) {
                Log.i("Size students","${students?.size}")
                studentList.text=""
                for (student in students!!)
                {
                    studentList.append("$student\n")
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        students = null
    }
}