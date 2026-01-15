package com.sanazi.favorite

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.sanazi.common_ui.getAppDependenciesProvider
import com.sanazi.dependencies.CoursesManagerProvider
import com.sanazi.em.presentation.bar.ListViewModel
import com.sanazi.list.domain.Course
import com.sanazi.list.domain.CoursesManager
import com.sanazi.list.presentation.CustomAdapter
import dagger.Component
import kotlinx.coroutines.launch
import javax.inject.Inject

class FavoriteFragment : Fragment(R.layout.favorite_layout){
    private lateinit var viewModel: ListViewModel
    @Inject
    lateinit var repository: CoursesManager

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        FavoriteComponent.create(getAppDependenciesProvider()).inject(this)
        viewModel = ViewModelProvider(
            this,
            ListViewModel.provideFactory(repository, this)
        )[ListViewModel::class.java]
        val customAdapter = CustomAdapter(viewModel)
        val recyclerView: RecyclerView = view.findViewById(R.id.offerList)
        recyclerView.layoutManager = LinearLayoutManager(view.context)
        recyclerView.adapter = customAdapter
        lifecycleScope.launch {
            viewModel.update { listFilter(it) }
            customAdapter.notifyItemRangeInserted(0, viewModel.dataSet.size)
        }
    }

    fun listFilter(courses: List<Course>): List<Course>{
        return courses.filter { it.hasLike }
    }
}

@Component(
    dependencies = [CoursesManagerProvider::class]
)
interface FavoriteComponent{
    fun inject(favoriteFragment: FavoriteFragment)

    companion object{
        fun create(coursesManagerProvider: CoursesManagerProvider): FavoriteComponent{
            return DaggerFavoriteComponent.factory().create(coursesManagerProvider)
        }
    }

    @Component.Factory
    interface Factory {
        fun create(
            coursesManagerProvider: CoursesManagerProvider
        ): FavoriteComponent
    }
}