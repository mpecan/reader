package org.some.project.reporter

import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.parameters.options.default
import com.github.ajalt.clikt.parameters.options.option
import com.github.ajalt.clikt.parameters.options.prompt
import com.github.ajalt.clikt.parameters.types.int
import org.some.project.reporter.readers.FileDeserializer


class Runner: CliktCommand() {
    val path: String by option(help = "The relative or absolute path to the folder where the data is").default(".")
    val operation: Int by option().int().prompt("Select mode of operation \n" +
            " 1. All workflows with instances\n" +
            " 2. Workflows with running instances\n" +
            " 3. Contractors assigned to running instances\n")
    override fun run() {
        val store = DataStore()
        val loader = FileDeserializer(store)
        loader.processDirectory(path)
        when(operation){
            1 -> store.printWorkflowsWithInstances()
            2 -> store.printWorkflowsWithRunningAndNumber()
            3 -> store.printAllContractorsWithRunningWorkflows()
            else -> println("Invalid selection")
        }
    }

}

fun main(args: Array<String>) {
    Runner().main(args)
}
