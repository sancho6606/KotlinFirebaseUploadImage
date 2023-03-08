package com.sancho.kotlinfirebaseuploadimage.view

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.sancho.kotlinfirebaseuploadimage.databinding.RecyclerviewItemBinding
import com.sancho.kotlinfirebaseuploadimage.model.User

class UserAdapter constructor(
    val context: Context,
    val arrayList: ArrayList<User>
):RecyclerView.Adapter<UserAdapter.UserViewHolder>() {

    val databaseReference: DatabaseReference = FirebaseDatabase.getInstance().getReference().child("Users")

    class UserViewHolder(val binding:RecyclerviewItemBinding):RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val view=RecyclerviewItemBinding.inflate(LayoutInflater.from(context),parent,false)
        return UserViewHolder(view)
    }
    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        holder.binding.apply {
            textviewread.text=arrayList.get(position).name
            //Glide
           Glide.with(context)
               .load(arrayList.get(position).image)
               //.placeholder(com.sancho.kotlinfirebaseuploadimage.R.drawable.noimage)
               .centerCrop()
               .listener(object : RequestListener<Drawable>{
                   override fun onResourceReady(
                       resource: Drawable?,
                       model: Any?,
                       target: Target<Drawable>?,
                       dataSource: DataSource?,
                       isFirstResource: Boolean
                   ): Boolean {
                       progressbarimageread.visibility= View.GONE
                       return false
                   }

                   override fun onLoadFailed(
                       e: GlideException?,
                       model: Any?,
                       target: Target<Drawable>?,
                       isFirstResource: Boolean
                   ): Boolean {
                       progressbarimageread.visibility= View.GONE
                       return false
                   }
               })
               .into(imageviewread)
            //Glide

            linearlayread.setOnLongClickListener {
                val alertDialog=AlertDialog.Builder(context)
                alertDialog.setTitle("Delete")
                alertDialog.setMessage(arrayList.get(position).name)
                alertDialog.setPositiveButton("Yes",object :DialogInterface.OnClickListener{
                    override fun onClick(p0: DialogInterface?, p1: Int) {
                        databaseReference.child(arrayList.get(position).pushkey).removeValue()
                    }
                })
                alertDialog.setNegativeButton("No",object :DialogInterface.OnClickListener{
                    override fun onClick(p0: DialogInterface?, p1: Int) {

                    }
                })

                alertDialog.create().show()
                return@setOnLongClickListener true
            }
            linearlayread.setOnClickListener {
                val intent=Intent(context,MainActivity2::class.java)
                intent.putExtra("url",arrayList.get(position).image)
                intent.putExtra("name",arrayList.get(position).name)
                context.startActivity(intent)
            }

        }
    }
    override fun getItemCount(): Int =arrayList.size
    }