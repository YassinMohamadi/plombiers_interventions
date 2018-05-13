package com.example.yassine.exo1_preferencices

import android.content.Context
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.DatePicker
import android.widget.EditText
import android.widget.Spinner
import org.json.JSONObject
import java.io.BufferedReader
import java.io.FileNotFoundException
import java.io.IOException
import java.io.InputStreamReader

class Delete : AppCompatActivity() {
    private var fileName:String = "interventions.json"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_delete)
    }

    fun delete(view: View) {
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
        var ed:EditText = findViewById(R.id.numDel)
        var numAsup = ed.text.toString()
        i=0
        val sb = StringBuilder()
        for (i in arrayJS.indices ){
            if (arrayJS[i].getString("numero")!= numAsup){
                var str:String =""
                str = this.toJsonString(arrayJS[i].getString("numero"),arrayJS[i].getString("date"),arrayJS[i].getString("nomPlombier"),arrayJS[i].getString("typeIntervention"))
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
