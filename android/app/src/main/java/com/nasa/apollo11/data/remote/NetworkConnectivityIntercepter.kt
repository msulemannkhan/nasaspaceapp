package com.nasa.apollo11.data.remote

import java.io.IOException

class NetworkConnectivityError : IOException() {
     override val message: String
        get() = "No Internet Connection"
}