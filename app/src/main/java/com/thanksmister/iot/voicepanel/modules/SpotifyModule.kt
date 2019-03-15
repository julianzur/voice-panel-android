package com.thanksmister.iot.voicepanel.modules

import android.arch.lifecycle.Lifecycle
import android.arch.lifecycle.LifecycleObserver
import android.arch.lifecycle.OnLifecycleEvent
import android.content.Context
import android.content.ContextWrapper
import com.spotify.android.appremote.api.Connector
import timber.log.Timber
import com.spotify.android.appremote.api.SpotifyAppRemote
import com.spotify.android.appremote.api.ConnectionParams


class SpotifyModule (base: Context?) : ContextWrapper(base), LifecycleObserver {

    private val CLIENT_ID = ""
    private val REDIRECT_URI = "a"
    private lateinit var mSpotifyAppRemote: SpotifyAppRemote

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    private fun initClient() {
        Timber.d("init Spotify")
        val connectionParams = ConnectionParams.Builder(CLIENT_ID)
                .setRedirectUri(REDIRECT_URI)
                .showAuthView(true)
                .build()

        val connectionListener = object : Connector.ConnectionListener {
            override fun onConnected(spotifyAppRemote: SpotifyAppRemote) {
                mSpotifyAppRemote = spotifyAppRemote
                Timber.d("MainActivity", "Connected! Yay!")

            }

            override fun onFailure(throwable: Throwable) {
                Timber.d("MainActivity", throwable.message, throwable)
            }
        }

        SpotifyAppRemote.connect(this, connectionParams, connectionListener)
    }
}
