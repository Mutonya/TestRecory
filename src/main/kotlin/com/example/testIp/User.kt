package com.example.testIp.com.example.testIp


data class User(
    val email: String,
    val stellarAccount: String,
    val recoveryData: String, // Encrypted mnemonic or recovery key
    var verificationCode: String? = null

)
