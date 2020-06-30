/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package scovmod.test;

import scovmod.model.state.infection.InfectionState;
import scovmod.model.state.population.LocalPopulation;

/**
 *
 * @author thomd_000
 */
public class FakeLocalPopulation extends LocalPopulation {
    public void testSet(int personId, InfectionState state) { super.setStateByPersonId(personId, state); }
}
