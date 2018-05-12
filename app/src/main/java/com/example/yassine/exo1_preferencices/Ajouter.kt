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




class Ajouter : AppCompatActivity() {
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
        IOhelper.writeToFile(this, "interventions.txt", this.toJsonString())

    }

    fun toJsonString(): String {

        return "{ \"numero\": \"ES\",\n" +
                "    \"date\": \"Valencia\",\n" +
                "    \"nomPlombier\": \"yassine\",\n" +
                "    \"typeIntervention\":\"D\"}"
    }




}
