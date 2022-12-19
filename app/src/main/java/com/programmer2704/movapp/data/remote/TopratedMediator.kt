package com.programmer2704.movapp.data.remote

import android.util.Log
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.programmer2704.movapp.data.local.MyDB
import com.programmer2704.movapp.data.local.TopratedEntity
import com.programmer2704.movapp.data.local.remotekeys.RemoteKeys
import retrofit2.HttpException
import java.io.IOException

private const val STARTING_PAGE_INDEX = 1

@ExperimentalPagingApi
class TopratedMediator(
    private val db: MyDB,
    private val api: MovieApi
): RemoteMediator<Int, TopratedEntity>() {

    override suspend fun initialize(): InitializeAction {
        // Launch remote refresh as soon as paging starts and do not trigger remote prepend or
        // append until refresh has succeeded. In cases where we don't mind showing out-of-date,
        // cached offline data, we can return SKIP_INITIAL_REFRESH instead to prevent paging
        // triggering remote refresh.
        return InitializeAction.LAUNCH_INITIAL_REFRESH
    }

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, TopratedEntity>
    ): MediatorResult{
        val page = when (loadType) {
            LoadType.REFRESH -> {
                val remoteKeys = getRemoteKeyClosestToCurrentPosition(state)
                Log.d("page"," refresh: ${remoteKeys?.nextKey?.minus(1)}")
                remoteKeys?.nextKey?.minus(1) ?: STARTING_PAGE_INDEX
            }
            LoadType.PREPEND -> {
                val remoteKeys = getRemoteKeyForFirstItem(state)

                // If remoteKeys is null, that means the refresh result is not in the database yet.
                // We can return Success with `endOfPaginationReached = false` because Paging
                // will call this method again if RemoteKeys becomes non-null.
                // If remoteKeys is NOT NULL but its prevKey is null, that means we've reached
                // the end of pagination for prepend.
                val prevKey = remoteKeys?.prevKey
                if (prevKey == null) {
                    return MediatorResult.Success(endOfPaginationReached = remoteKeys != null)
                }
                Log.d("page"," prepend: $prevKey")
                prevKey
            }
            LoadType.APPEND -> {
                val remoteKeys = getRemoteKeyForLastItem(state)

                // If remoteKeys is null, that means the refresh result is not in the database yet.
                // We can return Success with `endOfPaginationReached = false` because Paging
                // will call this method again if RemoteKeys becomes non-null.
                // If remoteKeys is NOT NULL but its prevKey is null, that means we've reached
                // the end of pagination for append.
                val nextKey = remoteKeys?.nextKey
                if (nextKey == null) {
                    return MediatorResult.Success(endOfPaginationReached = remoteKeys != null)
                }
                Log.d("page"," append: $nextKey")
                nextKey
            }
        }

        try {
            val apiResponse = api.getTopRatedMovieTry2(page)//20
//            val apiResponse = api.getTopRatedMovieTry2(page, totalPages = 2, totalResults = 20)//20
//            val apiResponse = api.getTopRatedMovieTry2(page, totalResults = 20)//20
//            val apiResponse = api.getTopRatedMovieTry2(page, totalPages = 20)//20

            val repos = apiResponse.results.map {
                //ng
                it
//                it.toTopratedEntity()
            }

            val endOfPaginationReached = repos.isEmpty()

            db.withTransaction {
                // clear all tables in the database
                if (loadType == LoadType.REFRESH) {
                    db.remoteKeysDao().clearRemoteKeys()
                    db.movieDao().deleteMovies()
                }
                val prevKey = if (page == STARTING_PAGE_INDEX) null else page - 1
                val nextKey = if (endOfPaginationReached) null else page + 1
                val keys = repos.map {
                    RemoteKeys(
//                        repoId = it.id.hashCode().toLong(),
                        repoId = it.hashCode().toLong(),
                        prevKey = prevKey,
                        nextKey = nextKey)
                }
                db.remoteKeysDao().insertAll(keys)
//                db.movieDao().insertMovies(repos)
            }

            return MediatorResult.Success(endOfPaginationReached = endOfPaginationReached)
        } catch (exception: IOException) {
            return MediatorResult.Error(exception)
        } catch (exception: HttpException) {
            return MediatorResult.Error(exception)
        }
    }

    private suspend fun getRemoteKeyForLastItem(state: PagingState<Int, TopratedEntity>): RemoteKeys? {
        // Get the last page that was retrieved, that contained items.
        // From that last page, get the last item
        return state.pages.lastOrNull() { it.data.isNotEmpty() }?.data?.lastOrNull()
            ?.let { repo ->
                // Get the remote keys of the last item retrieved
                val remoteKey = db.remoteKeysDao().remoteKeysRepoId(repo.id.hashCode().toLong())
                remoteKey
            }
    }

    private suspend fun getRemoteKeyForFirstItem(state: PagingState<Int, TopratedEntity>): RemoteKeys? {
        // Get the first page that was retrieved, that contained items.
        // From that first page, get the first item
        return state.pages.firstOrNull { it.data.isNotEmpty() }?.data?.firstOrNull()
            ?.let { repo ->
                // Get the remote keys of the first items retrieved
                db.remoteKeysDao().remoteKeysRepoId(repo.id.hashCode().toLong())
            }
    }

    private suspend fun getRemoteKeyClosestToCurrentPosition(
        state: PagingState<Int, TopratedEntity>
    ): RemoteKeys? {
        // The paging library is trying to load data after the anchor position
        // Get the item closest to the anchor position
        return state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)?.id?.let { repoId ->
                db.remoteKeysDao().remoteKeysRepoId(repoId.hashCode().toLong())
            }
        }
    }
}