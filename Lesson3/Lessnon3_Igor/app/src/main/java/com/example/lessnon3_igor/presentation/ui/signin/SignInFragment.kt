package com.example.lessnon3_igor.presentation.ui.signin
import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Patterns
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.lessnon3_igor.R
import com.example.lessnon3_igor.databinding.FragmentSignInBinding
import com.google.android.material.snackbar.Snackbar


class SignInFragment:Fragment() {
    private lateinit var binding: FragmentSignInBinding
    private val MIN_PASSWORD_LENGTH = 6

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding =  FragmentSignInBinding.inflate(inflater, container, false)
        return  binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.buttonSignIn.setOnClickListener {
            hideKeyboard()
            loading()
        }
        binding.textPassword.setOnEditorActionListener { _, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_DONE ||
                event.action == KeyEvent.ACTION_DOWN &&
                event.keyCode == KeyEvent.KEYCODE_ENTER
            ) {
                hideKeyboard()
                loading()
                true
            } else {
                false
            }
        }
    }
    private fun isEmailValid(email: String): Boolean {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    private fun isPasswordValid(password: String): Boolean {
        return password.length >= MIN_PASSWORD_LENGTH && password.isNotEmpty()
    }
    private fun navigateToCatalog() {
        val email = binding.textLogin.text.toString()
        val password = binding.textPassword.text.toString()

        val isEmailValid = isEmailValid(email)
        val isPasswordValid = isPasswordValid(password)

        if (!isEmailValid) {
            binding.layoutLogin.error = getString(R.string.error_input)
        } else {
            binding.layoutLogin.error = null
        }

        if (!isPasswordValid) {
            if (password.isEmpty()) {
                binding.layoutPassword.error = getString(R.string.error_input)
            } else {
                binding.layoutPassword.error = "Пароль должен содержать минимум $MIN_PASSWORD_LENGTH символов"
            }
        } else {
            binding.layoutPassword.error = null
        }

        if (!(isEmailValid && isPasswordValid))
        {
            return
        }

        if(email != "xhhdjkd@gmail.com" || password !="ffgg2190")
        {
            Snackbar.make(
                binding.root,
                getString(R.string.sign_in_unexpected_error),
                Snackbar.LENGTH_SHORT
            ).show()
            return
        }

        val action = SignInFragmentDirections.actionFragmentSignInToFragmentCatalog()
        findNavController().navigate(action)
    }
    private fun hideKeyboard() {
        val imm = requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(binding.root.windowToken, 0)
    }

    private fun loading()
    {
        binding.buttonSignIn.text=""
        binding.loading.isVisible = true
        Handler(Looper.getMainLooper()).postDelayed({
            binding.buttonSignIn.text=getString(R.string.sign_in)
            binding.loading.isVisible = false
            navigateToCatalog()
        },4000)
    }
}