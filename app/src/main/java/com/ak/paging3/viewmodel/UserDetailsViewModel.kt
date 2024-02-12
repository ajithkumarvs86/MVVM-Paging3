package com.ak.paging3.viewmodel

import android.net.http.HttpException
import android.os.Build
import androidx.annotation.RequiresExtension
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.ak.paging3.model.User
import com.ak.paging3.model.UserDetailsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.net.SocketTimeoutException
import javax.inject.Inject

/**
 * Created by Ajithkumar Vadamalai on 14/12/23.
 */

@HiltViewModel
class UserDetailsViewModel @Inject constructor(
    private val userDetailsRepository: UserDetailsRepository
) :ViewModel() {

    private val _user = MutableLiveData<User>()
    private val _error= MutableLiveData<String>()
    val user :LiveData<User> get() = _user
    val error :LiveData<String> get() = _error

    fun getAllUser():LiveData<PagingData<User>> = userDetailsRepository.getAllUser().cachedIn(viewModelScope)

    @RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
    fun getUser(userId:Int){
        viewModelScope.launch {
            try {
                val response = userDetailsRepository.getUser(userId)
                if (response.isSuccessful){
                    _user.value = response.body()
                }else{
                    _error.value = response.errorBody().toString()
                }
            }catch (e: HttpException) {
                _error.value = "Service Unavailable!"
            }catch (e: SocketTimeoutException) {
                _error.value = "Request Timeout!"
            }
            catch (e: Exception) {
                _error.value = "Unable to Reach Server!"
            }
            catch (e:Exception){
                _error.value = e.message
            }

        }
    }



}