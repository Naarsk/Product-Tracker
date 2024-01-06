package com.example.product_tracker

import android.util.Log
import java.io.File
import java.io.FileInputStream
import java.io.FileNotFoundException
import java.io.FileOutputStream
import java.io.InputStream
import java.io.OutputStream

class Utils{
    fun copyFile(inputFilePath: String, outputPath: String, outputFile: String): String {
        val inputStream: InputStream?
        val outputStream: OutputStream?
        try {

            //create output directory if it doesn't exist
            val dir = File(outputPath)
            if (!dir.exists()) {
                dir.mkdirs()
            }
            inputStream = FileInputStream(inputFilePath)
            outputStream = FileOutputStream(outputPath + outputFile)
            val buffer = ByteArray(1024)
            var read: Int
            while (inputStream.read(buffer).also { read = it } != -1) {
                outputStream.write(buffer, 0, read)
            }
            inputStream.close()

            // write the output file (You have now copied the file)
            outputStream.flush()
            outputStream.close()
        } catch (fnfe1: FileNotFoundException) {
            Log.e("copyFile", fnfe1.message!!)
        } catch (e: Exception) {
            Log.e("copyFile", e.message!!)
        }
        return outputPath + outputFile
    }
    fun deleteFile(inputFilePath: String) {
        try {
            // delete the original file
            File(inputFilePath).delete()
        } catch (e: java.lang.Exception) {
            Log.e("tag", e.message!!)
        }
    }
}
