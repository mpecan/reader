package org.some.project.reporter

import org.some.project.reporter.dto.Contractor
import org.some.project.reporter.dto.Employee
import org.some.project.reporter.dto.Workflow
import org.some.project.reporter.dto.WorkflowInstance

class DataStore {
    val employees = hashMapOf<Int, Employee>()
    val contractors = hashMapOf<String, Contractor>()
    val workflows = hashMapOf<Int, Workflow>()
    val workflowInstances = hashMapOf<Long, WorkflowInstance>()

    fun ingest(data: List<Any>) {
        data.forEach {
            when (it) {
                is Employee -> employees[it.employeeId] = it
                is Contractor -> contractors[it.email] = it
                is Workflow -> workflows[it.id] = it
                is WorkflowInstance -> workflowInstances[it.id] = it
            }
        }
    }

    public fun printWorkflowsWithInstances() {
        println("Workflows with instances:")
        workflows.forEach { (_, workflow) ->
            println("\t$workflow")
            workflowInstances.filter { it.value.workflowId == workflow.id }
                    .forEach { _, instance ->
                        println("\t\t$instance")
                    }
        }
    }

    public fun printWorkflowsWithRunningAndNumber() {
        println("Workflows with running instances:")
        runningWorkflowsWithNumber()
                .forEach { (workflow, number) ->
                    println("\t$workflow Running instances: $number")
                }
    }

    private fun runningWorkflowsWithNumber(): List<Pair<Workflow?, Int>> {
        return workflowInstances.values.groupBy { it.workflowId }
                .map { (workflowId, list) -> workflowId to list.size }
                .filter { (_, number) -> number > 0 }
                .filter { (workflowId, _) -> workflows[workflowId] != null }
                .map { (workflowId, number) -> workflows[workflowId] to number }
    }

    public fun printAllContractorsWithRunningWorkflows() {
        println("Contractors with running instances:")
        workflowInstances.values
                .filter { it.status == "RUNNING" }
                .map { it.assignee }
                .toSet()
                .filter { contractors.containsKey(it) }
                .forEach { println("\t${contractors[it]}") }
    }


}