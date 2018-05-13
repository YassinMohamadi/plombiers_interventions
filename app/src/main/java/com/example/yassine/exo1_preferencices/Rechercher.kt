package com.example.yassine.exo1_preferencices

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.DatePicker
import android.widget.TextView
import org.json.JSONObject
import java.io.BufferedReader
import java.io.FileNotFoundException
import java.io.IOException
import java.io.InputStreamReader

class Rechercher : AppCompatActivity() {


    private var fileName:String = "interventions.json"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_rechercher)
    }


    fun rechercher(view: View) {
        var i:Int=0
        var arrayJS = arrayListOf<JSONObject>()
        var ret = ""
        try {
            val inputStream = openFileInput(this.fileName)
            if (inputStream != null) {
                val inputStreamReader = InputStreamReader(inputStream)
                val bufferedReader = BufferedReader(inputStreamReader)
                var receiveString:String? = null
                val stringBuilder = StringBuilder()

                while ({ receiveString = bufferedReader.readLine(); receiveString }() != null) {
                    stringBuilder.append(receiveString)
                    i++
                    if(i==4){
                        ret = stringBuilder.toString()
                        stringBuilder.setLength(0)
                        val js = JSONObject(ret)
                        arrayJS.add(js)
                        i=0

                    }
                }

                inputStream.close()

            }
        } catch (e: FileNotFoundException) {
            Log.e("login activity", "File not found: " + e.toString())
        } catch (e: IOException) {
            Log.e("login activity", "Can not read file: " + e.toString())
        }

        //return ret

        var dp: DatePicker = findViewById(R.id.datePicker)
        var date = "j" +dp.dayOfMonth.toString() + "m" + (dp.month+1).toString() + "a" + dp.year.toString()

        i=0
        val sb = StringBuilder()
        for (i in arrayJS.indices ){
            if (arrayJS[i].getString("date")== date){

                sb.append("Numero: "+ arrayJS[i].getString("numero"))
                sb.append(" ")
                sb.append("| Date: "+arrayJS[i].getString("date"))
                sb.append(" ")
                sb.append("| Nom Plombier: "+ arrayJS[i].getString("nomPlombier"))
                sb.append(" ")
                sb.append("| Type d'intervention: "+ arrayJS[i].getString("typeIntervention"))
                sb.append("\n")
            }
        }
        var txtJson:TextView = findViewById(R.id.afficherExister)
        ret = sb.toString()
        txtJson?.text = ret
    }
}
