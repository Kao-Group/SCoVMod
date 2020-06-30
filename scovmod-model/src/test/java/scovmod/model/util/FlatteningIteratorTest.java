/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package scovmod.model.util;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.NoSuchElementException;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import org.junit.Test;
import static scovmod.model.util.TestUtils.*;

public class FlatteningIteratorTest {

	@Test
	public void isIterator() {
		
		@SuppressWarnings("unchecked")
		FlatteningIterator<Integer> instance = new FlatteningIterator<>(
			iteratorOf(iteratorOf(1))
		);
		assertTrue(instance instanceof Iterator);
	}
	
	@Test
	public void next() {
		@SuppressWarnings("unchecked")
		Iterator<Iterator<Integer>> grandIt = iteratorOf(
				iteratorOf(1, 2, 3),
				iteratorOf(4),
				iteratorOf(5,6,7)
		);

		FlatteningIterator<Integer> instance = new FlatteningIterator<>(grandIt);

		assertEquals(1, instance.next().intValue());
		assertEquals(2, instance.next().intValue());
		assertEquals(3, instance.next().intValue());
		assertEquals(4, instance.next().intValue());
		assertEquals(5, instance.next().intValue());
		assertEquals(6, instance.next().intValue());
		assertEquals(7, instance.next().intValue());
	}
	
	@Test
	public void hasNext() {
		@SuppressWarnings("unchecked")
		Iterator<Iterator<Integer>> grandIt = iteratorOf(
				iteratorOf(1, 2, 3),
				iteratorOf(4),
				iteratorOf(5,6,7)
		);

		FlatteningIterator<Integer> instance = new FlatteningIterator<>(grandIt);

		assertEquals(1, instance.next().intValue());
		assertTrue(instance.hasNext());
		assertEquals(2, instance.next().intValue());
		assertTrue(instance.hasNext());
		assertEquals(3, instance.next().intValue());
		assertTrue(instance.hasNext());
		assertEquals(4, instance.next().intValue());
		assertTrue(instance.hasNext());
		assertEquals(5, instance.next().intValue());
		assertTrue(instance.hasNext());
		assertEquals(6, instance.next().intValue());
		assertTrue(instance.hasNext());
		assertEquals(7, instance.next().intValue());
		assertFalse(instance.hasNext());
	}
	
	@Test
	public void skipOverEmptyInneriterators(){
		@SuppressWarnings("unchecked")
		Iterator<Iterator<Integer>> grandIt = iteratorOf(
				iteratorOf(1, 2),
				(new ArrayList<Integer>()).iterator(),
				iteratorOf(3)
		);

		@SuppressWarnings("unchecked")
		FlatteningIterator<Integer> instance = new FlatteningIterator<>(grandIt);
		
		assertEquals(1, instance.next().intValue());
		assertTrue(instance.hasNext());
		assertEquals(2, instance.next().intValue());
		assertTrue(instance.hasNext());
		assertEquals(3, instance.next().intValue());
		assertFalse(instance.hasNext());
	}
	
	@Test
	public void removeNotSupported(){
		@SuppressWarnings("unchecked")
		Iterator<Iterator<Integer>> grandIt = iteratorOf(
		    iteratorOf(1, 2, 3)
		);
		
		FlatteningIterator<Integer> instance = new FlatteningIterator<>(grandIt);
		
		try{
			instance.remove();
			fail("Excpected exception");
		}catch (UnsupportedOperationException e) {}
	}
	
	@Test
	public void exceptionIfAskForNonExistantNext(){
		@SuppressWarnings("unchecked")
		Iterator<Iterator<Integer>> grandIt = iteratorOf(
			iteratorOf(1)
		);
		
		FlatteningIterator<Integer> instance = new FlatteningIterator<>(grandIt);
		instance.next();
		
		try{
			instance.next();
			fail("Excpected exception");
		}catch (NoSuchElementException e) {}
	}
	
	@Test
	public void allowEmptyOuterIterator(){
		Iterator<Iterator<Integer>> emptyIterator = (new ArrayList<Iterator<Integer>>()).iterator();
		
		FlatteningIterator<Integer> instance = new FlatteningIterator<>(emptyIterator);
		
		assertFalse(instance.hasNext());
		try{
			instance.next();
			fail("expected exception");
		}catch(NoSuchElementException e){}
	}
}
