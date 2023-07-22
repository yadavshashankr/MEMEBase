package com.shashank.memebase.entry.fragments


import android.os.Bundle
import android.text.Editable
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnClickListener
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.viewModels
import androidx.transition.Slide
import com.shashank.memebase.*
import com.shashank.memebase.databinding.LayoutLoginBinding
import com.shashank.memebase.entry.MainActivity
import com.shashank.memebase.entry.viewModels.LoginViewModel
import com.shashank.memebase.entry.models.AuthenticationRequest
import com.shashank.memebase.meme.fragments.AgendaFragment
import com.shashank.memebase.usecases.domain.FragmentInflater
import com.shashank.memebase.usecases.FragmentInflaterImpl
import com.shashank.memebase.usecases.NetworkStatus
import com.shashank.memebase.usecases.TaskyWatcherImpl
import com.shashank.memebase.usecases.domain.TextChanged
import com.shashank.memebase.views.TaskyAppCompatEditText
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginFragment : Fragment(), FragmentInflater by FragmentInflaterImpl(), TextChanged, OnClickListener {
    private lateinit var viewBinding: LayoutLoginBinding

    private val viewModel: LoginViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewBinding = DataBindingUtil.inflate(inflater, R.layout.layout_login, container, false)
        viewBinding.lifecycleOwner = this
        return viewBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setToolbarAndFab()
        setObservers()

        viewBinding.btnLogin.setOnClickListener(this)
        viewBinding.tvRegister.setOnClickListener(this)
    }

    private fun setToolbarAndFab() {
        setFragmentManager(activity?.supportFragmentManager as FragmentManager)
        val parentActivity = requireActivity() as MainActivity
        parentActivity.setTitle(requireContext().getString(R.string.welcome_back))
        parentActivity.hideFAB()
    }

    private fun setObservers() {
        viewModel.email.observe(viewLifecycleOwner) {
            val emailField = viewBinding.etEmail
            it?.let { isValid -> emailField.setValid(isValid)
                emailField.setError(emailField.subLayout.etInput.text?.isNotEmpty() == true && !isValid)}
        }

        viewModel.password.observe(viewLifecycleOwner) {
            val passwordField = viewBinding.etPassword
            it?.let { isValid -> passwordField.setValid(isValid)
                passwordField.setError(passwordField.subLayout.etInput.text?.isNotEmpty() == true && !isValid)}
        }

        viewModel.validateFields.observe(viewLifecycleOwner) {
            it.let {  viewBinding.btnLogin.isEnabled = it == true }
        }

        viewModel.networkObserver.observe(viewLifecycleOwner){
            when(it){
                NetworkStatus.Available -> {
                    viewModel.areFieldsValid()
                }
                NetworkStatus.Unavailable -> {
                    viewModel.areFieldsValid()
                }
            }
        }
    }

    private fun setFieldValidations(loginFragment: LoginFragment?) {
        val etEmail = viewBinding.etEmail.subLayout.etInput
        val etPassword = viewBinding.etPassword.subLayout.etInput

        etEmail.addTextChangedListener(loginFragment?.let {
            TaskyWatcherImpl(viewBinding.etEmail,
                it
            )
        })
        etPassword.addTextChangedListener(loginFragment?.let {
            TaskyWatcherImpl(viewBinding.etPassword,
                it
            )
        })
    }

    override fun onTextChanged(editable: Editable, taskyAppcompatEditText: TaskyAppCompatEditText) {
        if (taskyAppcompatEditText.id == R.id.et_email) {
            viewModel.onEmailChange(editable.toString())
        }

        if(taskyAppcompatEditText.id == R.id.et_password) {
            viewModel.onPasswordChange(editable.toString())
        }
            viewModel.areFieldsValid()
    }

    private fun removeFieldValidations(loginFragment: LoginFragment) {

        val etEmail = viewBinding.etEmail.subLayout.etInput
        val etPassword = viewBinding.etPassword.subLayout.etInput

        etEmail.removeTextChangedListener(
            TaskyWatcherImpl(viewBinding.etEmail,
                loginFragment
            )
        )

        etPassword.removeTextChangedListener(
            TaskyWatcherImpl(viewBinding.etPassword,
                loginFragment
            )
        )
    }

    private fun startLogin() {
        val email = viewBinding.etEmail.subLayout.etInput.text.toString()
        val password = viewBinding.etPassword.subLayout.etInput.text.toString()

        viewModel.login(AuthenticationRequest(email, password))

        viewModel.loginObserver.observe(viewLifecycleOwner){
            if(it){
                val parentActivity = (activity as MainActivity)
                parentActivity.startFragment(AgendaFragment.getInstance())
            }else{
                Toast.makeText(activity, "Invalid credentials", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun openRegistrationFragment() {
        setFragmentManager(activity?.supportFragmentManager as FragmentManager)
        val parentActivity = requireActivity() as MainActivity
        parentActivity.startFragment(RegistrationFragment.getInstance())
        removeFragment(this)
    }

    override fun onResume() {
        super.onResume()
        setFieldValidations(this)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        removeFieldValidations(this)
    }

    override fun onClick(view: View?) {
        when(view){
            viewBinding.tvRegister -> openRegistrationFragment()

            viewBinding.btnLogin ->  startLogin()
        }
    }

    companion object {
        private lateinit var loginFragment : LoginFragment
        @JvmStatic
        fun getInstance(): LoginFragment {
            loginFragment = LoginFragment()
            loginFragment.apply {
                enterTransition = Slide(Gravity.BOTTOM)
            }
            return loginFragment
        }

    }
}