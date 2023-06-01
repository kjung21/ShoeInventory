package com.kryptopass.shoeinventory.login

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.kryptopass.shoeinventory.R
import com.kryptopass.shoeinventory.databinding.FragmentLoginBinding
import timber.log.Timber

class LoginFragment : Fragment() {

    private lateinit var binding: FragmentLoginBinding
    private lateinit var loginViewModel: LoginViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_login, container, false)
        loginViewModel = ViewModelProvider(this)[LoginViewModel::class.java]

        Timber.i("LoginFragment: onCreateView called")

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        Timber.i("LoginFragment: onViewCreated called")

        loginViewModel.emailValid.observe(viewLifecycleOwner) { valid ->
            binding.editTextEmail.error = if (valid) {
                null
            } else {
                getString(R.string.error_email)
            }
        }
        loginViewModel.passwordValid.observe(viewLifecycleOwner) { valid ->
            binding.editTextPassword.error = if (valid) {
                null
            } else {
                getString(R.string.error_password)
            }
        }
        loginViewModel.isAuthorized.observe(viewLifecycleOwner) { authorized ->
            if (authorized) {
                findNavController().navigate(
                    LoginFragmentDirections.actionLoginFragmentToWelcomeFragment()
                )
                loginViewModel.authorizedStatusProcessed()
            }
        }
        with(binding) {
            editTextEmail.addTextChangedListener {
                loginViewModel.resetErrors()
            }
            editTextPassword.addTextChangedListener {
                loginViewModel.resetErrors()
            }
            buttonLogin.setOnClickListener {
                authorize()
            }
            buttonRegister.setOnClickListener {
                authorize()
            }
        }
    }

    private fun authorize() {
        val login = binding.editTextEmail.text?.toString() ?: ""
        val password = binding.editTextPassword.text?.toString() ?: ""
        loginViewModel.authorize(login, password)
    }
}