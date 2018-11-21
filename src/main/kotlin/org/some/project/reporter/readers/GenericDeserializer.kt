package org.some.project.reporter.readers

import java.io.BufferedReader
import kotlin.reflect.KMutableProperty
import kotlin.reflect.full.starProjectedType

class GenericDeserializer<T : Any>(private val supplier: () -> T) {

    private var inObject = false
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
                else -> throw RuntimeException()
            }
        }
    }

    public fun readData(reader: BufferedReader): List<T> {
        val values = mutableListOf<T>()

        reader.lines().forEach {

            if (it.trim().isEmpty()) {
                // do nothing
            } else if (it.contains("start")) {
                inObject = true
            } else if (it.contains("end")) {
                values.add(curObj)
                curObj = supplier.invoke()
                inObject = false
            } else {
                val map = it.split(":").map { data -> data.trim() }
                val (field, value) = map
                if (field != "") {
                    lastField = field
                }
                val callable = fieldMembers[lastField]
                if (callable is KMutableProperty<*>) {
                    callable.applyField(value)
                }
            }

        }
        return values
    }

}
