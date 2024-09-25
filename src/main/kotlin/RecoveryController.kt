package com.example.testIp


import com.example.testIp.com.example.testIp.User
import com.example.testIp.com.example.testIp.UserRepository
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/recovery")
class RecoveryController(
    private val userRepository: UserRepository
) {

    // Endpoint to register a user and store recovery data
    @PostMapping("/register")
    fun registerUser(@RequestBody request: RecoveryRequest): ResponseEntity<String> {
        if (request.email.isBlank() || request.recoveryData.isNullOrBlank()) {
            return ResponseEntity.badRequest().body("Email and recovery data are required.")
        }

        if (userRepository.existsByEmail(request.email)) {
            return ResponseEntity.badRequest().body("User with this email already exists.")
        }

        // Simulate a Stellar account address (for testing purposes)
        val stellarAccount = "G" + request.email.hashCode().toString().padStart(55, '0').take(55)

        val user = User(
            email = request.email,
            stellarAccount = stellarAccount,
            recoveryData = request.recoveryData
        )

        userRepository.save(user)
        return ResponseEntity.ok("User registered successfully.")
    }

    // Endpoint to initiate recovery
    @PostMapping("/initiate")
    fun initiateRecovery(@RequestBody request: RecoveryRequest): ResponseEntity<String> {
        val user = userRepository.findByEmail(request.email)
            ?: return ResponseEntity.status(404).body("User not found.")

        // Generate a verification code
        val verificationCode = (100000..999999).random().toString()
        user.verificationCode = verificationCode

        println("Recovery code for ${user.email}: $verificationCode")

        // Simulate sending the code via email
        println("Sending recovery code to ${user.email}")

        return ResponseEntity.ok("Recovery initiated. Please confirm with the verification code.")
    }


    // Endpoint to confirm recovery and return recovery data
    @PostMapping("/confirm")
    fun confirmRecovery(@RequestBody request: RecoveryRequest): ResponseEntity<Any> {
        val user = userRepository.findByEmail(request.email)
            ?: return ResponseEntity.status(404).body("User not found.")

        if (request.recoveryData != user.verificationCode) {
            return ResponseEntity.status(400).body("Invalid verification code.")
        }

        println("Recovery confirmed for ${user.email}")

        // Clear the verification code
        user.verificationCode = null

        // Return the recovery data
        val response = mapOf(
            "message" to "Recovery successful.",
            "stellarAccount" to user.stellarAccount,
            "recoveryData" to user.recoveryData
        )

        return ResponseEntity.ok(response)
    }

}

