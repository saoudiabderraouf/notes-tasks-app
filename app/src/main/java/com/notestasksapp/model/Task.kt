package com.notestasksapp.model

data class Task(
    var id: Long,
    var title: String,
    var description: String,
    var category: String,
    var start_date: Long,
    var end_date: Long
)
