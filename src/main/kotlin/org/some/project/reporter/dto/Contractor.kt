package org.some.project.reporter.dto

class Contractor {
    var contractorName: String = ""
    var fullName: String = ""
    var email: String = ""

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Contractor) return false

        if (contractorName != other.contractorName) return false

        return true
    }

    override fun hashCode(): Int {
        return contractorName.hashCode()
    }

    override fun toString(): String {
        return "Contractor(contractorName='$contractorName', fullName='$fullName', email='$email')"
    }


}