package org.some.project.reporter.dto

class WorkflowInstance {
    var id: Long = 0
    var workflowId: Int = 0
    var assignee: String = ""
    var step: String = ""
    var status: String = ""
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is WorkflowInstance) return false

        if (id != other.id) return false

        return true
    }

    override fun hashCode(): Int {
        return id.hashCode()
    }

    override fun toString(): String {
        return "WorkflowInstance(id=$id, workflowId=$workflowId, assignee='$assignee', step='$step', status='$status')"
    }


}