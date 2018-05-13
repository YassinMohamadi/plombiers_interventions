package com.example.yassine.exo1_preferencices

import android.app.Activity
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import org.json.JSONObject
import android.widget.TextView
import co.zsmb.materialdrawerkt.builders.drawer
import co.zsmb.materialdrawerkt.draweritems.badgeable.primaryItem
import co.zsmb.materialdrawerkt.draweritems.sectionHeader
import com.mikepenz.google_material_typeface_library.GoogleMaterial
import com.mikepenz.material_design_iconic_typeface_library.MaterialDesignIconic
import com.mikepenz.materialdrawer.Drawer
import kotlinx.android.synthetic.main.activity_main.*
import kotlin.reflect.KClass
import java.io.*



class MainActivity : AppCompatActivity() {

    private var fileName: String = "interventions.json"
    private lateinit var result: Drawer

    private var txtJson: TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
        getSupportActionBar()?.setDisplayShowTitleEnabled(false)
        toolbar.setTitle("")
        toolbar.setSubtitle("")

        if (isFilePresent()) {
            /*var fos: FileOutputStream? = null
            fos = openFileOutput(fileName, Context.MODE_PRIVATE)*/
        } else {
            Log.d("exist", "No")
        }

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
                iicon = GoogleMaterial.Icon.gmd_add
                onClick(openActivity(Ajouter::class, "1"))
            }
            primaryItem("Modifier") {
                iicon = GoogleMaterial.Icon.gmd_settings
                onClick(openActivity(Modifier::class,"2"))
            }
            primaryItem("Supprimer") {
                iicon = GoogleMaterial.Icon.gmd_delete
                onClick(openActivity(Delete::class, "5"))
            }
            primaryItem("Rechercher intervention") {
                iicon = GoogleMaterial.Icon.gmd_search
                onClick(openActivity(Rechercher::class, "5"))
            }


        }
    }

    fun readJson(view: View) {
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
        Log.e("la taille est", arrayJS.size.toString() )
        i=0
        val sb = StringBuilder()
        for (i in arrayJS.indices ){
            sb.append("Numero: "+ arrayJS[i].getString("numero"))
            sb.append(" ")
            sb.append("| Date: "+arrayJS[i].getString("date"))
            sb.append(" ")
            sb.append("| Nom Plombier: "+ arrayJS[i].getString("nomPlombier"))
            sb.append(" ")
            sb.append("| Type d'intervention: "+ arrayJS[i].getString("typeIntervention"))
            sb.append("\n")
        }
        txtJson = findViewById(R.id.txtJson)
        ret = sb.toString()
        txtJson?.text = ret
    }


    private fun <T : Activity> openActivity(activity: KClass<T>, a: String): (View?) -> Boolean = {
        var myIntent: Intent = Intent(this@MainActivity, activity.java)
        myIntent.putExtra("code", a)
        startActivity(myIntent)
        false
    }

    fun isFilePresent(): Boolean {
        val path = this.getFilesDir().getAbsolutePath() + "/" + "interventions.json"
        val file = File(path)
        return file.exists()
    }

}
