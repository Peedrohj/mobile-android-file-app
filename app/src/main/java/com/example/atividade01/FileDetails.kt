package com.example.atividade01

import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.security.crypto.EncryptedFile
import androidx.security.crypto.MasterKeys
import com.example.atividade01.data.FileData
import kotlinx.android.synthetic.main.activity_file_details.*
import java.io.File
import java.io.FileInputStream
import java.io.IOException
import java.lang.Exception

class FileDetails : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_file_details)

        val fileData: FileData =
            intent.getParcelableExtra<FileData>(MainActivity.DETAILS_FILE) ?: return

        fileName.text = fileData.name

        if (fileData.isInternal) {
            val file = File(filesDir, fileData.name)
            var content = ""

            // Try to read encrypted file
            try {
                content = readEncryptedFile(file)
            } catch (e: IOException) {

                // Check if the error occurred because the file is not encrypted
                if (e.toString() == "java.io.IOException: No matching key found for the ciphertext in the stream.") {
                    val inputStream = FileInputStream(file)
                    content = inputStream.bufferedReader().use { it.readText() }
                }else{
                    throw Exception(e)
                }
            }

            fileContent.text = content
        } else {
            val file = File(this.getExternalFilesDir(null), fileData.name)
            var content = ""

            // Try to read encrypted file
            try {
                content = readEncryptedFile(file)
            } catch (e: IOException) {

                // Check if the error occurred because the file is not encrypted
                if (e.toString() == "java.io.IOException: No matching key found for the ciphertext in the stream.") {
                    val inputStream = FileInputStream(file)
                    content = inputStream.bufferedReader().use { it.readText() }
                }else{
                    throw Exception(e)
                }
            }


            fileContent.text = content

        }
    }

    private fun readEncryptedFile(file: File): String {
        val context: Context = applicationContext
        val keyGenParameterSpec = MasterKeys.AES256_GCM_SPEC
        val masterKeyAlias = MasterKeys.getOrCreate(keyGenParameterSpec)
        var result = ""

        val encryptedFile = EncryptedFile.Builder(
            file,
            context,
            masterKeyAlias,
            EncryptedFile.FileEncryptionScheme.AES256_GCM_HKDF_4KB
        ).build()


        encryptedFile.openFileInput().use { reader ->
            result = reader.bufferedReader().use { it.readText() }
        }

        return result
    }
}