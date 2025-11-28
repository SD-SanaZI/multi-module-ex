package com.sanazi.login.domain

import com.sanazi.common.AccountInfo

interface InputChecker{
    fun check(email: String, password: String): Result<AccountInfo>
}