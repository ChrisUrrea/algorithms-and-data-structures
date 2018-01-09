package project3;

import static org.junit.Assert.fail;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class PossibleLocationsQueueTest {

	PossibleLocationsQueue list;
	
	@Test 
	void testConstructors() {
		list = new PossibleLocationsQueue();
		try {
			assertNotNull(list);
			assertEquals(list.roomInLine(), 10);
			list = new PossibleLocationsQueue(20);
			assertNotNull(list);
			assertEquals(list.roomInLine(), 20);
		} catch (IllegalArgumentException e) {
			fail("Unexpected exception caught when creating a new Queue list with a specified capacity");
		} catch (Exception e) {
			fail("Unexpected exception caught when creating a new Queue list");
		}
	}
	
	@Test
	void testIsEmpty() {
		list = new PossibleLocationsQueue();

		try {
			list.add(new Location(1, 1));
			list.add(new Location(2, 1));
			assertFalse(list.isEmpty());
			list.remove();
			list.remove();
			assertTrue(list.isEmpty());
		} catch (NullPointerException e) {
			fail("Unexcepected NullPointerException caught while testing Empty method");
		} catch (Exception e) {
			fail("Unexcepected exception caught while testing Empty method");
		}
	}

	@Test
	void testAddRemove() {
		list = new PossibleLocationsQueue();

		try {
			list.add(new Location(1, 1));
			assertEquals(list.getSize(), 1);
			list.add(new Location(2, 1));
			list.add(new Location(1, 3));
			assertEquals(list.getSize(), 3);
			list.remove();
			assertEquals(list.getSize(), 2);
			list.remove();
			list.remove();
			assertEquals(list.getSize(), 0);
			assertNull(list.remove());
			assertTrue(list.isEmpty());
		} catch (NullPointerException e) {
			fail("Unexpected NullPointer exception caught");
		} catch (Exception e) {
			fail("Unexpected Exception caught while adding and removing elements");
		}

	}

	@Test
	void addWithNull() {
		list = new PossibleLocationsQueue();
		try {
			list.add(null);
		} catch (NullPointerException success) {
			return;
		} catch (Exception e) {
			fail("Unexpected exception caught when using adding null element to the list: \n" + e.toString());
		}
	}

	@Test
	void testExpandSize() {
		list = new PossibleLocationsQueue();
		try {
			for (int i = 0; i < 10; i++) {
				list.add(new Location( (int) (Math.random() * 5), (int) (Math.random() *5)));
			}
		} catch (ArrayIndexOutOfBoundsException s) {
			fail("ArrayIndexOutOfBoundsException caught while testing queue expansion");
		} catch (Exception e) {
			fail("Unexpected exception caught while testing queue expansion");
		}
		try {
			assertEquals(list.getSize(), 10);
			list.add(new Location(3, 3));
			assertEquals(list.getSize(), 11);
			assertEquals(list.roomInLine(), 20);
		} catch (Exception e) {
			fail("Unexpected exception caught while adding elements after queue expansion");
		}
	}
		
}
