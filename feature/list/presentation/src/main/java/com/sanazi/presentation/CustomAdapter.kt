package com.sanazi.list.presentation

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.DrawableRes
import androidx.recyclerview.widget.RecyclerView
import com.sanazi.list.domain.Course
import com.sanazi.list.domain.UserRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class CustomAdapterHelper(private val repo: UserRepository){
    val dataSet = mutableListOf<Course>()

    suspend fun onFavoriteClick(position: Int){
        withContext(Dispatchers.IO) {
            repo.setFavorite(dataSet[position].id, !dataSet[position].hasLike)
        }
        val index = dataSet.indexOfFirst { it.id == dataSet[position].id }
        if (index != -1){
            val course = dataSet[index]
            dataSet.removeAt(index)
            dataSet.add(index, course.copy(hasLike = !course.hasLike))
        }
    }
}

class CustomAdapter(
    val helper: CustomAdapterHelper
    ) :
    RecyclerView.Adapter<CustomAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val favorite: ImageView = view.findViewById(R.id.favorite)
        val offerRate: TextView = view.findViewById(R.id.offerRate)
        val offerDate: TextView = view.findViewById(R.id.offerDate)
        val offerTitle: TextView = view.findViewById(R.id.offerTitle)
        val offerText: TextView = view.findViewById(R.id.offerText)
        val offerPrise: TextView = view.findViewById(R.id.offerPrise)
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.offer_element, viewGroup, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        viewHolder.offerRate.text = helper.dataSet[position].rate
        viewHolder.offerDate.text = helper.dataSet[position].startDate
        viewHolder.offerTitle.text = helper.dataSet[position].title
        viewHolder.offerText.text = helper.dataSet[position].text
        viewHolder.offerPrise.text = viewHolder.itemView.resources.getString(R.string.price, helper.dataSet[position].price)
        viewHolder.favorite.setImageResource(favoriteDrawable(helper.dataSet[position].hasLike))
        viewHolder.favorite.setOnClickListener {
            CoroutineScope(Dispatchers.Main).launch {
                helper.onFavoriteClick(position)
                notifyItemChanged(position)
            }
        }
    }

    @DrawableRes
    private fun favoriteDrawable(hasLike: Boolean): Int{
        return if (hasLike) R.drawable.fill_favorite_icon else com.sanazi.common_ui.R.drawable.favorite_icon
    }

    override fun getItemCount() = helper.dataSet.size
}