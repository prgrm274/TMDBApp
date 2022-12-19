package com.programmer2704.movapp.view.viewmodel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlin.coroutines.CoroutineContext

abstract class BaseViewmodel : ViewModel() {

    val parentJob = Job()

    val coroutineContext: CoroutineContext
        get() = parentJob + Dispatchers.Default

    val scope = CoroutineScope(coroutineContext)

}