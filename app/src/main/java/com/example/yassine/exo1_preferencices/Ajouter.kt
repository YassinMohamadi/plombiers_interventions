package com.example.yassine.exo1_preferencices

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.mikepenz.fastadapter.adapters.ItemAdapter.items
import android.widget.ArrayAdapter
import android.widget.Spinner
import org.json.JSONObject
import android.content.res.AssetManager
import android.util.Log
import java.io.*
import android.util.JsonWriter
import android.widget.DatePicker
import android.widget.EditText


class Ajouter : AppCompatActivity() {

    private var fileName:String = "interventions.json"

    private val items: List<String>? = arrayListOf("A", "B", "C")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ajouter)
        val spinnerRegion = findViewById<Spinner>(R.id.multi_spinner)
        val lRegion = arrayOf("A", "B", "C")
        val dataAdapterR = ArrayAdapter(this, android.R.layout.simple_spinner_item, lRegion)
        dataAdapterR.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerRegion.adapter = dataAdapterR

    }

    fun writeJson(view: View) {
        var numeroET:EditText = findViewById(R.id.num)
        var nomPET:EditText = findViewById(R.id.nomP)
        var typeSpiner:Spinner = findViewById(R.id.multi_spinner)
        var dp:DatePicker = findViewById(R.id.datePicker2)


        var numero = numeroET.text.toString()
        var nomP = nomPET.text.toString()
        var typeIntervention = typeSpiner.selectedItem.toString()
        var date = "j" +dp.dayOfMonth.toString() + "m" + (dp.month+1).toString() + "a" + dp.year.toString()



        IOhelper.writeToFile(this, this.fileName, this.toJsonString(numero,nomP,date,typeIntervention))

    }

    fun toJsonString(numero:String,nomP:String, date:String, typeIntervention:String): String {

        return "{ \"numero\": "+ numero +",\n" +
                "    \"date\": "+ date +",\n" +
                "    \"nomPlombier\": "+ nomP +",\n" +
                "    \"typeIntervention\":"+ typeIntervention + "}"+"\n"
    }




}
