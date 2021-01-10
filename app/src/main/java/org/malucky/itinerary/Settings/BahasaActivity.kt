package org.malucky.itinerary.Settings

import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.res.Configuration
import android.widget.RadioButton
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_bahasa.*
import org.malucky.itinerary.BaseActivity
import org.malucky.itinerary.R
import org.malucky.itinerary.common.constant.LocalizationUtil
import java.util.*


class BahasaActivity : BaseActivity() {
    val bahasa = arrayOf("Indonesia", "English")

    override fun getView(): Int = R.layout.activity_bahasa

    override fun onActivityCreated() {

        loadLocale()
        toolbar_bahasa.setTitle(getString(R.string.lang))
        toolbar_bahasa.setNavigationIcon(R.drawable.ic_arrow_back_white_24dp)
        toolbar_bahasa.setNavigationOnClickListener {
            finish()
        }

        tv_lang_choice.setOnClickListener {
            showChangeLang()
        }

    }

    private fun showChangeLang() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle(getString(R.string.pilih_bahasa))
        builder.setSingleChoiceItems(bahasa, -1){ dialog, which ->
            if (which == 0){
                setLocate("in-rID")
                recreate()
            }else if (which == 1){
                setLocate("en")
                recreate()
            }

            dialog.dismiss()
        }
        val  mDialog = builder.create()
        mDialog.show()
    }

    private fun setLocate(lang: String) {
        val locale = Locale(lang)
        Locale.setDefault(locale)
        val config = Configuration()
        config.locale = locale
        baseContext.resources.updateConfiguration(config, baseContext.resources.displayMetrics)

        val editor = getSharedPreferences("Settingslang", Context.MODE_PRIVATE).edit()
        editor.putString("bahasa", lang)
        editor.apply()
    }

    private fun loadLocale(){
        val sharedPreferences = getSharedPreferences("Settingslang", Activity.MODE_PRIVATE)
        val language = sharedPreferences.getString("bahasa","")
        setLocate(language!!)
    }

}