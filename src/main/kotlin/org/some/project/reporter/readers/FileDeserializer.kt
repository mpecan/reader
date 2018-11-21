package org.some.project.reporter.readers

import org.some.project.reporter.DataStore
import org.some.project.reporter.dto.Contractor
import org.some.project.reporter.dto.Employee
import org.some.project.reporter.dto.Workflow
import org.some.project.reporter.dto.WorkflowInstance
import java.io.File
import java.nio.charset.Charset

class FileDeserializer(val target: DataStore) {


    private fun String?.asSupplier() = when (this) {
        "WORKFLOW INSTANCES" -> ::WorkflowInstance
        "WORKFLOWS" -> ::Workflow
        "EMPLOYEES" -> ::Employee
        "CONTRACTORS" -> ::Contractor
        else -> null
    }

    fun processFile(file: File){
        if(file.canRead()){
            println("Processing file ${file.path}")
            file.bufferedReader(Charset.forName("UTF-8")).use { reader ->
                val supplier = reader.readLine().asSupplier() ?: return
                val data = GenericDeserializer(supplier).readData(reader)
                target.ingest(data)
                data
            }
        }
    }

    fun processDirectory(path: String){
        val directory = File(path)
        if(directory.canRead() && directory.isDirectory){
            directory.listFiles().filter { it.extension == "data" }.forEach(this::processFile)
        } else{
            println("Directory either does not exist or cannot be read ${directory.absolutePath}")
        }
    }

}