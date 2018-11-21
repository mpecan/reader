package org.some.project.reporter.readers

import java.io.BufferedReader
import kotlin.reflect.KMutableProperty
import kotlin.reflect.full.starProjectedType

class GenericDeserializer<T : Any>(private val supplier: () -> T) {

    companion object {
        const val START = "start"
        const val END = "end"
    }

    private var processingObject = false
    private var objectValid = true
    private var curObj: T = supplier.invoke()
    private val fieldMembers = curObj::class.members.map { it.name to it }.toMap()
    private val values = mutableListOf<T>()

    public fun readData(reader: BufferedReader): List<T> {
        reader.lines().filter { !it.trim().isEmpty() }.forEach {
            when {
                !objectValid && it.contains(END) -> {
                    commitOrDiscard()
                }
                it.contains(START) -> {
                    processingObject = true
                }
                it.contains(END) -> {
                    commitOrDiscard()
                }
                else -> {
                    processLine(it)
                }
            }
        }
        return values
    }

    private fun processLine(it: String) {
        val (field, value) = it.split(":").map { data -> data.trim() }
        if (field == "") {
            objectValid = false
            return
        }
        
        val callable = fieldMembers[field]
        if (callable is KMutableProperty<*>) {
            callable.applyField(value)
        } else {
            objectValid = false
        }
    }

    private fun commitOrDiscard() {
        if (objectValid) {
            values.add(curObj)
        } else {
            println("Discarded object $curObj as the fields were not valid.")
        }
        curObj = supplier.invoke()
        processingObject = false
        objectValid = true
    }

    private fun KMutableProperty<*>.applyField(value: String) {
        this.let { it }.let {
            when (returnType) {
                String::class.starProjectedType -> setter.call(curObj, value)
                Int::class.starProjectedType -> setter.call(curObj, value.toInt())
                Double::class.starProjectedType -> setter.call(curObj, value.toDouble())
                Long::class.starProjectedType -> setter.call(curObj, value.toLong())
                else -> throw RuntimeException("Unsupported type $returnType")
            }
        }
    }

}
