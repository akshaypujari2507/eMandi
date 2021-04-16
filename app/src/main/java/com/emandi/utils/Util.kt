package com.emandi.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build

class Util {
    companion object {

        val SCREEN_KEY = "screen"
        val ORDERID_KEY = "order_id"
        val TITLE_KEY = "title"

        val BASE_URL = "https://staging.emandi.store/dummy-api/"

        fun checksdkforbackground(): Boolean {
            val sdk = android.os.Build.VERSION.SDK_INT
            if (sdk < android.os.Build.VERSION_CODES.JELLY_BEAN) {
                return true
            } else {
                return false
            }
        }

        fun isNetworkAvailable(context: Context?): Boolean {
            if (context == null) return false
            val connectivityManager =
                context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                val capabilities =
                    connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
                if (capabilities != null) {
                    when {
                        capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> {
                            return true
                        }
                        capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> {
                            return true
                        }
                        capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> {
                            return true
                        }
                    }
                }
            } else {
                val activeNetworkInfo = connectivityManager.activeNetworkInfo
                if (activeNetworkInfo != null && activeNetworkInfo.isConnected) {
                    return true
                }
            }
            return false
        }

        fun getAmountWithCommaWithDecimal(amount: String?): String {
            if (amount != null && amount.length > 0) {
                val damt = amount.toDouble()
                if (damt > 0) {
                    val strprice = String.format("%.2f", damt)
                    val strcoamt: String = getAmountWithComma(strprice)!!
                    if (strcoamt != null) return strcoamt
                }
            }
            return "0.00"
        }

        fun removeChar(s: String, c: Char): String? {
            var r = ""
            for (i in 0 until s.length) {
                if (s[i] != c) r += s[i]
            }
            return r
        }

        fun getAmountWithComma(amount: String): String? {
            var stOrg: String = removeChar(amount, '.')!!
            stOrg = removeChar(stOrg, ',')!!
            stOrg = removeChar(stOrg, '-')!!
            if (stOrg.indexOf("0") == 0) {
                stOrg =
                    if (stOrg == "0") stOrg.substring(0) else if (stOrg == "00000") stOrg.substring(
                        2
                    ) else stOrg.substring(1)
            }
            var ilen = stOrg.length
            if (ilen == 2 || ilen == 1) {
                stOrg = "0.$stOrg"
            } else if (ilen > 2) {
                stOrg = stOrg.substring(0, ilen - 2) + "." + stOrg.substring(ilen - 2, ilen)
            }
            ilen = stOrg.length
            if (ilen > 6) {
                stOrg = stOrg.substring(0, ilen - 6) + "," + stOrg.substring(ilen - 6, ilen)
            }
            ilen = stOrg.length
            if (ilen > 9) {
                stOrg = stOrg.substring(0, ilen - 9) + "," + stOrg.substring(ilen - 9, ilen)
            }
            return if (stOrg != amount) {
                stOrg
            } else stOrg
        }


    }

}