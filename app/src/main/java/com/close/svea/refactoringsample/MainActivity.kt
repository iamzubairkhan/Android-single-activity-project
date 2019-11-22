package com.close.svea.refactoringsample

import Place
import Places
import android.content.Context
import android.net.ConnectivityManager
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.TextView
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

class MainActivity : AppCompatActivity(), View.OnClickListener {
    private val restClient: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl("https://secure.closepayment.com/close-admin/1.0/place/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        findViewById<RecyclerView>(R.id.places_recycler_view).layoutManager = LinearLayoutManager(applicationContext)
        findViewById<TextView>(R.id.button).setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        val networkInfo: ConnectivityManager? = (getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager)
        if (networkInfo?.activeNetworkInfo?.isConnected == true) {
            val job = GlobalScope.async(Dispatchers.IO) {
                restClient.create(GetPlaces::class.java).getAllPlaces().execute().body()!!.place
            }
            GlobalScope.launch(Dispatchers.Main) {
                val result = job.await()
                updateUi(result)
            }
        } else {
            findViewById<TextView>(R.id.message).text = "Not connected!"
            findViewById<TextView>(R.id.message).visibility = View.VISIBLE
        }

    }

    private fun updateUi(result: List<Place>) {

        if (result.isNotEmpty()) {
            findViewById<RecyclerView>(R.id.places_recycler_view).adapter = PlacesAdapter(result)
            findViewById<TextView>(R.id.message).visibility = View.GONE
        } else {
            findViewById<TextView>(R.id.message).text = "Try again!"
            findViewById<TextView>(R.id.message).visibility = View.VISIBLE
        }
    }

}

interface GetPlaces {

    @GET("meappid")
    fun getAllPlaces(
        @Query("meAppId") id: Int = 50,
        @Query("records") records: Int = 500
    ): Call<Places>
}
