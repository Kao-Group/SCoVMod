/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package scovmod.model.time;

import static org.junit.Assert.*;
import org.junit.Test;

public class TimeManagerTest {

	@Test
	public void constructorArgs() {
		assertEquals(1043, new TimeManager(1043).getTimeStep());
		assertEquals(1044, new TimeManager(1044).getTimeStep());
	}
	
	@Test
	public void advanceTo(){
		TimeManager instance = new TimeManager(1043);
		instance.advanceOneStep();
		instance.advanceOneStep();
		
		assertEquals(1045, instance.getTimeStep());
	}
}
