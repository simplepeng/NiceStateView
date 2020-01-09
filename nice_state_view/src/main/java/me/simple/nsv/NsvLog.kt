package me.simple.nsv

import android.util.Log

internal class NsvLog {

    companion object {

        private const val TAG = "NiceStateView"
        private const val DEBUG = false

        fun d(msg: String) {
            if (!DEBUG) return
            Log.d(TAG, msg)
        }
    }
}