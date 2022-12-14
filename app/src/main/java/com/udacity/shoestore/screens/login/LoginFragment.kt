package com.udacity.shoestore.screens.login

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.udacity.shoestore.R
import com.udacity.shoestore.databinding.FragmentLoginBinding
import com.udacity.shoestore.models.LoginState
import com.udacity.shoestore.models.LoginViewModel

/**
 * A simple [Fragment] subclass.
 * Use the [LoginFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class LoginFragment : Fragment() {

    private lateinit var viewModel: LoginViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Log.i("LoginFragment", "in onCreateView")
        // Inflate the layout for this fragment
        val binding : FragmentLoginBinding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_login, container, false)

        viewModel = ViewModelProvider(this).get(LoginViewModel::class.java)

        binding.btnLogin.setOnClickListener {
            viewModel.onLogin(binding.etUsername.text.toString(),
                    binding.etPassword.text.toString())
        }

        binding.registerButtonFragmentLogin.setOnClickListener {
            viewModel.onRegister(binding.etUsername.text.toString(),
                binding.etPassword.text.toString())
        }

        viewModel.loginState.observe(this as LifecycleOwner, Observer { ls ->
            when (ls) {
                LoginState.REGISTER -> {
                    navigateToWelcome()
                    viewModel.onEventLoginComplete()
                }
                LoginState.LOGIN -> {
                    navigateToShoeList()
                    viewModel.onEventLoginComplete()
                }
            }
        })
        return binding.root
    }

    private fun navigateToWelcome() {
        val action = LoginFragmentDirections.actionLoginFragmentToWelcomeFragment(viewModel.emailText.value ?: "")
        findNavController().navigate(action)
    }

    private fun navigateToShoeList() {
        val action = LoginFragmentDirections.actionLoginFragmentToShoeListFragment()
        findNavController().navigate(action)
    }


}