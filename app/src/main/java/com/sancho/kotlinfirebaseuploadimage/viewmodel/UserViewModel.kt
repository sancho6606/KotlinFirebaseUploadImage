package com.sancho.kotlinfirebaseuploadimage.viewmodel

import android.net.Uri
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sancho.kotlinfirebaseuploadimage.model.User
import com.sancho.kotlinfirebaseuploadimage.model.UserRepository

class UserViewModel constructor(
    val userRepository: UserRepository= UserRepository()
): ViewModel() {

    fun uploadimagetofirebase(uri:Uri, name: String){
        userRepository.uploadimage(name,uri)
    }

    fun readalldata():MutableLiveData<ArrayList<User>>{
        return userRepository.readfromfirebase()
    }

    fun showprogresssuccesfully(loaded: Boolean): MutableLiveData<Boolean> {
        return userRepository.succesfull(loaded)
    }

}