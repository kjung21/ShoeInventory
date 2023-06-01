package com.kryptopass.shoeinventory.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.kryptopass.shoeinventory.models.User
import timber.log.Timber

class LoginViewModel : ViewModel() {

    private val _isAuthorized = MutableLiveData<Boolean>()
    val isAuthorized: LiveData<Boolean>
        get() = _isAuthorized

    private val _emailValid = MutableLiveData<Boolean>()
    val emailValid: LiveData<Boolean>
        get() = _emailValid

    private val _passwordValid = MutableLiveData<Boolean>()
    val passwordValid: LiveData<Boolean>
        get() = _passwordValid

    private lateinit var users: MutableList<User>

    init {
        Timber.i("LoginViewModel created!")
        resetUsers()
    }

    fun authorize(email: String, password: String) {
        _isAuthorized.value = validateFields(email, password)
    }

    fun resetErrors() {
        _passwordValid.value = true
        _emailValid.value = true
    }

    fun authorizedStatusProcessed() {
        _isAuthorized.value = false
    }

    private fun validateFields(email: String, password: String): Boolean {

        _emailValid.value = validEmail(email)
        _passwordValid.value = validPassword(password)

        if (_emailValid.value!! && _passwordValid.value!!) {
            createAccount(email, password)
            return true
        }
        return false
    }

    private fun createAccount(email: String, password: String) {
        val user = users.find { user -> user.email == email && user.password == password }
        if (user == null) {
            users.add(User(email, password))
        }
    }

    private fun validEmail(email: String): Boolean {
        val emailTrimmed = email.trim()
        return emailTrimmed.length >= MIN_EMAIL_LENGTH &&
                emailTrimmed.endsWith(".com") && emailTrimmed.contains("@")
    }

    private fun validPassword(password: String): Boolean {
        return password.trim().length >= MIN_PASSWORD_LENGTH
    }

    private fun resetUsers() {
        users = mutableListOf(
            User("admin@gmail.com", "admin123")
        )
    }

    companion object {
        private const val MIN_EMAIL_LENGTH = 8
        private const val MIN_PASSWORD_LENGTH = 6
    }
}