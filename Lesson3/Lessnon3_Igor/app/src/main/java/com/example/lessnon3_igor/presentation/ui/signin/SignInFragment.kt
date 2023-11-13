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
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.createViewModelLazy
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.lessnon3_igor.R
import com.example.lessnon3_igor.databinding.FragmentSignInBinding
import com.example.lessnon3_igor.presentation.data.repository.PreferenceStorage
import com.example.lessnon3_igor.presentation.data.responsemodel.ResponseStates
import com.google.android.material.snackbar.Snackbar
import dagger.android.support.AndroidSupportInjection
import javax.inject.Inject

class SignInFragment:Fragment() {
    private var _binding: FragmentSignInBinding? = null
    private val binding get() = _binding!!

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val viewModel by createViewModelLazy(
        ExampleViewModel::class,
        { this.viewModelStore },
        factoryProducer = { viewModelFactory })

    override fun onAttach(context: Context) {
        super.onAttach(context)
        AndroidSupportInjection.inject(this)
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSignInBinding.inflate(inflater, container, false)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.buttonSignIn.setOnClickListener {
            hideKeyboard()
            navigateToCatalog()
        }
        binding.textPassword.setOnEditorActionListener { _, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_DONE ||
                event.action == KeyEvent.ACTION_DOWN &&
                event.keyCode == KeyEvent.KEYCODE_ENTER
            ) {
                hideKeyboard()
                navigateToCatalog()
                true
            } else {
                false
            }
        }
    }
    private fun isEmailValid(email: String): Boolean {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches() && email.isNotEmpty()
    }

    private fun isPasswordValid(password: String): Boolean {
        return password.isNotEmpty()
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
            }
        } else {
            binding.layoutPassword.error = null
        }

        if (!(isEmailValid && isPasswordValid))
        {
            return
        }

        viewModel.login(email, password)
        viewModel.exampleLiveData.observe(viewLifecycleOwner) { result ->
            when (result) {
                is ResponseStates.Success -> {
                    val accessToken = result.data.accessToken
                    // Сохраните токен в SharedPreferences
                    val preferenceStorage = PreferenceStorage(requireContext())
                    preferenceStorage.userToken = accessToken

                    val action = SignInFragmentDirections.actionFragmentSignInToFragmentCatalog()
                    findNavController().navigate(action)
                }
                is ResponseStates.Failure -> {
                    Snackbar.make(
                        binding.root,
                        getString(R.string.sign_in_unexpected_error),
                        Snackbar.LENGTH_SHORT
                    ).show()
                }
                is ResponseStates.Loading -> {
                    loading()
                }
            }
        }
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
        },4000)
    }
    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}