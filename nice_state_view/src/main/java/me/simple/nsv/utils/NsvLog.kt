package me.simple.nsv.utils

import android.util.Log

internal class NsvLog {

    companion object {

        private const val TAG = "NiceStateView"
        private const val DEBUG = false

        fun d(msg: String) {
            Log.d(TAG, msg)
        }
    }
}