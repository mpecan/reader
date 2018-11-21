package org.some.project.reporter.readers

import com.winterbe.expekt.expect
import org.some.project.reporter.dto.Contractor
import org.spekframework.spek2.Spek
import java.io.BufferedReader
import java.io.StringReader


fun String.withReader(closure: (BufferedReader) -> Unit) = StringReader(this).use { stringReader ->
    BufferedReader(stringReader).use { closure(it) }
}

class GenericDeserializerTest
    : Spek({

    val contractorName = "con24"
    val fullName = "Conny Contractor"
    val email = "conny.contractor@example.com"

    group("valid objects") {
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

    group("invalid objects") {
        val missingField = """start
                                  contractorName : $contractorName
                                  fullName       : $fullName
                                        : $email
                                end"""

        test("should discard objects with missing fields") {
            val subject = GenericDeserializer(::Contractor)

            missingField.withReader {
                val data = subject.readData(it)
                expect(data).has.size.equal(0)
            }
        }


        val typoInFieldName = """start
                                        contractorName : $contractorName
                                        fullName       : $fullName
                                        cookies         : $email
                                    end"""
        test("should discard objects with invalid fields") {
            val subject = GenericDeserializer(::Contractor)

            missingField.withReader {
                val data = subject.readData(it)
                expect(data).has.size.equal(0)
            }
        }
    }
})