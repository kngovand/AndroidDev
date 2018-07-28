package com.example.kevin.menuaction

import android.graphics.Color
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.text.InputType
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.EditText
import android.widget.TextView
import android.widget.ToggleButton

import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*

class MainActivity : AppCompatActivity() {



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)



        toggleButton1.setOnCheckedChangeListener{buttonView, isChecked ->
            if(isChecked) textView2.visibility = View.INVISIBLE
            else textView2.visibility = View.VISIBLE
        }

        toggleButton2.setOnCheckedChangeListener { buttonView, isChecked ->
            if(isChecked)textView2.setTextColor(Color.BLUE)
            else textView2.setTextColor(Color.BLACK)

        }

    }


    private fun helpMessageSelected() {

        textView1.visibility = View.INVISIBLE
        textView2.visibility = View.VISIBLE

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        return when (item.itemId) {
            R.id.action_help -> {

                helpMessageSelected()
                return true

            }
            else -> super.onOptionsItemSelected(item)
        }
    }


}
