/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package scovmod.model.time;

import scovmod.model.input.config.ConfigParameters;
import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import static org.mockito.Mockito.when;

import org.mockito.MockitoAnnotations;

public class TaskTimeManagerTest {

    private static final long FIRST_TIME_STEP = 1000;

    @Mock
    private TimeManager tm;
    @Mock
    private ConfigParameters config;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void isFirstTimeStep(){
        when(tm.getTimeStep()).thenReturn(FIRST_TIME_STEP, FIRST_TIME_STEP+1);
        when(config.getFirstTimeStep()).thenReturn(FIRST_TIME_STEP);

        TaskTimeManager instance = new TaskTimeManager(config, tm);

        assertTrue(instance.isFirstTimeStep());
        assertFalse(instance.isFirstTimeStep());
    }


}
