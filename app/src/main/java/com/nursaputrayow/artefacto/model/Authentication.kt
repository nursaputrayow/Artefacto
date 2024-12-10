package com.nursaputrayow.artefacto.model

data class RegisterRequest(
    val name: String,
    val phone: String,
    val email: String,
    val password: String,
    val password_confirmation: String
)

data class RegisterResponse(
    val status: String,
    val message: String,
    val data: UserData?
)

data class UserData(
    val user: User,
    val verification_code: String?
)

data class User(
    val id: Int,
    val name: String,
    val phone: String,
    val email: String,
    val created_at: String,
    val updated_at: String
)