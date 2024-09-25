package com.example.testIp


data class RecoveryRequest(
    val email: String,
    val recoveryData: String? = null, // Used as verification code during confirmation
    val recoveryData2: String? = null // Optional, for other purposes
)

