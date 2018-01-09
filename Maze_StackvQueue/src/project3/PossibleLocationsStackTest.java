package project3;

import static org.junit.Assert.fail;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.Before;
import org.junit.jupiter.api.Test;

class PossibleLocationsStackTest {
	PossibleLocationsStack list;

	@Test
	void testStackConstructor() {
		try {
		list = new PossibleLocationsStack();
		assertNotNull(list);
		} catch (Exception e) {
			fail("Unexpected error caught when creating a new Stack object");
		}
	}
	
	@Test
	void testAddWithull() {
		list = new PossibleLocationsStack();
		try {
			list.add(null);
		} catch (NullPointerException success) {
			return;
		} catch (Exception e) {
			fail("Unexpected exception caught when using adding null element to the list: \n" + e.toString());
		}
			fail("Didn't throw Null Pointer exception");
	}
	

	@Test
	void testAddRemoveMultiples() {
		list = new PossibleLocationsStack();
		try {
			list.add(new Location(1, 1));
			list.add(new Location(1, 2));
			list.add(new Location(3, 2));
		} catch (Exception e) {
			fail("Unexpected exception caught while trying to add items");
		}
		try {
			list.remove();
			list.remove();
			list.remove();
		} catch (Exception e) {
			fail("Unexpeted exception caught while trying to remove items");
		}
		if (list.getSize() != 0) {
			fail("Expected list size to equal 0 after removing all items");
		}
		assertNull(list.remove());
	}

	
	@Test
	void testisEmpty() {
		try {
		list = new PossibleLocationsStack();
		assertTrue(list.isEmpty());
		list.add(new Location(4,4));
		assertFalse(list.isEmpty());
		list.remove();
		assertTrue(list.isEmpty());
		} catch (Exception e) {
			fail("Unexpected error caught testing isEmpty() Stack method");
		}
	}



}
