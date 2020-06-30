/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package scovmod.model.time;


public class TimeManager {

	private long step;
	
	public TimeManager(long startEpochTimeStep) {
		this.step = startEpochTimeStep;
	}

	public long getTimeStep() {
		return step;
	}

	public void advanceOneStep() {
		 step++;
	}

}
