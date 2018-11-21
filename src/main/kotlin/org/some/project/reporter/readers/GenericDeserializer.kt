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
    private var lastField = ""
    private var curObj: T = supplier.invoke()
    private val fieldMembers = curObj::class.members.map { it.name to it }.toMap()

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

    public fun readData(reader: BufferedReader): List<T> {
        val values = mutableListOf<T>()
        reader.lines().forEach {
            if (it.trim().isEmpty()) {
                // do nothing
            } else if (it.contains(START)) {
                processingObject = true
                objectValid = true
            } else if (it.contains(END)) {
                if (objectValid) {
                    values.add(curObj)
                } else {
                    println("Discarded object $curObj as the fields were not valid.")
                }
                curObj = supplier.invoke()
                processingObject = false
            } else {
                val (field, value) = it.split(":").map { data -> data.trim() }
                if (field == "") {
                    objectValid = false
                }
                val callable = fieldMembers[field]
                if (callable is KMutableProperty<*>) {
                    callable.applyField(value)
                }
            }
        }
        return values
    }

}
