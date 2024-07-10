package com.notestasksapp.model

data class Note(
    var id: Int,
    var title: String,
    var description: String,
    var color: String,
    var date_added: String,
    var last_updated: String
)
