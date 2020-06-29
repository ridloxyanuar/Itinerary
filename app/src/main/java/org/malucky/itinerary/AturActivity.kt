package org.malucky.itinerary

import android.content.Intent
import android.util.Log
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.work.Data
import androidx.work.ExistingWorkPolicy
import androidx.work.OneTimeWorkRequest
import androidx.work.WorkManager
import com.google.firebase.Timestamp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import io.reactivex.Completable
import io.reactivex.CompletableObserver
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_atur.*
import org.malucky.itinerary.Presenters.TimeLineAdapter
import org.malucky.itinerary.Presenters.TimeLineRatingAdapter
import org.malucky.itinerary.data.Journey
import org.malucky.itinerary.db.CartDatabase
import org.malucky.itinerary.db.CartLocation
import org.malucky.itinerary.util.NotifyWork
import org.malucky.itinerary.util.NotifyWork.Companion.NOTIFICATION_WORK
import java.util.*
import java.util.concurrent.TimeUnit
import kotlin.Comparator
import kotlin.collections.ArrayList


class AturActivity : BaseActivity(), TimeLineAdapter.OnItemClickListner, SingleChoice.SingleChoiceListener{

    private lateinit var mLayoutManager: LinearLayoutManager
    private var userDatabase: CartDatabase? = null



    override fun getView(): Int = R.layout.activity_atur

    override fun onActivityCreated() {
        toolbar_atur.setTitle("Hasil Rekomendasi")
        setSupportActionBar(toolbar_atur)

        toolbar_atur.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp)
        toolbar_atur.setNavigationOnClickListener {
            finish()
        }

        userDatabase = CartDatabase.getInstance(this)


        setupFrogoRecyclerView()
        setupFrogoRecyclerView2()

        button.setOnClickListener {
            //buat jadwal baru
            newSchedule()
        }

