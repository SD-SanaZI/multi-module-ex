package com.sanazi.account

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.DrawableRes
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AccountAdapter(
    val manager: AccountAdapterManager
) :
    RecyclerView.Adapter<AccountAdapter.AccountAdapterViewHolder>() {

    sealed class AccountAdapterViewHolder(view: View) : RecyclerView.ViewHolder(view)

    inner class CourseViewHolder(view: View) : AccountAdapterViewHolder(view) {
        val favorite: ImageView = view.findViewById(R.id.favorite)
        val offerRate: TextView = view.findViewById(R.id.offerRate)
        val offerDate: TextView = view.findViewById(R.id.offerDate)
        val offerTitle: TextView = view.findViewById(R.id.offerTitle)
        val progressPercent: TextView = view.findViewById(R.id.progressPercent)
        val lessensDone: TextView = view.findViewById(R.id.lessensDone)
        val lessensTotal: TextView = view.findViewById(R.id.lessensTotal)
        val progressStart: View = view.findViewById(R.id.progressStart)
        val progressEnd: View = view.findViewById(R.id.progressEnd)

        fun bind(course: AccountCourse, position: Int) {
            offerRate.text = course.rate
            offerDate.text = course.startDate
            offerTitle.text = course.title

            val done = ((course.doneLessons.toFloat()/course.totalLessons) * 100).toInt()
            progressPercent.text =
                progressPercent.resources.getString(R.string.percent, done)
            lessensDone.text = course.doneLessons.toString()
            lessensTotal.text = progressPercent.resources.getString(R.string.total_lessons, course.totalLessons)

            progressStart.layoutParams = (progressStart.layoutParams as ConstraintLayout.LayoutParams).also {
                it.horizontalWeight = course.doneLessons.toFloat()
            }

            progressEnd.layoutParams = (progressEnd.layoutParams as ConstraintLayout.LayoutParams).also {
                it.horizontalWeight = (course.totalLessons - course.doneLessons).toFloat()
            }

            favorite.setImageResource(favoriteDrawable(course.hasLike))
            favorite.setOnClickListener {
                CoroutineScope(Dispatchers.Main).launch {
                    manager.onFavoriteClick(position)
                    notifyItemChanged(position)
                }
            }
        }
    }

    inner class CoursePlankViewHolder(view: View) : AccountAdapterViewHolder(view)

    inner class ProfileViewHolder(view: View) : AccountAdapterViewHolder(view) {
        val firstButtonContainer: View = view.findViewById(R.id.firstButtonContainer)
        val secondButtonContainer: View = view.findViewById(R.id.secondButtonContainer)
        val thirdButtonContainer: View = view.findViewById(R.id.thirdButtonContainer)

        fun bind() {
            firstButtonContainer.setOnClickListener {
                manager.onFirstButtonClick()
            }
            secondButtonContainer.setOnClickListener {
                manager.onSecondButtonClick()
            }
            thirdButtonContainer.setOnClickListener {
                manager.onThirdButtonClick()
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (position) {
            0 -> R.layout.profile
            1 -> R.layout.course_plank
            else -> R.layout.account_offer_element
        }
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): AccountAdapterViewHolder {
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(viewType, viewGroup, false)
        return when (viewType) {
            R.layout.account_offer_element -> {
                CourseViewHolder(view)
            }

            R.layout.course_plank -> {
                CoursePlankViewHolder(view)
            }

            R.layout.profile -> {
                ProfileViewHolder(view)
            }

            else -> throw Exception("Unexpected layout")
        }
    }

    override fun onBindViewHolder(viewHolder: AccountAdapterViewHolder, position: Int) {
        when (viewHolder) {
            is ProfileViewHolder -> {
                viewHolder.bind()
            }

            is CourseViewHolder -> {
                viewHolder.bind(manager.dataSet[position - 2], position - 2)
            }

            is CoursePlankViewHolder -> {}
        }
    }

    @DrawableRes
    private fun favoriteDrawable(hasLike: Boolean): Int {
        return if (hasLike) R.drawable.fill_favorite_icon else com.sanazi.common_ui.R.drawable.favorite_icon
    }

    override fun getItemCount() = manager.dataSet.size + 2
}