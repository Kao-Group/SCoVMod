package scovmod.model.util.math;

import static junit.framework.TestCase.fail;
import static org.hamcrest.CoreMatchers.hasItems;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;

public class EnumeratorTest {

	private final Integer[] expected = new Integer[]{1,2,3,4,5};
	private final Set<Integer> setOfItems = new HashSet<>();
	private Enumerator instance;
	
	@Before
	public void setup(){
		setOfItems.add(1);
		setOfItems.add(2);
		setOfItems.add(3);
		setOfItems.add(4);
		setOfItems.add(5);

		instance = new Enumerator();
	}

	@Test
	public void toLinkedList(){
		LinkedList<Integer> result = instance.toLinkedList(setOfItems);
		
		assertEquals(setOfItems.size(), result.size());
		assertThat(result, hasItems(expected));
	}
	
	@Test
	public void toArrayList(){
		ArrayList<Integer> result = instance.toArrayList(setOfItems);
		
		assertEquals(setOfItems.size(), result.size());
		assertThat(result, hasItems(expected));
	}
}
