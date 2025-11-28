package com.sanazi.menu.presentation

import android.graphics.PorterDuff
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment

class MenuButtonFragment : Fragment(R.layout.menu_button){
    private var container: View? = null
    private var image: ImageView? = null
    private var text: TextView? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val imgId = requireArguments().getInt("ImgId")
        val textId = requireArguments().getInt("TextId")
        container = view.findViewById(R.id.container)
        image = view.findViewById(R.id.image)
        image?.setImageResource(imgId)
        text = view.findViewById(R.id.text)
        text?.setText(textId)
        if (imgId == R.drawable.main_icon) change(true)
    }

    fun change(isActive: Boolean){
        if (isActive){
            container?.visibility = View.VISIBLE
            val green = resources.getColor(R.color.green, null)
            image?.setColorFilter(green, PorterDuff.Mode.SRC_IN)
            text?.setTextColor(green)
        } else{
            container?.visibility = View.INVISIBLE
            val green = resources.getColor(R.color.white, null)
            image?.setColorFilter(green, PorterDuff.Mode.SRC_IN)
            text?.setTextColor(green)
        }
    }
}