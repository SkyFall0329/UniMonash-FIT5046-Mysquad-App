package com.example.mysquad.data.entityForTesting.larry

data class Activity(
    val title: String,
    val time: String, // 例："2025-04-16 17:30"
    val isHost: Boolean // true = 我是主持人，false = 我是参与者
)