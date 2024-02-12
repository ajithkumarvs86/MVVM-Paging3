package com.ak.paging3.api

import com.ak.paging3.model.User
import com.ak.paging3.model.UserListResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query


/**
 * Created by Ajithkumar Vadamalai on 14/12/23.
 */


interface ApiService {

    @GET("/users")
    suspend fun getUserList(@Query("limit") limit: Int,@Query("skip") skip: Int) : Response<UserListResponse>

    @GET("/users/{userId}")
    suspend fun getUser(@Path("userId") userId: Int) : Response<User>

}