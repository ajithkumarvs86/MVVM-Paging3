package com.ak.paging3.model

import android.net.http.HttpException
import android.os.Build
import androidx.annotation.RequiresExtension
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.ak.paging3.api.ApiService
import java.net.SocketTimeoutException
import javax.inject.Inject

/**
 * Created by Ajithkumar Vadamalai on 14/12/23.
 */

class PaginationSource @Inject constructor(private val apiService: ApiService):PagingSource<Int,User>() {

    companion object {
        private const val STARTING_PAGE_INDEX = 0
    }

    override fun getRefreshKey(state: PagingState<Int, User>): Int? {
        return state.anchorPosition?.let { anchorPosition->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(10) ?:
            state.closestPageToPosition(anchorPosition)?.nextKey?.minus(10)
        }
    }

    @RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, User> {

        return try {
            val position = params.key ?: STARTING_PAGE_INDEX
            val result = apiService.getUserList(params.loadSize, position )
            if(result.isSuccessful){
                LoadResult.Page(data = result.body()!!.users,
                    prevKey = if(position == 0) null else position-10,
                    nextKey = if (position + 10 < result.body()!!.total) position + 10 else null)
            } else{
                LoadResult.Error(Throwable("Something Went Wrong!"))
            }
        }catch (e: HttpException) {
            LoadResult.Error(Throwable("Service Unavailable!"))
        }catch (e: SocketTimeoutException) {
            LoadResult.Error(Throwable("Request Timeout!"))
        }
        catch (e: Exception) {
            LoadResult.Error(Throwable("Unable to Reach Server!"))
        }

    }

}
