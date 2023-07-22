package com.shashank.memebase.entry.fragments

import android.os.Bundle
import android.text.Editable
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnClickListener
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.viewModels
import androidx.transition.Slide
import com.shashank.memebase.*
import com.shashank.memebase.databinding.LayoutRegistrationBinding
import com.shashank.memebase.entry.MainActivity
import com.shashank.memebase.entry.viewModels.RegisterViewModel
import com.shashank.memebase.entry.models.RegisterRequest
import com.shashank.memebase.usecases.*
import com.shashank.memebase.usecases.domain.FragmentInflater
import com.shashank.memebase.usecases.domain.TextChanged
import com.shashank.memebase.views.TaskyAppCompatEditText
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RegistrationFragment : Fragment(), FragmentInflater by FragmentInflaterImpl(),
    TextChanged, OnClickListener{
    private lateinit var viewBinding: LayoutRegistrationBinding

    private val viewModel: RegisterViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewBinding = DataBindingUtil.inflate(inflater, R.layout.layout_registration, container, false)
        return viewBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setObservers()
        setToolBarAndFab()
        viewBinding.btnReg.setOnClickListener(this)
    }

    private fun setToolBarAndFab() {
        setFragmentManager(activity?.supportFragmentManager as FragmentManager)

        val parentActivity = requireActivity() as MainActivity
        parentActivity.setTitle((activity as MainActivity).getString(R.string.create_your_account))
        parentActivity.setFabLocation(false)
        parentActivity.showFAB(R.drawable.fab_back, "fab")
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

        viewModel.name.observe(viewLifecycleOwner) {
            val etName = viewBinding.etName
            it?.let { isValid -> etName.setValid(isValid)
                etName.setError(etName.subLayout.etInput.text?.isNotEmpty() == true && !isValid)}
        }

        viewModel.validateFields.observe(viewLifecycleOwner) {
            it.let {  viewBinding.btnReg.isEnabled = it == true }
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

        viewModel.registrationObserver.observe(viewLifecycleOwner){
            if(it){
                startLoginFragment()
            }
        }
    }

    private fun setFieldValidations(registrationFragment: RegistrationFragment?) {
        val etEmail = viewBinding.etEmail.subLayout.etInput
        val etPassword = viewBinding.etPassword.subLayout.etInput
        val etName = viewBinding.etName.subLayout.etInput

        etEmail.addTextChangedListener(registrationFragment?.let {
            TaskyWatcherImpl(viewBinding.etEmail,
                it
            )
        })
        etPassword.addTextChangedListener(registrationFragment?.let {
            TaskyWatcherImpl(viewBinding.etPassword,
                it
            )
        })
        etName.addTextChangedListener(registrationFragment?.let {
            TaskyWatcherImpl(viewBinding.etName,
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

        if(taskyAppcompatEditText.id == R.id.et_name) {
            viewModel.onNameChange(editable.toString())
        }
        viewModel.areFieldsValid()
    }

    private fun removeFieldValidations(registrationFragment: RegistrationFragment?) {
        val etEmail = viewBinding.etEmail.subLayout.etInput
        val etPassword = viewBinding.etPassword.subLayout.etInput
        val etName = viewBinding.etName.subLayout.etInput

        etEmail.removeTextChangedListener(registrationFragment?.let {
            TaskyWatcherImpl(viewBinding.etEmail, it)
        })
        etPassword.removeTextChangedListener(registrationFragment?.let {
            TaskyWatcherImpl(viewBinding.etPassword, it)
        })
        etName.removeTextChangedListener(registrationFragment?.let {
            TaskyWatcherImpl(viewBinding.etName, it)
        })
    }

    override fun onClick(view: View?) {
        if (view == viewBinding.btnReg){
            startRegistration()
        }
    }

    private fun startRegistration() {
        val name = viewBinding.etName.subLayout.etInput.text.toString()
        val email = viewBinding.etEmail.subLayout.etInput.text.toString()
        val password = viewBinding.etPassword.subLayout.etInput.text.toString()
        viewModel.register(RegisterRequest(name, email, password))
    }

    private fun startLoginFragment() {
        val parentActivity = requireActivity() as MainActivity
        parentActivity.startFragment(LoginFragment.getInstance())

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

    companion object {
        private lateinit var registrationFragment : RegistrationFragment
        @JvmStatic
        fun getInstance(): RegistrationFragment {
            registrationFragment = RegistrationFragment()
            registrationFragment.apply {
                enterTransition = Slide(Gravity.BOTTOM)
            }
            return registrationFragment
        }
    }

}