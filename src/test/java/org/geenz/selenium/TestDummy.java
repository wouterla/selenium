package org.geenz.selenium;

import org.junit.*;
import static org.junit.Assert.*;

public class TestDummy {

	@Test
	public void testOne() {
		DummyClass dummy = new DummyClass();
		assertEquals(1, dummy.one());
	}
}
