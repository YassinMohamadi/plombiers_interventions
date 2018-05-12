package com.example.yassine.exo1_preferencices

import android.content.Context
import android.content.res.AssetManager
import android.support.v7.app.AppCompatActivity
import android.util.Log
import java.io.*

//import jdk.nashorn.internal.runtime.ScriptingFunctions.readLine


/**
 * Created by Yassine on 4/28/2018.
 */
class IOhelper {


    companion object {

        fun writeToFile(context: Context, fileName: String, str: String) {
            try {
                val fos = context.openFileOutput(fileName, Context.MODE_PRIVATE)
                fos.write(str.toByteArray(), 0, str.length)
                Log.d("oui","kteb")
                fos.close()
            } catch (e: IOException) {
                e.printStackTrace()
            }

        }

        fun stringFromStream(`is`: InputStream): String {
            try {
                val reader = BufferedReader(InputStreamReader(`is`))
                val sb = StringBuilder()
                var line: String? = null

                line = reader.readLine()
                while (line != null) {
                    sb.append(line).append("\n")
                    line = reader.readLine()
                }
                reader.close()
                return sb.toString()
            } catch (e: IOException) {
                e.printStackTrace()
            }

            return ""
        }

        fun stringFromAsset(context: Context, assetFileName: String): String {
            val am = context.assets
            try {
                val `is` = am.open(assetFileName)
                val result = this.stringFromStream(`is`)
                `is`.close()
                return result
            } catch (e: Exception) {
                e.printStackTrace()
            }

            return ""
        }

    }


}