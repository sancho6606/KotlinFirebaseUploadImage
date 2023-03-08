package com.sancho.kotlinfirebaseuploadimage.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bumptech.glide.Glide
import com.sancho.kotlinfirebaseuploadimage.R
import com.sancho.kotlinfirebaseuploadimage.databinding.ActivityMain2Binding

class MainActivity2 : AppCompatActivity() {
    lateinit var binding: ActivityMain2Binding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityMain2Binding.inflate(layoutInflater)
        setContentView(binding.root)

        val intent=intent
        var name=intent.getStringExtra("name")
        var url=intent.getStringExtra("url")


        binding.apply {
            textviewopennew.text=name
            Glide.with(imageviewopennew).load(url).into(imageviewopennew)
        }

    }
}