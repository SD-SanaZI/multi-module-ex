package com.sanazi.menu.presentation

import android.view.View
import androidx.fragment.app.FragmentContainerView
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MenuViewModel : ViewModel() {
    val buttonList = listOf(
        BarButton(R.id.menu1,
            R.id.mainFragment,
            R.drawable.main_icon, R.string.main),
        BarButton(R.id.menu2,
            R.id.favoriteFragment,
            com.sanazi.common_ui.R.drawable.favorite_icon, R.string.favorite),
        BarButton(R.id.menu3,
            R.id.accFragment,
            R.drawable.acc_icon, R.string.acc),
    )
    private val current = MutableLiveData(buttonList.first())

    fun update(view: View){
        current.value?.buttonId?.let { current ->
            view.findViewById<FragmentContainerView>(current).getFragment<MenuButtonFragment>()
                .change(true)
            buttonList.filter { it.buttonId != current }.forEach {
                view.findViewById<FragmentContainerView>(it.buttonId).getFragment<MenuButtonFragment>()
                    .change(false)
            }
        }
    }

    fun change(buttonId: Int){
        val newCurrent = buttonList.find { it.buttonId == buttonId }
        newCurrent?.let { current.value = it }
    }
}