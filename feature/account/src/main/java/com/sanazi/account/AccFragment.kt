package com.sanazi.account

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.DrawableRes
import androidx.core.net.toUri
import androidx.fragment.app.Fragment
import androidx.lifecycle.AbstractSavedStateViewModelFactory
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavDeepLinkRequest
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.savedstate.SavedStateRegistryOwner
import com.sanazi.common_ui.getAppDependenciesProvider
import com.sanazi.dependencies.CoursesManagerProvider
import com.sanazi.list.domain.Course
import com.sanazi.list.domain.CoursesManager
import dagger.Component
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class AccountFragment : Fragment(R.layout.account) {
    private lateinit var viewModel: AccountViewModel

    @Inject
    lateinit var repository: CoursesManager

    @SuppressLint("NotifyDataSetChanged")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        AccountComponent.create(getAppDependenciesProvider()).inject(this)
        viewModel = ViewModelProvider(
            this,
            AccountViewModel.provideFactory(repository, this)
        )[AccountViewModel::class.java]
        val customAdapter = AccountAdapter(
            object : AccountAdapterManager{
                override val dataSet: List<Course>
                    get() = viewModel.dataSet

                override suspend fun onFavoriteClick(position: Int) {
                    viewModel.onFavoriteClick(position)
                }

                override fun onFirstButtonClick() {
                    val request = NavDeepLinkRequest.Builder
                        .fromUri("android-app://com.sanazi/supportFragment".toUri())
                        .build()
                    findNavController().navigate(request)
                }

                override fun onSecondButtonClick() {
                    val request = NavDeepLinkRequest.Builder
                        .fromUri("android-app://com.sanazi/settingsFragment".toUri())
                        .build()
                    findNavController().navigate(request)
                }

                override fun onThirdButtonClick() {
                    parentFragment?.parentFragment?.findNavController()?.let { navController ->
                        navController.popBackStack()
                        val request = NavDeepLinkRequest.Builder
                            .fromUri("android-app://com.sanazi/inputFragment".toUri())
                            .build()
                        navController.navigate(request)
                    }
                }
            }
        )
        val recyclerView: RecyclerView = view.findViewById(R.id.offerList)
        recyclerView.layoutManager = LinearLayoutManager(view.context)
        recyclerView.adapter = customAdapter
        lifecycleScope.launch {
            viewModel.update { listFilter(it) }
            customAdapter.notifyDataSetChanged()
        }
    }

    fun listFilter(courses: List<Course>): List<Course> {
        return courses
    }
}

class AccountViewModel @Inject constructor(
    private val manager: CoursesManager
) : ViewModel(), CoursesAdapterManager {
    override val dataSet: MutableList<Course> = mutableListOf()

    override suspend fun onFavoriteClick(position: Int) {
        withContext(Dispatchers.IO) {
            manager.setFavorite(dataSet[position].id, !dataSet[position].hasLike)
        }

        val index = dataSet.indexOfFirst { it.id == dataSet[position].id }
        if (index != -1) {
            val course = dataSet[index]
            dataSet.removeAt(index)
            dataSet.add(index, course.copy(hasLike = !course.hasLike))
        }
    }

    suspend fun update(listFilter: (List<Course>) -> List<Course>) {
        withContext(Dispatchers.IO) {
            dataSet.let {
                it.clear()
                it.addAll(listFilter(manager.getAllCourses()))
            }
        }
    }

    companion object {
        fun provideFactory(
            repo: CoursesManager,
            owner: SavedStateRegistryOwner,
            defaultArgs: Bundle? = null,
        ): AbstractSavedStateViewModelFactory =
            object : AbstractSavedStateViewModelFactory(owner, defaultArgs) {
                @Suppress("UNCHECKED_CAST")
                override fun <T : ViewModel> create(
                    key: String,
                    modelClass: Class<T>,
                    handle: SavedStateHandle
                ): T {
                    return AccountViewModel(repo) as T
                }
            }
    }
}

@Component(
    dependencies = [CoursesManagerProvider::class]
)
interface AccountComponent {
    fun inject(accountFragment: AccountFragment)

    companion object {
        fun create(coursesManagerProvider: CoursesManagerProvider): AccountComponent {
            return DaggerAccountComponent.factory().create(coursesManagerProvider)
        }
    }

    @Component.Factory
    interface Factory {
        fun create(
            coursesManagerProvider: CoursesManagerProvider
        ): AccountComponent
    }
}

interface AccountAdapterManager: CoursesAdapterManager, ProfileManager

interface CoursesAdapterManager{
    val dataSet: List<Course>
    suspend fun onFavoriteClick(position: Int)
}

interface ProfileManager{
    fun onFirstButtonClick()
    fun onSecondButtonClick()
    fun onThirdButtonClick()
}

class AccountAdapter(
    val manager: AccountAdapterManager
) :
    RecyclerView.Adapter<AccountAdapter.AccountAdapterViewHolder>() {

    sealed class AccountAdapterViewHolder(view: View) : RecyclerView.ViewHolder(view)

    inner class CourseViewHolder(view: View) : AccountAdapterViewHolder(view) {
        val favorite: ImageView = view.findViewById(com.sanazi.list.presentation.R.id.favorite)
        val offerRate: TextView = view.findViewById(com.sanazi.list.presentation.R.id.offerRate)
        val offerDate: TextView = view.findViewById(com.sanazi.list.presentation.R.id.offerDate)
        val offerTitle: TextView = view.findViewById(com.sanazi.list.presentation.R.id.offerTitle)
        val offerText: TextView = view.findViewById(com.sanazi.list.presentation.R.id.offerText)
        val offerPrise: TextView = view.findViewById(com.sanazi.list.presentation.R.id.offerPrise)

        fun bind(course: Course, position: Int) {
            offerRate.text = course.rate
            offerDate.text = course.startDate
            offerTitle.text = course.title
            offerText.text = course.text
            offerPrise.text = itemView.resources.getString(
                com.sanazi.list.presentation.R.string.price,
                manager.dataSet[position].price
            )
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
            else -> com.sanazi.list.presentation.R.layout.offer_element
        }
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): AccountAdapterViewHolder {
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(viewType, viewGroup, false)
        return when (viewType) {
            com.sanazi.list.presentation.R.layout.offer_element -> {
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
        return if (hasLike) com.sanazi.list.presentation.R.drawable.fill_favorite_icon else com.sanazi.common_ui.R.drawable.favorite_icon
    }

    override fun getItemCount() = manager.dataSet.size + 2
}