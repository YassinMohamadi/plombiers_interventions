package com.example.yassine.exo1_preferencices

import android.content.Context
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ArrayAdapter
import android.widget.DatePicker
import android.widget.EditText
import android.widget.Spinner
import kotlinx.android.synthetic.main.activity_ajouter.*
import org.json.JSONObject
import java.io.BufferedReader
import java.io.FileNotFoundException
import java.io.IOException
import java.io.InputStreamReader

class Modifier : AppCompatActivity() {

    private var fileName: String = "interventions.json"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_modifier)

        val spinnerNum = findViewById<Spinner>(R.id.multi_spinner_num)
        val numArray = arrayListOf<String>()


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
        i=0
        val sb = StringBuilder()
        for (i in arrayJS.indices ){
            numArray.add(arrayJS[i].getString("numero"))
        }



        val dataAdapterN = ArrayAdapter(this, android.R.layout.simple_spinner_item, numArray)
        dataAdapterN.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerNum.adapter = dataAdapterN

        val spinnerRegion = findViewById<Spinner>(R.id.multi_spinner)
        val lRegion = arrayOf("A", "B", "C")
        val dataAdapterR = ArrayAdapter(this, android.R.layout.simple_spinner_item, lRegion)
        dataAdapterR.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerRegion.adapter = dataAdapterR



    }

    fun modifier(view: View) {
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
        val fos = openFileOutput(fileName, Context.MODE_PRIVATE)
        var typeSpiner:Spinner = findViewById(R.id.multi_spinner_num)
        var numIntervention = typeSpiner.selectedItem.toString()
        i=0
        val sb = StringBuilder()
        for (i in arrayJS.indices ){
            if (arrayJS[i].getString("numero")!= numIntervention){
                var str:String =""
                str = this.toJsonString(arrayJS[i].getString("numero"),arrayJS[i].getString("date"),arrayJS[i].getString("nomPlombier"),arrayJS[i].getString("typeIntervention"))
                fos.write(str.toByteArray() , 0, str.length)
            }else{
                var numeroET:EditText = findViewById(R.id.num)
                var nomPET:EditText = findViewById(R.id.nomP)
                var typeSpiner:Spinner = findViewById(R.id.multi_spinner)
                var dp: DatePicker = findViewById(R.id.datePicker2)


                var numero = numeroET.text.toString()
                var nomP = nomPET.text.toString()
                var typeIntervention = typeSpiner.selectedItem.toString()
                var date = "j" +dp.dayOfMonth.toString() + "m" + (dp.month+1).toString() + "a" + dp.year.toString()

                if (numero == ""){
                    numero = arrayJS[i].getString("numero")
                }
                if (nomP == ""){
                    nomP = arrayJS[i].getString("nomPlombier")
                }
                if (typeIntervention == ""){
                    typeIntervention = arrayJS[i].getString("typeIntervention")
                }
                if (date == ""){
                    date = arrayJS[i].getString("date")
                }
                var str:String =""
                str = this.toJsonString(numero,date,nomP,typeIntervention)
                fos.write(str.toByteArray() , 0, str.length)
            }
        }
        fos.close()

    }

    fun toJsonString(numero:String,nomP:String, date:String, typeIntervention:String): String {

        return "{ \"numero\": "+ numero +",\n" +
                "    \"date\": "+ date +",\n" +
                "    \"nomPlombier\": "+ nomP +",\n" +
                "    \"typeIntervention\":"+ typeIntervention + "}"+"\n"
    }
}
