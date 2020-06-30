/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package scovmod.model.transition.susceptible;

import scovmod.model.util.math.Random;

public class InfectionPressure {

    private double wildlifePressure;
    private double personPressure;

    public InfectionPressure(
            double wildlifePressure,
            double personPressure) {
        this.wildlifePressure = wildlifePressure;
        this.personPressure = personPressure;
    }

    double getPersonPressure() {
        return personPressure;
    }

    public boolean isNonZeroPressure(){
        return this.personPressure >0 || this.wildlifePressure>0;
    }

    double getWildlifePressure() {
        return wildlifePressure;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        InfectionPressure that = (InfectionPressure) o;

        if (Double.compare(that.wildlifePressure, wildlifePressure) != 0) return false;
        return Double.compare(that.personPressure, personPressure) == 0;
    }

    @Override
    public String toString() {
        return "InfectionPressure{" +
                "wildlifePressure=" + wildlifePressure +
                ", personPressure=" + personPressure +
                '}';
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        temp = Double.doubleToLongBits(wildlifePressure);
        result = (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(personPressure);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        return result;
    }

    public TransmissionEventType evaluate(Random rnd, double timeStepInDays) {

        double probNoTransmission =
                Math.exp(-(wildlifePressure * timeStepInDays)) *
                Math.exp(-(personPressure * timeStepInDays));
        boolean transmissionOccurs = rnd.nextBoolean(1 - probNoTransmission);

        if(transmissionOccurs){
            return TransmissionEventType.FROM_PERSON;
        } else {
            return TransmissionEventType.NULL;
        }
    }

    public InfectionPressure setWildlifePressure(double wildlifePressure) {
        assert(getWildlifePressure() == 0);
        return new InfectionPressure(wildlifePressure, personPressure );
    }

    public InfectionPressure augmentPeoplePressure(double additionalPeoplePressure) {
        return new InfectionPressure(wildlifePressure, personPressure + additionalPeoplePressure);
    }
}
