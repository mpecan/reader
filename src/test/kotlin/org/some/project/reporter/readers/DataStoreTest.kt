package org.some.project.reporter.readers

import com.winterbe.expekt.expect
import org.some.project.reporter.DataStore
import org.some.project.reporter.dto.Contractor
import org.some.project.reporter.dto.Employee
import org.some.project.reporter.dto.Workflow
import org.some.project.reporter.dto.WorkflowInstance
import org.spekframework.spek2.Spek

class DataStoreTest : Spek({
    group("accepts objects and stores them") {
        val workflowList = listOf(Workflow().apply { id = 1 }, Workflow().apply { id = 2 })
        test("workflows") {
            val subject = DataStore()
            subject.ingest(workflowList)
            expect(subject.workflows).to.have.size(2)
        }
        val employeeList = listOf(Employee().apply { employeeId = 1 }, Employee().apply { employeeId = 2 })
        test("employees") {
            val subject = DataStore()
            subject.ingest(employeeList)
            expect(subject.employees).to.have.size(2)
        }
        val workflowInstanceList = listOf(WorkflowInstance().apply { id = 1 }, WorkflowInstance().apply { id = 2 })
        test("workflowInstances") {
            val subject = DataStore()
            subject.ingest(workflowInstanceList)
            expect(subject.workflowInstances).to.have.size(2)
        }
        val contractorList = listOf(Contractor().apply { email = "abc" }, Contractor().apply { email = "cba" })
        test("contractors") {
            val subject = DataStore()
            subject.ingest(contractorList)
            expect(subject.contractors).to.have.size(2)
        }
    }

    group("ignores duplicates") {
        val workflowList = listOf(Workflow().apply { id = 1 }, Workflow().apply { id = 2 }, Workflow().apply { id = 2 })
        test("workflows") {
            val subject = DataStore()
            subject.ingest(workflowList)
            expect(subject.workflows).to.have.size(2)
        }
        val employeeList = listOf(Employee().apply { employeeId = 1 }, Employee().apply { employeeId = 2 }, Employee().apply { employeeId = 2 })
        test("employees") {
            val subject = DataStore()
            subject.ingest(employeeList)
            expect(subject.employees).to.have.size(2)
        }
        val workflowInstanceList = listOf(WorkflowInstance().apply { id = 1 }, WorkflowInstance().apply { id = 2 }, WorkflowInstance().apply { id = 2 })
        test("workflowInstances") {
            val subject = DataStore()
            subject.ingest(workflowInstanceList)
            expect(subject.workflowInstances).to.have.size(2)
        }
        val contractorList = listOf(Contractor().apply { email = "abc" }, Contractor().apply { email = "cba" }, Contractor().apply { contractorName = "cba" })
        test("contractors") {
            val subject = DataStore()
            subject.ingest(contractorList)
            expect(subject.contractors).to.have.size(2)
        }
    }
})