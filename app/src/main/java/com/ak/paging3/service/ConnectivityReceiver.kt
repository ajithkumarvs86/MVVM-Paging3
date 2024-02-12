package com.ak.paging3.service

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AlertDialog
import com.ak.paging3.utils.isNetworkAvailable


/**
 * Created by Ajithkumar Vadamalai on 14/12/23.
 */

class ConnectivityReceiver: BroadcastReceiver() {

    private var alertDialog:AlertDialog? =null

    override fun onReceive(context: Context?, intent: Intent?) {
        context?.let {
            if (!isNetworkAvailable(context)){
                showAlert(context)
            }else{
                alertDialog?.let {
                    it.dismiss()
                }
            }
        }
    }

    private fun showAlert(context: Context){
        val builder = AlertDialog.Builder(context)
        builder.apply {
            setCancelable(false)
            setTitle("No Internet")
            setMessage("Internet not available, Cross check your internet connectivity.")
        }
        alertDialog = builder.create()
        alertDialog!!.show()
    }
}