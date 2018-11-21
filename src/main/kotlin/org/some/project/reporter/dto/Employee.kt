package org.some.project.reporter.dto;

class Employee {
    var employeeId: Int = -1
    var fullName: String = ""
    var email: String = ""
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Employee) return false

        if (employeeId != other.employeeId) return false

        return true
    }

    override fun hashCode(): Int {
        return employeeId
    }

    override fun toString(): String {
        return "Employee(employeeId=$employeeId, fullName='$fullName', email='$email')"
    }


}
