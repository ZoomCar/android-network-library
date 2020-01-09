package com.zoomcar.networkclient

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.google.gson.JsonElement
import com.zoomcar.zcnetwork.core.ZcNetworkBuilder
import com.zoomcar.zcnetwork.core.ZcNetworkListener
import com.zoomcar.zcnetwork.error.NetworkError
import com.zoomcar.zcnetwork.utils.ZcRequestType
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private val TAG = "MainActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        button.setOnClickListener {
            val zcNetworkBuilder: ZcNetworkBuilder = ZcNetworkBuilder()
                .setActivity(this)
                .setRequestType(ZcRequestType.GET)
                .setHeaderParams(hashMapOf("Accept" to "application/json"))
                .setUrl("/users/1")
                .setListener(object : ZcNetworkListener {
                    override fun onSuccess(
                        response: JsonElement?,
                        responseCode: Int
                    ) {
                        Log.d(TAG, "onSuccess: ")
                    }

                    override fun onError(error: NetworkError) {
                        Log.d(TAG, "onError: ${error.httpCode}")
                    }
                })
            zcNetworkBuilder.request()
        }
    }
}