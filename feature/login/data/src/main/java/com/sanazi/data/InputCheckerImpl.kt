package com.sanazi.list.data

import com.sanazi.common.AccountInfo
import com.sanazi.login.domain.InputChecker
import javax.inject.Inject

class InputCheckerImpl @Inject constructor(): InputChecker {
    val emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\$"
    override fun check(email: String, password: String): Result<AccountInfo> {
        return if(email.matches(emailRegex.toRegex())){
            if (password.isNotEmpty()) Result.success(AccountInfo())
            else Result.failure(Throwable("Wrong password"))
        } else Result.failure(Throwable("Wrong email"))
    }
}