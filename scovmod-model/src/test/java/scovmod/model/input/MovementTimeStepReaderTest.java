/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package scovmod.model.input;

import java.nio.file.Paths;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import org.junit.Test;


public class MovementTimeStepReaderTest {
	
	@Test
	public void loadStochasticIncrement(){						
		assertEquals(1, MovementTimeStepReader.getStochasticIncrement(Paths.get("src", "test", "resources", "inputData", "movements")));
	}
	
	@Test
	public void wrongJsonStructureFile(){
		try{
			MovementTimeStepReader.getStochasticIncrement(Paths.get("src", "test", "resources", "inputData", "badMovementConfig"));
			fail("Expected exception");
		}catch(RuntimeException e){}
	}
}
