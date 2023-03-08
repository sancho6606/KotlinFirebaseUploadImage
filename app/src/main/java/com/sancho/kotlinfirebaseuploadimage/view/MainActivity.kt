package com.sancho.kotlinfirebaseuploadimage.view

import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.bumptech.glide.Glide
import com.sancho.kotlinfirebaseuploadimage.databinding.ActivityMainBinding
import com.sancho.kotlinfirebaseuploadimage.viewmodel.UserViewModel

class MainActivity : AppCompatActivity() {
     var imageuri:Uri?=null
    lateinit var binding: ActivityMainBinding
    lateinit var userViewModel: UserViewModel
    lateinit var userAdapter: UserAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        userViewModel=ViewModelProvider(this@MainActivity).get(UserViewModel::class.java)

        binding.imageviewopengallery.setOnClickListener{
            openFileChooser()
        }
        binding.buttonopengallery.setOnClickListener{
            userViewModel.uploadimagetofirebase(uri = imageuri!!,binding.edittextimagename.text.toString())
        }

        userViewModel.showprogresssuccesfully(false).observe(this@MainActivity,{
            if (it){
                showprogeressbar()
            }else{
             hideprogeressbar()
            }
        })

        binding.recyclerview1.layoutManager=GridLayoutManager(this@MainActivity,3)
        userViewModel.readalldata().observe(this@MainActivity,{
            userAdapter=UserAdapter(this@MainActivity,it)
            binding.recyclerview1.adapter=userAdapter
        })

    }
    fun openFileChooser() {
        getContent.launch("image/*")
    }
    val getContent = registerForActivityResult(ActivityResultContracts.GetContent())  { uri: Uri? ->
        binding.imageviewopengallery.setImageURI(uri)
        if(uri!=null){
            imageuri=uri
        }
    }

    fun showprogeressbar(){
        binding.progressbaruploadimage.visibility=View.VISIBLE
    }
    fun hideprogeressbar(){
        binding.progressbaruploadimage.visibility=View.INVISIBLE
    }

}