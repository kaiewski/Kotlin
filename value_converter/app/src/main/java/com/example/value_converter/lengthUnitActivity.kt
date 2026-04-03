package com.example.value_converter

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.widget.doAfterTextChanged

class lengthUnitActivity : AppCompatActivity() {

    private val convertedValues = arrayListOf<String>("km", "m", "cm", "mm", "ft", "in", "mi", "yd")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_length)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val unitsFromSpinner: Spinner = findViewById(R.id.spinnerUnitsFrom)
        val unitsToSpinner: Spinner = findViewById(R.id.spinnerUnitsTo)
        val valueFrom: EditText = findViewById(R.id.unitsFrom)
        val valueTo: TextView = findViewById(R.id.unitsTo)
        val currencyButton: Button = findViewById(R.id.currencyConverter)
        setupUnits(unitsFromSpinner, unitsToSpinner)

        currencyButton.setOnClickListener {
            val intent = Intent(this, currencyUnitActivity::class.java)
            startActivity(intent)
        }

        val itemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                valueTo.text = calculate()
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
                // Required, but you can add some options here
            }
        }

        valueFrom.doAfterTextChanged {  editable ->
            valueTo.text = calculate()
        }

        unitsFromSpinner.onItemSelectedListener = itemSelectedListener
        unitsToSpinner.onItemSelectedListener = itemSelectedListener
    }

    private fun calculate(): String{
        val _valueFrom = findViewById<EditText>(R.id.unitsFrom).text.toString()
        val _unitsFromSpinner = findViewById<Spinner>(R.id.spinnerUnitsFrom).selectedItem.toString()
        val _unitsToSpinner = findViewById<Spinner>(R.id.spinnerUnitsTo).selectedItem.toString()
        return ConversationUtils.calculateUnits(_unitsFromSpinner, _unitsToSpinner, _valueFrom, "0")
    }
    private fun setupUnits (unitsFrom: Spinner, unitsTo: Spinner){
        val listUnits = arrayListOf<String>()
        for (key in convertedValues){
            listUnits.add(key)
        }

        val arrayAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, listUnits)
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        unitsFrom.adapter = arrayAdapter
        unitsTo.adapter = arrayAdapter
    }
}