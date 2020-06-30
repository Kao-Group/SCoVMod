package scovmod.model.util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.concurrent.ExecutorService;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import scovmod.test.SerialExecutorService;

public class BufferedIteratorTest {
	final int CHUNK_SIZE = 2;
	ExecutorService executor = new SerialExecutorService();

	@Mock
	private Iterator<Integer> innerIt;
	
	@Before
	public void setup(){
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void chunkingItemsFromInnerIterator() {
		when(innerIt.next()).thenReturn(1,2,3,4,5,6);
		when(innerIt.hasNext()).thenReturn(true, true, true, true, true, true, false);
		
		BufferedIterator<Integer> instance = new BufferedIterator<Integer>(innerIt, CHUNK_SIZE, executor);
		
		List<Integer> result = new ArrayList<Integer>();
		result.add(instance.next());
		result.add(instance.next());	//End of first buffer
		result.add(instance.next());
		result.add(instance.next());	//End of second buffer
		result.add(instance.next());	//Start of third buffer
		
//		System.out.println("Result: "+result);
		
		List<Integer> expected = new ArrayList<Integer>();
		expected.add(1);	
		expected.add(2);	
		expected.add(3);
		expected.add(4);	//End of second buffer
		expected.add(5);
		
		assertEquals(expected, result);
		
		verify(innerIt, times(7)).hasNext();
		verify(innerIt, times(6)).next();
		verifyNoMoreInteractions(innerIt);
	}
	
	@Test
	public void noMoreItemsAtEndOfBuffer(){
		Iterator<String> innerIt = Collections.nCopies(4, "item").iterator();
		
		BufferedIterator<String> instance = new BufferedIterator<>(innerIt, CHUNK_SIZE, executor);
		instance.next();	// 1
		instance.next(); 
		instance.next(); 
		instance.next(); 	// 4
		
		assertFalse(instance.hasNext());
		try{
			instance.next();
			fail("Expected exception");
		}catch(NoSuchElementException e){}
	}
	
	@Test
	public void noMoreItemsMidBuffer(){
		Iterator<String> innerIt = Collections.nCopies(5, "item").iterator();
		
		BufferedIterator<String> instance = new BufferedIterator<>(innerIt, CHUNK_SIZE, executor);
		instance.next(); 	// 1
		instance.next(); 
		instance.next();
		instance.next(); 
		instance.next(); 	// 5
		
		assertFalse(instance.hasNext());
		try{
			instance.next();
			fail("Expected exception");
		}catch(NoSuchElementException e){}
	}
	
	@Test
	public void removeNotSupported(){
		BufferedIterator<Integer> instance = new BufferedIterator<>(innerIt, CHUNK_SIZE, executor);
		try{
			instance.remove();
			fail("Excpected exception");
		}catch (UnsupportedOperationException e) {}
	}
}
