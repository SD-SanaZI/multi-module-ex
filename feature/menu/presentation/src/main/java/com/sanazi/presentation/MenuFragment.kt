package com.sanazi.menu.presentation

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentContainerView
import androidx.fragment.app.add
import androidx.fragment.app.commitNow
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController

class MenuFragment : Fragment(R.layout.menu){
    private val viewModel: MenuViewModel by viewModels()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        childFragmentManager.commitNow {
            setReorderingAllowed(true)
            viewModel.buttonList.forEach {
                add<MenuButtonFragment>(
                    it.buttonId, args = bundleOf(
                        "ImgId" to it.imageId,
                        "TextId" to it.textId
                    )
                )
            }
        }
        val listener =  NavController.OnDestinationChangedListener{ _, destination, _ ->
            viewModel.buttonList.firstOrNull{ it.navId ==  destination.id}?.let {
                viewModel.change(it.buttonId)
                viewModel.update(view)
            }
        }
        viewModel.buttonList.forEach { pair ->
            view.findViewById<FragmentContainerView>(pair.buttonId).setOnClickListener {
                viewModel.change(pair.buttonId)
                viewModel.update(view)
                findNavController().removeOnDestinationChangedListener(listener)
                findNavController().navigate(pair.navId)
                findNavController().addOnDestinationChangedListener(listener)
            }
        }
    }
}


