package com.programmer2704.movapp.tools

import android.content.Context
import android.net.ConnectivityManager
import androidx.fragment.app.FragmentActivity

object Usage {

    fun isOnline(activity: FragmentActivity?): Boolean {
        val connectivityManager = activity?.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo = connectivityManager.activeNetworkInfo
        return networkInfo != null && networkInfo.isConnected
    }
}