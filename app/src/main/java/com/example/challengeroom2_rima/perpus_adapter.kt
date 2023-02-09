package com.example.challengeroom2_rima

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.challengeroom2_rima.room.tbBuku
import kotlinx.android.synthetic.main.activity_perpus_adapter.view.*

class perpus_adapter (private val book: ArrayList<tbBuku>,private val listener: onAdapterListener)
    : RecyclerView.Adapter<perpus_adapter.bukuViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): bukuViewHolder {
        return bukuViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.activity_perpus_adapter,parent,false)
        )
    }

    override fun getItemCount() = book.size

    override fun onBindViewHolder(holder: bukuViewHolder, position: Int) {
        val detailbuku = book[position]
        holder.view.tvKategori.text = detailbuku.kategori
        holder.view.tvJudul.text = detailbuku.judul
        holder.view.tvJudul.setOnClickListener{
            listener.onClick(detailbuku)
        }
        holder.view.icon_edit.setOnClickListener{
            listener.onUpdate(detailbuku)
        }
        holder.view.icon_delete.setOnClickListener{
            listener.onDelete(detailbuku)
        }
    }

    class bukuViewHolder(val view: View) : RecyclerView.ViewHolder(view)

    fun setData(list: List<tbBuku>){
        book.clear()
        book.addAll(list)
        notifyDataSetChanged()
    }

    interface onAdapterListener{
        fun onClick(buku: tbBuku)
        fun onUpdate(buku: tbBuku)
        fun onDelete(buku: tbBuku)
    }
    }


