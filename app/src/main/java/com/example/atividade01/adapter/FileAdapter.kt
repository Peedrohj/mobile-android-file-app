package com.example.atividade01.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.atividade01.R
import com.example.atividade01.data.FileData
import kotlinx.android.synthetic.main.file_item.view.*

class FileAdapter(
    private val fileDataList: List<FileData>,
    private val listener: OnItemClickListener
) : RecyclerView.Adapter<FileAdapter.FileHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FileHolder {
        val fileView =
            LayoutInflater.from(parent.context).inflate(R.layout.file_item, parent, false)

        return FileHolder(fileView)
    }

    override fun onBindViewHolder(holder: FileHolder, position: Int) {
        val currentItem = fileDataList[position]

        holder.fileName!!.text = currentItem.name
    }

    override fun getItemCount(): Int {
        return fileDataList.size
    }

    inner class FileHolder(fileView: View) : RecyclerView.ViewHolder(fileView),
        View.OnClickListener {
        val fileName: TextView? = fileView.file_name

        init {
            itemView.delete_btn.setOnClickListener(this)
            itemView.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            val position: Int = adapterPosition
            var isDelete = v?.id  == R.id.delete_btn

            if (position != RecyclerView.NO_POSITION) {

                if (isDelete) {
                    listener.onDeleteButtonClick(position)
                }else{
                    listener.onItemClick(position)
                }
            }
        }
    }

    interface OnItemClickListener {
        fun onDeleteButtonClick(position: Int)
        fun onItemClick(position: Int)
    }
}
