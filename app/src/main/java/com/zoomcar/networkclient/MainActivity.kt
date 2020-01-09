package com.zoomcar.networkclient

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.bluelinelabs.logansquare.LoganSquare
import com.google.gson.JsonElement
import com.zoomcar.zcnetwork.core.ZcNetworkBuilder
import com.zoomcar.zcnetwork.core.ZcNetworkListener
import com.zoomcar.zcnetwork.error.NetworkError
import com.zoomcar.zcnetwork.utils.LibTag
import com.zoomcar.zcnetwork.utils.ZcRequestType
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        button.setOnClickListener {
            val zcNetworkBuilder: ZcNetworkBuilder = ZcNetworkBuilder()
                    .setActivity(this)
                    .setBodyParams(hashMapOf("title" to "foo", "body" to "bar", "userId" to 1))
                    .setRequestType(ZcRequestType.POST_WITH_BODY)
                    .setHeaderParams(hashMapOf("Accept" to "application/json", "key" to "value"))
                    .setUrl("https://jsonplaceholder.typicode.com/posts")
                    .setListener(object : ZcNetworkListener {
                        override fun onSuccess(
                                response: JsonElement?,
                                responseCode: Int
                        ) {
                            val user = LoganSquare.parse(response.toString(), User::class.java)
                            Log.d(LibTag.TAG, "onSuccess: ${user.name}")
                        }

                        override fun onError(error: NetworkError) {
                            Log.d(LibTag.TAG, "onError: ${error.httpCode}")
                        }
                    })
            zcNetworkBuilder.request()
        }
    }
}