package com.nasa.apollo11.data.remote

import android.content.Context
import android.net.ConnectivityManager
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class CheckInternetConnection @Inject constructor(@ApplicationContext private val _context: Context) {

    fun isNetworkAvailable(): Boolean { /*ConnectivityManager connectivityManager
                = (ConnectivityManager) _context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();*/
        return isOnline()
    }

    companion object {
        fun isInternetConnected(context: Context): Boolean {
            val connectivityManager =
                context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val activeNetworkInfo = connectivityManager.activeNetworkInfo
            return activeNetworkInfo != null && activeNetworkInfo.isConnected
        }
    }

    //  inner  class WiFiConnectUseCase(context: Context) {
//
//        private var wifiManager =
//            context.applicationContext.getSystemService(Context.WIFI_SERVICE) as WifiManager
//
//        private val connectivityManager =
//            context.applicationContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
//
//        private val wifiSSID = "My Network"
//        private val wifiPassword = "password1234"
//
//        @RequiresApi(api = Build.VERSION_CODES.Q)
//        operator fun invoke(): Single<Pair<Network, ConnectivityManager.NetworkCallback>> { //return the NetworkCallback in order to disconnect properly from the device
//            return connect()
//                .delay(3, TimeUnit.SECONDS) // wait for 3 sec, just to make sure everything is configured on the device
//        }
//
//        @RequiresApi(Build.VERSION_CODES.Q)
//        private fun connect(): Single<Pair<Network, ConnectivityManager.NetworkCallback>> {
//            return Single.create { emitter ->
//
//                val specifier = WifiNetworkSpecifier.Builder()
//                    .setSsid(wifiSSID)
//                    .setWpa2Passphrase(wifiPassword)
//                    .build()
//
//                val networkRequest = NetworkRequest.Builder()
//                    .addTransportType(NetworkCapabilities.TRANSPORT_WIFI)
//                    .removeCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET) // as not internet connection is required for this device
//                    //.setNetworkSpecifier(specifier)
//                    .build()
//
//                val networkCallback = object : ConnectivityManager.NetworkCallback() {
//                    override fun onAvailable(network: Network) {
//                        super.onAvailable(network)
//                        Timber.v("connect to WiFi success. Network is available.")
//                        emitter.onSuccess(Pair(network, this))
//                    }
//
//                    override fun onUnavailable() {
//                        super.onUnavailable()
//                        Timber.w("connect to WiFi failed. Network is unavailable")
//                        emitter.tryOnError(IllegalArgumentException("connect to WiFi failed. Network is unavailable"))
//                    }
//                }
//
//                connectivityManager.requestNetwork(networkRequest, networkCallback)
//            }
//        }
//    }
    fun isOnline(): Boolean {
        try {
            val p1 = Runtime.getRuntime().exec("ping -c 1 www.google.com")
            val returnVal = p1.waitFor()
            return returnVal == 0
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return false
    }

}
