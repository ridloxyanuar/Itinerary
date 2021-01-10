package org.malucky.itinerary

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_my_journey.*
import kotlinx.android.synthetic.main.activity_settings.*
import kotlinx.android.synthetic.main.fragment_home.*
import org.malucky.itinerary.Presenters.KategoriAdapter
import org.malucky.itinerary.Presenters.SettingsAdapter
import org.malucky.itinerary.data.Kategori
import org.malucky.itinerary.data.Settings

class SettingsActivity : BaseActivity() {

    override fun getView(): Int = R.layout.activity_settings

    override fun onActivityCreated() {

        toolbar_settings.setTitle(getString(R.string.setting))
        toolbar_settings.setNavigationIcon(R.drawable.ic_arrow_back_white_24dp)
        toolbar_settings.setNavigationOnClickListener {
            finish()
        }

        val linearLayoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        rv_settings.layoutManager = linearLayoutManager
        val divider = DividerItemDecoration(this,DividerItemDecoration.VERTICAL)
        divider.setDrawable(ContextCompat.getDrawable(this,R.drawable.item_selector)!!)
        rv_settings.addItemDecoration(divider)

        rv_settings.setHasFixedSize(true)

        settingsItem = ArrayList()
        settingsItem = setSettings()
        val settingsAdapter = SettingsAdapter(this, settingsItem!!)
        rv_settings.adapter = settingsAdapter



    }

    private fun setSettings(): ArrayList<Settings> {
        var arrayList: ArrayList<Settings> = ArrayList()

        arrayList.add(Settings(R.drawable.ic_baseline_translate_24, getString(R.string.set_bahasa), getString(
                    R.string.ket_bahasa)))
        arrayList.add(Settings(R.drawable.ic_baseline_brightness_4_24, getString(R.string.set_tema), getString(
                    R.string.ket_tema)))
        arrayList.add(Settings(R.drawable.ic_baseline_info_24, getString(R.string.about), "v3.0 Team MALUCKY 2020 "))


        return arrayList
    }

}