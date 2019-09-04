package org.mozilla.fenix.components

import mozilla.components.service.fxa.ServerConfig

object FxaServer {
    const val CLIENT_ID = "a2270f727f45f648"
    const val REDIRECT_URL = "https://accounts.firefox.com/oauth/success/$CLIENT_ID"
    val config = ServerConfig.release(CLIENT_ID, REDIRECT_URL)
}
