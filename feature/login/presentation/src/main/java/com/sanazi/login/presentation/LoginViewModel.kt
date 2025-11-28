package com.sanazi.login.presentation

import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.core.net.toUri
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import androidx.navigation.NavDeepLinkRequest
import com.sanazi.login.domain.InputChecker

class LoginViewModel : ViewModel() {
    fun login(view: View, navController: NavController, inputChecker: InputChecker) {
        val result = inputChecker.check(
            view.findViewById<EditText>(R.id.emailInput).text.toString(),
            view.findViewById<EditText>(R.id.passInput).text.toString(),
        )
        result
            .onSuccess {
                navController.popBackStack()
                val request = NavDeepLinkRequest.Builder
                    .fromUri("android-app://com.sanazi/barFragment".toUri())
                    .build()
                navController.navigate(request)
            }
            .onFailure {
                Toast.makeText(
                    view.context,
                    it.message,
                    Toast.LENGTH_SHORT
                ).show()
            }
    }
}