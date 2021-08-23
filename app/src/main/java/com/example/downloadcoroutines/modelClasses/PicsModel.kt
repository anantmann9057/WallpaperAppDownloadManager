package com.example.downloadcoroutines.modelClasses

data class PicsModel(
    val author: String? = null,

    val download_url: String? = null,

    val id: String? = null,

    val url: String? = null,

    var placeHolderUrl: String? = null,

    var isVisible: Boolean? = false
) {
}