package com.sanazi.account

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import androidx.core.net.toUri
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavDeepLinkRequest
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.sanazi.common_ui.getAppDependenciesProvider
import com.sanazi.list.domain.Course
import com.sanazi.list.domain.CoursesManager
import kotlinx.coroutines.launch
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
                override val dataSet: List<AccountCourse>
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

data class AccountCourse(
    val id: Int,
    val title: String,
    val rate: String,
    val startDate: String,
    val hasLike: Boolean,
    val doneLessons: Int = 10,
    val totalLessons: Int = 52
)