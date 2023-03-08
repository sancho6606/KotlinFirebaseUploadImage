package com.sancho.kotlinfirebaseuploadimage.model

import android.net.Uri
import androidx.lifecycle.MutableLiveData
import com.google.firebase.database.*
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference

class UserRepository constructor(

   val databaseReference:DatabaseReference= FirebaseDatabase.getInstance().getReference().child("Users"),
   val        storageReference:StorageReference= FirebaseStorage.getInstance().getReference().child("Images")

) {
    val mlivedata=MutableLiveData<Boolean>()
    val liveData=MutableLiveData<ArrayList<User>>()
    val arrayList=ArrayList<User>()
    fun  uploadimage(name:String, uri: Uri){

        if (uri != null) {
            succesfull(true)
            val filereference: StorageReference = storageReference.child(
                System.currentTimeMillis().toString() + "." + System.currentTimeMillis().toString()
            )
            filereference.putFile(uri)
                .addOnSuccessListener { filereference.downloadUrl.addOnSuccessListener {
                    var pushkey=databaseReference.push().key.toString()
                    val user=User(name,it.toString(),pushkey)
                    databaseReference.child(pushkey).setValue(user).addOnCanceledListener {

                    }
                    succesfull(false)
                } }
        }
    }

    fun readfromfirebase():MutableLiveData<ArrayList<User>>{
        databaseReference.addValueEventListener(object :ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                arrayList.clear()
                for (datasnapshot:DataSnapshot in snapshot.children){
                    val user=datasnapshot.getValue(User::class.java)
                    arrayList.add(user!!)
                }
                liveData.value=arrayList
            }

            override fun onCancelled(error: DatabaseError) {

            }
        })
        return liveData
    }

        fun succesfull(loaded:Boolean):MutableLiveData<Boolean>{

            mlivedata.value=loaded
            return mlivedata
        }

}