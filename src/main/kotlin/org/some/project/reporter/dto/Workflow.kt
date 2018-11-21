package org.some.project.reporter.dto

class Workflow {
    var id: Int = -1
    var name: String = ""
    var author: String = ""
    var version: Int = -1
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Workflow) return false

        if (id != other.id) return false

        return true
    }

    override fun hashCode(): Int {
        return id
    }

}