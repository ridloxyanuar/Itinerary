package org.malucky.itinerary

import android.Manifest
import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import org.malucky.itinerary.Presenters.KategoriAdapter
import org.malucky.itinerary.data.Kategori
import org.malucky.itinerary.reusable.Navigator

abstract class BaseFragment: Fragment() {
    var navigate: Navigator = Navigator()
    var categoryItem: ArrayList<Kategori>? = null
    var gridLayoutManager: GridLayoutManager? = null
    var kategoriAdapters: KategoriAdapter? = null
    lateinit var auth: FirebaseAuth


    abstract fun getViewId(): Int

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        onFragmentCreated()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(getViewId(), container, false)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    abstract fun onFragmentCreated()
    fun Context.hideKeyboard(view: View) {
        val inputMethodManager = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
    }

    protected fun signOut(){
        auth.signOut()
        navigate.signin(activity!!)
    }

}