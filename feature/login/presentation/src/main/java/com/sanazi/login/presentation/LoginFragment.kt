package com.sanazi.login.presentation

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.sanazi.common_ui.getAppDependenciesProvider
import com.sanazi.login.domain.InputChecker
import javax.inject.Inject

class LoginFragment : Fragment(R.layout.input) {
    private val viewModel: LoginViewModel by viewModels()
    @Inject lateinit var inputChecker: InputChecker

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        LoginComponent.create(getAppDependenciesProvider()).inject(this)
        view.findViewById<EditText>(R.id.emailInput).inputType
        view.findViewById<Button>(R.id.button).setOnClickListener {
            viewModel.login(view, findNavController(), inputChecker)
        }
        view.findViewById<ImageButton>(R.id.vkButton).setOnClickListener {
            BrowserIntent(view.context).openUrlInBrowser("https://vk.com/")
        }
        view.findViewById<ImageButton>(R.id.okButton).setOnClickListener {
            BrowserIntent(view.context).openUrlInBrowser("https://ok.ru/")
        }
    }
}

