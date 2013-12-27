package ru.maxkar.lib.messages

import org.scalatest.junit.JUnitSuite

import org.junit._
import org.junit.Assert._

/** Test for the messages api. */
class TestMessages extends JUnitSuite {
  @Test
  def testProvidedMessages() : Unit = {
    val msg = Messages.fromFormatProvider(
      Map(
        "a" → "Hello, %1$s",
        "a.b" → "Test",
        "a.sum" → "Sum of %2$s and %3$s is %1$s!",
        "b.c.d.e.f.g" → "XxX").apply)

    assertEquals("Test", msg.a.b())
    assertEquals("Hello, Test", msg.a(msg.a.b()))
    assertEquals("XxX", msg.b.c.d.e.f.g())

    val m = msg.a
    assertEquals("Test", m.b())
    assertEquals("Sum of 23 and 100 is 123!", m.sum(123, 23, 100))
  }
}
