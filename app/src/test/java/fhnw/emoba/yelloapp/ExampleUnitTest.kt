package fhnw.emoba.yelloapp

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 *
 * ACHTUNG: bei den lokalen JUnit-Tests arbeiten wir mit JUNIT 5
 *
 */
class ExampleUnitTest {

    @Test
    fun testJunitSetup(){
        //given
        val s1 = 1
        val s2 = 2

        //when
        val sum = s1 + s2

        //then
        assertEquals(3, sum)
    }
}