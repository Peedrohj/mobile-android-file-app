package com.example.atividade01.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.atividade01.MainActivity
import com.example.atividade01.R
import com.example.atividade01.data.File
import kotlinx.android.synthetic.main.file_item.view.*

class FileAdapter(private val fileList: List<File>, private val listener: OnItemClickListener) : RecyclerView.Adapter<FileAdapter.FileHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FileHolder {
        val fileView = LayoutInflater.from(parent.context).inflate(R.layout.file_item, parent, false)

        return FileHolder(fileView)
    }

    override fun onBindViewHolder(holder: FileHolder, position: Int) {
        val currentItem = fileList[position]

        holder.fileName!!.text = currentItem.name
    }

    override fun getItemCount(): Int {
        return fileList.size
    }

    inner class FileHolder(fileView: View): RecyclerView.ViewHolder(fileView), View.OnClickListener {
        val fileName: TextView? = fileView.file_name

        init {
            itemView.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            val position: Int = adapterPosition
            if(position != RecyclerView.NO_POSITION){
                listener.onItemClick(position)

            }
        }
    }

    interface OnItemClickListener{
        fun onItemClick(position: Int)
    }
}
