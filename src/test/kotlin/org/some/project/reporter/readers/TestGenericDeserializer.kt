package org.some.project.reporter.readers

import com.winterbe.expekt.expect
import org.some.project.reporter.dto.Contractor
import org.spekframework.spek2.Spek
import java.io.BufferedReader
import java.io.StringReader


fun String.withReader(closure: (BufferedReader) -> Unit) = StringReader(this).use { stringReader ->
    BufferedReader(stringReader).use { closure(it) }
}

class TestGenericDeserializer
    : Spek({

    group("contractor") {
        val contractorName = "con24"
        val fullName = "Conny Contractor"
        val email = "conny.contractor@example.com"
        val testData = """start
                              contractorName : $contractorName
                              fullName       : $fullName
                              email          : $email
                            end"""

        test("test deserialization") {
            val subject = GenericDeserializer(::Contractor)

            testData.withReader {
                val data = subject.readData(it)
                expect(data).has.size.equal(1)
                expect(data[0].contractorName).to.equal(contractorName)
                expect(data[0].fullName).to.equal(fullName)
                expect(data[0].email).to.equal(email)
            }
        }
    }


})