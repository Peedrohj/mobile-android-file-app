package com.example.atividade01

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.atividade01.adapter.FileAdapter
import com.example.atividade01.data.File
import kotlinx.android.synthetic.main.activity_main.*

open class MainActivity : AppCompatActivity(), FileAdapter.OnItemClickListener {
    private var baseList: ArrayList<File> = generateBaseList(5)
    private var adapter = FileAdapter(baseList, this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        file_list.adapter = adapter
        file_list.layoutManager = LinearLayoutManager(this)
        file_list.setHasFixedSize(true)
    }

    private fun generateBaseList(size: Int): ArrayList<File> {
        val list = ArrayList<File>()

        for (i in 0 until size) {
            val item = File(
                name = "File: $i", isInternal = false
            )

            list += item
        }

        return list
    }

    override fun onItemClick(position: Int) {
        TODO("Not yet implemented")
    }
}