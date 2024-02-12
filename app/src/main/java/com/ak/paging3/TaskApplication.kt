package com.ak.paging3

import android.app.Application
import com.ak.paging3.model.UserDetailsRepository
import dagger.hilt.android.HiltAndroidApp

/**
 * Created by Ajithkumar Vadamalai on 14/12/23.
 */

@HiltAndroidApp
class TaskApplication: Application() {
    override fun onCreate() {
        super.onCreate()
    }
}