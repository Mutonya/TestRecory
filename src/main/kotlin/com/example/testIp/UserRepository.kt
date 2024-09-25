package com.example.testIp.com.example.testIp


import org.springframework.stereotype.Repository

@Repository
class UserRepository {
    private val users = mutableMapOf<String, User>()

    fun save(user: User) {
        users[user.email] = user
    }

    fun findByEmail(email: String): User? = users[email]

    fun existsByEmail(email: String): Boolean = users.containsKey(email)
}
