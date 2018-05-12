package com.example.yassine.exo1_preferencices

import android.app.Activity
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.support.v7.widget.ThemedSpinnerAdapter
import android.util.Log
import android.view.View
import java.lang.reflect.Array.getDouble
import org.json.JSONObject
import org.json.JSONArray
import android.widget.TextView
import co.zsmb.materialdrawerkt.builders.drawer
import co.zsmb.materialdrawerkt.draweritems.badgeable.primaryItem
import co.zsmb.materialdrawerkt.draweritems.sectionHeader
import com.mikepenz.materialdrawer.Drawer
import kotlinx.android.synthetic.main.activity_main.*
import kotlin.reflect.KClass
import android.os.Environment.getDataDirectory
import android.support.v4.app.FragmentActivity
//import jdk.nashorn.internal.runtime.ScriptingFunctions.readLine
import java.io.*
import android.content.Context.MODE_PRIVATE
import android.system.Os.read
import java.nio.file.Files.exists








class MainActivity : AppCompatActivity() {
    private lateinit var result: Drawer

    private var txtJson: TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
        getSupportActionBar()?.setDisplayShowTitleEnabled(false)
        toolbar.setTitle("")
        toolbar.setSubtitle("")
        Log.d("pre",isFilePresent().toString())
        result = drawer {
            toolbar = this@MainActivity.toolbar
            hasStableIds = true
            savedInstance = savedInstanceState
            showOnFirstLaunch = true

            /*  accountHeader {
                  background = R.drawable.htwo
                  savedInstance = savedInstanceState
                  translucentStatusBar = true

                  profile("MOHAMADI Yassine", "em_mohamadi@esi.dz") {
                      iconUrl = "https://avatars3.githubusercontent.com/u/1476232?v=3&s=460"
                      identifier = 100
                  }

              }*/

            sectionHeader("Modifier les interventions") {
                divider = false
            }

            primaryItem("Ajouter") {
                //iicon = GoogleMaterial.Icon.gmd_movie
                onClick(openActivity(Ajouter::class, "1"))
            }
            primaryItem("Modifier") {
                //iicon = MaterialDesignIconic.Icon.gmi_live_tv
                //onClick(openActivity(MesFilms::class,"2"))
            }
            primaryItem("Supprimer") {
                //iicon = GoogleMaterial.Icon.gmd_room
                //onClick(openActivity(HeaderFooterActivity::class))
            }


        }
    }

    fun readJson(view: View) {

        var ret = ""

        try {
            val inputStream = openFileInput("interventions.json")

            if (inputStream != null) {
                val inputStreamReader = InputStreamReader(inputStream)
                val bufferedReader = BufferedReader(inputStreamReader)
                var receiveString = ""
                val stringBuilder = StringBuilder()
                receiveString = bufferedReader.readLine()
                while (receiveString != null) {
                    stringBuilder.append(receiveString)
                    receiveString = bufferedReader.readLine()
                }

                inputStream.close()
                ret = stringBuilder.toString()
            }
        } catch (e: FileNotFoundException) {
            Log.e("login activity", "File not found: " + e.toString())
        } catch (e: IOException) {
            Log.e("login activity", "Can not read file: " + e.toString())
        }


        txtJson = findViewById(R.id.txtJson)
        txtJson?.text = ret



        /*  var jsonString: String = IOhelper.stringFromAsset(this, "interventions.json")
        try {
            //JSONObject jsonObject = new JSONObject(jsonString);
            val interventions = JSONArray(jsonString)
            Log.d("oui", "fat")
            var result = ""
            for (i in 0 until interventions.length()) {
                val city = interventions.getJSONObject(i)
                //new Gson().fromJson(city.toString(), City.class);
                result += "Numero : " + city.getString("numero") + "\n" +
                        "Date : " + city.getString("date") + "\n" +
                        "Nom Plombier : " + city.getString("nomPlombier") + "\n" +
                        "Type : " + city.getString("typeIntervention") + "\n"
            }
            txtJson = findViewById(R.id.txtJson)
            txtJson?.text = result
        } catch (e: Exception) {
            Log.d("ReadPlacesFeedTask", e.localizedMessage)
            Log.d("non", "exception")
        }
*/
      /* try {
            val fis = this.openFileInput("interventions.json")
            val isr = InputStreamReader(fis)
            val bufferedReader = BufferedReader(isr)
            val sb = StringBuilder()
            var line: String
            line = bufferedReader.readLine()
            while (line != null) {
                sb.append(line)
                line = bufferedReader.readLine()
            }

           return sb.toString()
        } catch (fileNotFound: FileNotFoundException) {
            return ""
        } catch (ioException: IOException) {
            return ""
        }*/

    }


    private fun create(jsonString: String?): Boolean {
        val FILENAME = "storage.json"
        try {
            val fos = openFileOutput("interventions.json", 0)
            if (jsonString != null) {
                fos.write(jsonString.toByteArray())
            }
            fos.close()
            return true
        } catch (fileNotFound: FileNotFoundException) {
            return false
        } catch (ioException: IOException) {
            return false
        }

    }

    fun isFilePresent( ): Boolean {
        val path = this.getFilesDir().getAbsolutePath() + "/" + "interventions.json"
        val file = File(path)
        return file.exists()
    }

    private fun <T : Activity> openActivity(activity: KClass<T>, a: String): (View?) -> Boolean = {
        var myIntent: Intent = Intent(this@MainActivity, activity.java)
        myIntent.putExtra("code", a)
        startActivity(myIntent)
        false
    }
}
