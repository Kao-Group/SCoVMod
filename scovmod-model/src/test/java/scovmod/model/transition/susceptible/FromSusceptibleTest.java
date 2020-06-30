/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package scovmod.model.transition.susceptible;

import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import org.mockito.*;
import scovmod.model.movements.LocationIncomingPersons;
import java.util.Set;
import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.Test;

import static org.mockito.Mockito.*;
import static org.mockito.ArgumentMatchers.any;

import scovmod.model.transition.TransitionExecutor;


public class FromSusceptibleTest {

    @Mock
    LocalPersonPressure lap;
    @Mock
    VisitorPressure vp;
    @Mock
    TransitionExecutor te;
    @Captor
    ArgumentCaptor<Int2ObjectMap<InfectionPressure>> pressureMap;
    @Mock
    Int2ObjectMap<InfectionPressure> locationPressure;
    @Mock
    Set<LocationIncomingPersons> setIncomingPersons;

    FromSusceptible instance;

    @Before
    public void setup(){
        MockitoAnnotations.initMocks(this);
        instance = new FromSusceptible(lap, vp, te);
    }


    @Test
    public void addPressureThroughLocalVisitorAndEnvironment() {
	    Set<LocationIncomingPersons> setOfIncomingPersons = setIncomingPersons;

        instance.doTransitions(setOfIncomingPersons);

        InOrder inOrder = inOrder(vp, lap,te);

        inOrder.verify(vp).addVisitorPresssure(same(setOfIncomingPersons), pressureMap.capture());
        Int2ObjectMap<InfectionPressure> locationPressure = pressureMap.getValue();
        assertEquals(0, locationPressure.size());

        inOrder.verify(lap).addLocalPressure(same(locationPressure));
        inOrder.verify(te).doSusceptibleTransitions(same(locationPressure));
     }

}
