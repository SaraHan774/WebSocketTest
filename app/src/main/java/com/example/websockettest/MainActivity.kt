package com.example.websockettest

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import okhttp3.*
import okhttp3.logging.HttpLoggingInterceptor
import okio.ByteString

class MainActivity : AppCompatActivity() {
    private var client: OkHttpClient? = null
    private var ws: WebSocket? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Create an OkHttpClient instance
        client = OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            }).build()

        // Create a WebSocket request
        val request: Request = Request.Builder()
            .url("ws://192.168.45.241:8080")
            .build()

        // Create a WebSocket listener
        val listener: WebSocketListener = object : WebSocketListener() {
            override fun onOpen(webSocket: WebSocket, response: Response) {
                // WebSocket connection established
                Log.d("WebSocket", "onOpen")
            }

            override fun onMessage(webSocket: WebSocket, text: String) {
                // WebSocket message received
                Log.d("WebSocket", "onMessage: $text")
            }

            override fun onMessage(webSocket: WebSocket, bytes: ByteString) {
                // WebSocket message received as bytes
                Log.d("WebSocket", "onMessage: " + bytes.utf8())
            }

            override fun onClosing(webSocket: WebSocket, code: Int, reason: String) {
                // WebSocket connection is closing
                Log.d("WebSocket", "onClosing: $code, $reason")
            }

            override fun onClosed(webSocket: WebSocket, code: Int, reason: String) {
                // WebSocket connection is closed
                Log.d("WebSocket", "onClosed: $code, $reason")
            }

            override fun onFailure(webSocket: WebSocket, t: Throwable, response: Response?) {
                // WebSocket connection failure
                Log.d("WebSocket", "onFailure: " + t.message)
            }
        }

        // Connect to the WebSocket server
        ws = client!!.newWebSocket(request, listener)
    }

    override fun onDestroy() {
        super.onDestroy()

        // Close the WebSocket connection
        if (ws != null) {
            ws!!.cancel()
        }
    }
}