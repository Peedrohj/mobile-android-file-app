package com.example.atividade01

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.atividade01.adapter.FileAdapter
import com.example.atividade01.data.FileData
import kotlinx.android.synthetic.main.activity_main.*
import java.io.File
import java.io.FileOutputStream

open class MainActivity : AppCompatActivity(), FileAdapter.OnItemClickListener {
    private var baseList: ArrayList<FileData> = generateBaseList(0)
    private var adapter = FileAdapter(baseList, this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        file_list.adapter = adapter
        file_list.layoutManager = LinearLayoutManager(this)
        file_list.setHasFixedSize(true)


        create_btn.setOnClickListener {
            val checkedbtn = btn_group.checkedRadioButtonId
            var isInternal = true

            if (checkedbtn == external_btn.id) {
                isInternal = false
            }

            val fileName = file_name_input.text.toString()
            val fileContents = file_content_input.text.toString()

            if (fileName.isEmpty()) {
                Toast.makeText(this, "Provide at least the file name", Toast.LENGTH_SHORT).show()
            } else {
                if (isInternal) {
                    try {
                        this.openFileOutput(fileName, MODE_PRIVATE).use {
                            it.write(fileContents.toByteArray())
                        }

                        Toast.makeText(this, "File created", Toast.LENGTH_SHORT).show()
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                } else {
                    val file = File(this.getExternalFilesDir(null), fileName)
                    val fileOutputStream = FileOutputStream(file)

                    try {
                        fileOutputStream.use { stream ->
                            stream.write(fileContents.toByteArray())
                        }

                        Toast.makeText(
                            this,
                            "File created ${this.getExternalFilesDir(null)}",
                            Toast.LENGTH_SHORT
                        ).show()
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }

                }
            }

            val files: Array<String> = this.fileList()

            println("DEBUG FILES: $files")


        }

    }


    private fun generateBaseList(size: Int): ArrayList<FileData> {
        val list = ArrayList<FileData>()

        for (i in 0 until size) {
            val item = FileData(
                name = "File: $i", isInternal = false
            )

            list += item
        }

        return list
    }

    override fun onItemClick(position: Int) {
        Toast.makeText(this, "Item $position clicked", Toast.LENGTH_SHORT).show()

    }
}