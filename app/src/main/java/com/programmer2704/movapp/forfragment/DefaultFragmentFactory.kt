package com.programmer2704.movapp.forfragment

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentFactory
import com.programmer2704.movapp.view.fragment.PopularFragment
import com.programmer2704.movapp.view.fragment.UpcomingFragment
import javax.inject.Inject

class DefaultFragmentFactory @Inject constructor(
) : FragmentFactory() {

    override fun instantiate(
        classLoader: ClassLoader,
        className: String): Fragment {

        return when (className) {
            PopularFragment::class.java.name ->
                PopularFragment()
            UpcomingFragment::class.java.name ->
                UpcomingFragment()
            else -> super.instantiate(classLoader, className)
        }
    }
}