        button2.setOnClickListener {
            val singleChoiceDialog: DialogFragment = SingleChoice()
            singleChoiceDialog.setCancelable(false)
            singleChoiceDialog.show(supportFragmentManager, "Single Choice Dialog")        }
    }

    private fun newSchedule() {
        Completable.fromAction {
            userDatabase!!.cartLocationDatabaseDAO.clear()
        }.observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe(object : CompletableObserver {
                override fun onSubscribe(d: Disposable) {}

                override fun onComplete() {
                    val intent = Intent(this@AturActivity, MainActivity::class.java)
                    startActivity(intent)
                }

                override fun onError(e: Throwable) {

                }
            })
    }

    private fun setupFrogoRecyclerView2() {
        val getIntentStringChartLocation = getIntent().getStringExtra("EXTRA_NAME")
        val listType = object : TypeToken<ArrayList<CartLocation>?>() {}.type
        val listDataChartLocation =
            Gson().fromJson<List<CartLocation>>(getIntentStringChartLocation, listType)

        val sortrating = listDataChartLocation.sortedByDescending { it.rate }


        mLayoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)

        rv_atur2.apply {
            layoutManager = mLayoutManager
            adapter = TimeLineRatingAdapter(this@AturActivity, sortrating, this@AturActivity)
        }

    }

    private fun setupFrogoRecyclerView() {
        val getIntentStringChartLocation = getIntent().getStringExtra("EXTRA_NAME")
        val listType = object : TypeToken<ArrayList<CartLocation>?>() {}.type
        val listDataChartLocation = Gson().fromJson<List<CartLocation>>(getIntentStringChartLocation, listType)

        val sortjarak = listDataChartLocation.sortedWith(object : Comparator<CartLocation>{
            override fun compare(o1: CartLocation?, o2: CartLocation?): Int {
                return (cleaningJarak(o1?.jarak!!) - cleaningJarak(o2?.jarak!!))
            }

        })


        mLayoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)

        rv_atur.apply {
            layoutManager = mLayoutManager
            adapter = TimeLineAdapter(this@AturActivity, sortjarak, this@AturActivity)
        }

    }



    override fun onItemClick(item: List<CartLocation?>, position: Int) {
        val namaLokasi = item.get(position)!!.namaLokasi

        }

    private fun scheduleNotification(data: Data, delay: Long) {
        val notificationWork = OneTimeWorkRequest.Builder(NotifyWork::class.java)
            .setInitialDelay(delay, TimeUnit.MILLISECONDS).setInputData(data).build()

        val instanceWorkManager = WorkManager.getInstance(this)
        instanceWorkManager.beginUniqueWork(NOTIFICATION_WORK, ExistingWorkPolicy.REPLACE, notificationWork).enqueue()
    }

    private fun cleaningJarak(jarak: String): Int {
        return jarak.replace(" m", "").toInt()
    }


    override fun onPositiveButtonClicked(list: Array<String?>?, position: Int) {
        val userID = FirebaseAuth.getInstance().currentUser!!.uid
        firebaseFirestore = FirebaseFirestore.getInstance()
        storageReference = FirebaseStorage.getInstance().reference

        val pil = list!![position]
        if (pil!!.equals("Rekomendasi Berdasarkan Jarak")){
            //jarak
            val getIntentStringChartLocation = getIntent().getStringExtra("EXTRA_NAME")
            val listType = object : TypeToken<ArrayList<CartLocation>?>() {}.type
            val listDataChartLocation = Gson().fromJson<List<CartLocation>>(getIntentStringChartLocation, listType)

            val sortjarak = listDataChartLocation.sortedWith(object : Comparator<CartLocation>{
                override fun compare(o1: CartLocation?, o2: CartLocation?): Int {
                    return (cleaningJarak(o1?.jarak!!) - cleaningJarak(o2?.jarak!!))
                }
            })


            for (i in 0 until sortjarak.size){
                val namaLok = sortjarak.get(i).namaLokasi
                val jarakLok = sortjarak.get(i).jarak
                val latitudeLok = sortjarak.get(i).latitude
                val longitudeLok = sortjarak.get(i).longitude
                val rateLok = sortjarak.get(i).rate

                val journeyJarak = Journey(namaLok, jarakLok, rateLok, latitudeLok, longitudeLok, false, Timestamp(Date()), userID)

                firebaseFirestore.collection("Journey")
                    .add(journeyJarak)
                    .addOnCompleteListener {
                        val intent = Intent(this, MainActivity::class.java)
                        startActivity(intent)
//                        finish()
                        Log.d("OnSucces", "Journey added successfully")
                    }
                    .addOnFailureListener {
                        Log.d("OnFailure", it.localizedMessage!!)
                    }
            }


            //////////////////////////////////////////////////////////////////////////////////////
            Toast.makeText(this, "Jarak", Toast.LENGTH_SHORT).show()
        }else{
            //rating
            val getIntentStringChartLocation = getIntent().getStringExtra("EXTRA_NAME")
            val listType = object : TypeToken<ArrayList<CartLocation>?>() {}.type
            val listDataChartLocation =
                Gson().fromJson<List<CartLocation>>(getIntentStringChartLocation, listType)

            val sortrating = listDataChartLocation.sortedByDescending { it.rate }


            for (i in 0 until sortrating.size){
                val namaLok = sortrating.get(i).namaLokasi
                val jarakLok = sortrating.get(i).jarak
                val latitudeLok = sortrating.get(i).latitude
                val longitudeLok = sortrating.get(i).longitude
                val rateLok = sortrating.get(i).rate

                val journey = Journey(namaLok, jarakLok, rateLok, latitudeLok, longitudeLok, false, Timestamp(Date()), userID)

                firebaseFirestore.collection("Journey")
                    .add(journey)
                    .addOnCompleteListener {
                        val intent = Intent(this, MainActivity::class.java)
                        startActivity(intent)
//                        finish()
                        Log.d("OnSucces", "Journey added successfully")
                    }
                    .addOnFailureListener {
                        Log.d("OnFailure", it.localizedMessage!!)
                    }
            }

            //////////////////////////////////////////////////////////////////////////////////////
            Toast.makeText(this, "Rating", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onNegativeButtonClicked() {
        Toast.makeText(this, "Cancelled", Toast.LENGTH_SHORT).show()
    }

}
