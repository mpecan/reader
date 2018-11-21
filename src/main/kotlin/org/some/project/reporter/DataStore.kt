package org.some.project.reporter

import org.some.project.reporter.dto.Contractor
import org.some.project.reporter.dto.Employee
import org.some.project.reporter.dto.Workflow
import org.some.project.reporter.dto.WorkflowInstance

class DataStore {
    val employees = mutableSetOf<Employee>()
    val contractors = mutableSetOf<Contractor>()
    val workflows = mutableSetOf<Workflow>()
    val workflowInstances = mutableSetOf<WorkflowInstance>()

    fun ingest(data: List<Any>) {
        data.forEach {
            when (it) {
                is Employee -> employees.add(it)
                is Contractor -> contractors.add(it)
                is Workflow -> workflows.add(it)
                is WorkflowInstance -> workflowInstances.add(it)
            }
        }
    }

}