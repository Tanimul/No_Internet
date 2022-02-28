package com.example.nointernet

import android.app.Dialog
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity


class MainActivity : AppCompatActivity() {
    lateinit var dialog: Dialog
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        dialog = Dialog(this)
        if (!isNetWorkConnected(this)) {
            Toast.makeText(this, "Not Connected", Toast.LENGTH_SHORT).show()
            showInternetDialoge()
        } else {
            Toast.makeText(this, "Connected", Toast.LENGTH_SHORT).show()
        }
    }

    fun isNetWorkConnected(context: Context): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo =
            connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
        networkInfo.also {
            if (it != null) {
                if (it.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)) {
                    return true
                } else if (it.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)) {
                    return true
                }
            }
        }

        return false
    }

    fun showInternetDialoge() {
        dialog.window!!.setBackgroundDrawableResource(android.R.color.transparent)
        val view = LayoutInflater.from(this)
            .inflate(R.layout.layout_no_internet, findViewById(R.id.no_internet_layout))
        view.findViewById<View>(R.id.tap_to_retry).setOnClickListener {
            if (!isNetWorkConnected(this@MainActivity)) {
                showInternetDialoge()
                Toast.makeText(this, "Not Connected", Toast.LENGTH_SHORT).show()
            } else {
                dialog.dismiss()
            }
        }
        dialog.setContentView(view)

        dialog.show()

    }
}