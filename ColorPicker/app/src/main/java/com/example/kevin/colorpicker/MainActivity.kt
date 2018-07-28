package com.example.kevin.colorpicker

import android.content.DialogInterface
import android.graphics.Color
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.text.Editable
import android.text.InputType
import android.text.TextWatcher
import android.view.Menu
import android.view.MenuItem
import android.view.SurfaceView
import android.widget.EditText
import kotlinx.android.synthetic.main.activity_main.*
import android.widget.SeekBar
import java.io.File
import java.util.*


class MainActivity : AppCompatActivity() {

    val FILENAME = "saved_colors.txt"
    var colorList = arrayListOf<String>()

    var red = 0
    var blue = 0
    var green = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
        val file = File(filesDir, FILENAME)
        if (!file.exists()) {
            val output = file.printWriter()
            output.print(" ")
            output.close()
        }

        var redBox = findViewById<EditText>(R.id.editText1)
        var greenBox = findViewById<EditText>(R.id.editText2)
        var blueBox = findViewById<EditText>(R.id.editText3)
        val colorView = findViewById<SurfaceView>(R.id.colorView)
        val redBar = findViewById<SeekBar>(R.id.redBar)
        val greenBar = findViewById<SeekBar>(R.id.greenBar)
        val blueBar = findViewById<SeekBar>(R.id.blueBar)

        redBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                red = progress
                setSurfaceViewColor(red, green, blue)
                redBox.setText(progress.toString())
            }

            override fun onStartTrackingTouch(p0: SeekBar?) {
            }

            override fun onStopTrackingTouch(p0: SeekBar?) {
            }
        })

        blueBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(SeekBar: SeekBar?, progress: Int, p2: Boolean) {
                blue = progress
                setSurfaceViewColor(red, green, blue)
                blueBox.setText(progress.toString())
            }

            override fun onStartTrackingTouch(p0: SeekBar?) {
            }

            override fun onStopTrackingTouch(p0: SeekBar?) {
            }
        })

        greenBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(SeekBar: SeekBar?, progress: Int, p2: Boolean) {
                green = progress
                setSurfaceViewColor(red, green, blue)
                greenBox.setText(progress.toString())
            }

            override fun onStartTrackingTouch(p0: SeekBar?) {
            }

            override fun onStopTrackingTouch(p0: SeekBar?) {
            }
        })

        editText1.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable) {
                try {
                    //Update seekbar value after entering a number
                    redBar.setProgress(Integer.parseInt(redBox.editableText.toString()))
                    redBox.setSelection(redBox.editableText.toString().length)
                } catch (ex: Exception) {

                }


            }
        })

        editText2.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable) {
                try {
                    //Update seekbar value after entering a number
                    greenBar.setProgress(Integer.parseInt(s.toString()))
                    greenBox.setSelection(greenBox.editableText.toString().length)
                } catch (ex: Exception) {
                }

            }
        })

        editText3.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable) {
                try {
                    //Update seekbar value after entering a number
                    blueBar.setProgress(Integer.parseInt(s.toString()))
                    blueBox.setSelection(blueBox.editableText.toString().length)
                }
                catch (ex: Exception) {
                }
            }
        })

    } // end OnCreate

    private fun setSurfaceViewColor(r: Int, g: Int, b: Int) {
        colorView.setBackgroundColor(Color.rgb(r, g, b))
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val builder = AlertDialog.Builder(this)

        return when (item.itemId) {
            R.id.action_save -> {
                var name: EditText? = null

                with(builder) {
                    setTitle("Save Color")
                    setMessage("Enter color name Below to save:")

                    name = EditText(context)
                    name!!.hint = "Color Name"
                    name!!.inputType = InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS

                    setPositiveButton("OK") { dialog, whichButton ->
                        colorList.add("${name!!.text}," + redBar.progress + "," + greenBar.progress + "," + blueBar.progress)
                        dialog.dismiss()
                    }

                    setNegativeButton("Cancel") { dialog, whichButton -> dialog.dismiss() }
                }

                builder.create()
                builder.setView(name)
                builder.show()
                return true
            }

            R.id.action_recall -> {
                with(builder) {
                    setTitle("Recall Color")


                    setItems(colorList.toTypedArray(), DialogInterface.OnClickListener({ dialog, which ->
                        var selectedColor = colorList.get(which)
                        var data = selectedColor.split(",")
                        editText1.setText(data[1])
                        editText2.setText(data[2])
                        editText3.setText(data[3])
                    }))

                    setPositiveButton("OK") { dialog, whichButton -> dialog.dismiss() }

                    setNegativeButton("Cancel") { dialog, whichButton -> dialog.dismiss() }
                }

                builder.create()
                builder.show()
                return true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onPause() {
        super.onPause()

        val file = File(filesDir, FILENAME)


        val output = file.printWriter()

        for (color in colorList) {
            output.println("$color")
        }

        output.flush()
        output.close()
    }

    override fun onResume() {
        super.onResume()

        val file = File(filesDir, FILENAME)
        val input = Scanner(file)

        while (input.hasNextLine()) {
            colorList.add(input.nextLine())
        }

        input.close()
    }
}
