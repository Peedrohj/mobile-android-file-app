package com.example.atividade01

import android.content.Context
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.security.crypto.EncryptedFile
import androidx.security.crypto.MasterKeys
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

            // Check if the fileName isn't null
            if (fileName.isEmpty()) {
                Toast.makeText(this, "Provide at least the file name", Toast.LENGTH_SHORT).show()
            } else {

                // Check if will create an internal or external file
                if (isInternal) {

                    // Check if will use jetpack or not
                    if (!useJetpack.isChecked) {
                        this.createInternalFile(fileName, fileContents)
                    } else {
                        val file = File(filesDir, fileName)

                        if (file.exists()) {
                            file.delete()
                        }

                        createEncryptedFile(file, fileContents)
                    }

                } else {

                    // Check if will use jetpack or not
                    if (!useJetpack.isChecked) {
                        this.createExternalFile(fileName, fileContents)
                    } else {
                        val file = File(getExternalFilesDir(null), fileName)

                        if (file.exists()) {
                            file.delete()
                        }

                        createEncryptedFile(file, fileContents)
                    }
                }
            }
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

    private fun createEncryptedFile(file: File, content: String) {
        val context: Context = applicationContext
        val keyGenParameterSpec = MasterKeys.AES256_GCM_SPEC
        val masterKeyAlias = MasterKeys.getOrCreate(keyGenParameterSpec)

        val encryptedFile = EncryptedFile.Builder(
            file,
            context,
            masterKeyAlias,
            EncryptedFile.FileEncryptionScheme.AES256_GCM_HKDF_4KB
        ).build()

        encryptedFile.openFileOutput().bufferedWriter().use { bufferedWriter ->
            bufferedWriter.write(content)
        }
    }

    private fun createInternalFile(fileName: String, fileContents: String) {
        try {
            this.openFileOutput(fileName, MODE_PRIVATE).use {
                it.write(fileContents.toByteArray())
            }

            Toast.makeText(this, "File created", Toast.LENGTH_SHORT).show()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun createExternalFile(fileName: String, fileContents: String) {
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

    override fun onItemClick(position: Int) {
        Toast.makeText(this, "Item $position clicked", Toast.LENGTH_SHORT).show()

    }
}
