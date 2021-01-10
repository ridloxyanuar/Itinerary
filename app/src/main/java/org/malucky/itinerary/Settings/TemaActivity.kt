package org.malucky.itinerary.Settings

import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.RadioButton
import androidx.appcompat.app.AppCompatDelegate
import kotlinx.android.synthetic.main.activity_settings.*
import kotlinx.android.synthetic.main.activity_tema.*
import org.malucky.itinerary.BaseActivity
import org.malucky.itinerary.R

class TemaActivity : BaseActivity() {
    override fun getView(): Int = R.layout.activity_tema

    override fun onActivityCreated() {
        //darkmode
        val appSettingPrefs: SharedPreferences = getSharedPreferences("AppSettingPrefs", 0)
        val sharedPrefsEdit: SharedPreferences.Editor = appSettingPrefs.edit()
        val isNightModeOn: Boolean = appSettingPrefs.getBoolean("NightMode", false)

        if(isNightModeOn){
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        }

        toolbar_tema.setTitle("Tema")
        toolbar_tema.setNavigationIcon(R.drawable.ic_arrow_back_white_24dp)
        toolbar_tema.setNavigationOnClickListener {
            finish()
        }

        radioGroup.check(appSettingPrefs.getInt("CheckedID", 0))

        radioGroup.setOnCheckedChangeListener { _, checkedId ->
            val radio : RadioButton = findViewById(checkedId)
            when(radio){
                rb_terang -> {
                    //terang
                    if (isNightModeOn){
                        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                        sharedPrefsEdit.putBoolean("NightMode", false)
                        sharedPrefsEdit.putInt("CheckedID", checkedId)
                        sharedPrefsEdit.apply()
                    }
                }

                rb_gelap -> {
                    //dark mode
                    if (!isNightModeOn){
                        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                        sharedPrefsEdit.putBoolean("NightMode", true)
                        sharedPrefsEdit.putInt("CheckedID", checkedId)
                        sharedPrefsEdit.apply()
                    }
                }
            }
        }



    }

}