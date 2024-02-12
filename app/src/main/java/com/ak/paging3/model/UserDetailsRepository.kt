package com.ak.paging3.model

import android.annotation.SuppressLint
import androidx.lifecycle.LiveData
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.liveData
import com.ak.paging3.api.ApiService
import retrofit2.Response
import javax.inject.Inject

/**
 * Created by Ajithkumar Vadamalai on 14/12/23.
 */

class UserDetailsRepository @Inject constructor(private val apiService: ApiService) {

    @SuppressLint("Range")
    fun getAllUser(): LiveData<PagingData<User>>{
        return Pager(config = PagingConfig(pageSize = 10, enablePlaceholders = true, initialLoadSize = 10),
            pagingSourceFactory = {
                PaginationSource(apiService)
            }, initialKey = 0 ).liveData
    }
    suspend fun getUser(userId:Int): Response<User> = apiService.getUser(userId)
}