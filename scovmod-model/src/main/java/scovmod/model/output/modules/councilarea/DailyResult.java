/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package scovmod.model.output.modules.councilarea;

import java.time.LocalDate;
import java.util.Objects;

public class DailyResult implements Comparable<DailyResult>{

    private final LocalDate date;
    private final CouncilAreaValueType type;
    private final int councilAreaID;
    public DailyResult(LocalDate date, CouncilAreaValueType type, int councilAreaID){
        this.date = date;
        this.type = type;
        this.councilAreaID = councilAreaID;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DailyResult that = (DailyResult) o;
        return councilAreaID == that.councilAreaID &&
                Objects.equals(date, that.date) &&
                type == that.type;
    }

    @Override
    public int hashCode() {
        return Objects.hash(date, type, councilAreaID);
    }

    @Override
    public String toString() {
        return "DailyResult{" +
                "date=" + date +
                ", type=" + type +
                ", healthBoardID=" + councilAreaID +
                '}';
    }

    public LocalDate getDate() {
        return date;
    }

    public CouncilAreaValueType getType() {
        return type;
    }

    public int getCouncilAreaID() {
        return councilAreaID;
    }

    @Override
    public int compareTo(DailyResult dailyResult) {
        if(this.date.getDayOfYear()==dailyResult.date.getDayOfYear()) return this.getCouncilAreaID() - dailyResult.getCouncilAreaID();
        else return this.date.getDayOfYear() - dailyResult.date.getDayOfYear();
    }


}
