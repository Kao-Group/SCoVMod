/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package scovmod.model.output.modules.healthboard;

import java.time.LocalDate;
import java.util.Objects;

public class DailyResult {

    private final LocalDate date;
    private final HealthBoardValueType type;
    private final int healthBoardID;
    public DailyResult(LocalDate date, HealthBoardValueType type, int healthBoardID){
        this.date = date;
        this.type = type;
        this.healthBoardID = healthBoardID;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DailyResult that = (DailyResult) o;
        return healthBoardID == that.healthBoardID &&
                Objects.equals(date, that.date) &&
                type == that.type;
    }

    @Override
    public int hashCode() {
        return Objects.hash(date, type, healthBoardID);
    }

    @Override
    public String toString() {
        return "DailyResult{" +
                "date=" + date +
                ", type=" + type +
                ", healthBoardID=" + healthBoardID +
                '}';
    }

    public LocalDate getDate() {
        return date;
    }

    public HealthBoardValueType getType() {
        return type;
    }

    public int getHealthBoardID() {
        return healthBoardID;
    }

}
