package com.programmer2704.movapp.data

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.programmer2704.movapp.data.local.TopratedEntity
import com.programmer2704.movapp.data.remote.MovieApi
import retrofit2.HttpException
import java.io.IOException

private const val TOPRATED_STARTING_PAGE_INDEX = 1

class TopratedPagingSource(
    private val api: MovieApi,
) : PagingSource<Int, TopratedEntity>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, TopratedEntity> {
        val position = params.key ?: TOPRATED_STARTING_PAGE_INDEX

        return try {
            //| page and total_pages
            //? should use page and total_results?
            val response = api.getTopRatedMovieTry2(position)
            val toprateds = response.results

            LoadResult.Page(
                data = toprateds,
                prevKey = if (position == TOPRATED_STARTING_PAGE_INDEX) null else position - 1,
                nextKey = if (toprateds.isEmpty()) null else position + 1
            )
        } catch (exception: IOException) {
            LoadResult.Error(exception)
        } catch (exception: HttpException) {
            LoadResult.Error(exception)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, TopratedEntity>): Int? {
        return state.anchorPosition?.let {
            state.closestPageToPosition(it)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(it)?.nextKey?.minus(1)
        }
    }
}