package com.sanazi.common_ui

import androidx.fragment.app.Fragment
import com.sanazi.dependencies.AppDependenciesProvider
import com.sanazi.dependencies.DependenciesCarrier

fun Fragment.getAppDependenciesProvider(): AppDependenciesProvider =
    (requireActivity().application as DependenciesCarrier)
        .getAppDependenciesProvider()
