/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package scovmod.model.output.modules.community;

import java.time.LocalDate;
import java.util.Objects;

public class DailyResult {

    private final LocalDate date;
    private final CommunityValueType type;
    private final int communityId;
    public DailyResult(LocalDate date, CommunityValueType type, int communityId){
        this.date = date;
        this.type = type;
        this.communityId = communityId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DailyResult that = (DailyResult) o;
        return date == that.date &&
                communityId == that.communityId &&
                type == that.type;
    }

    @Override
    public int hashCode() {
        return Objects.hash(date, type, communityId);
    }

    public LocalDate getDate() {
        return date;
    }

    public CommunityValueType getType() {
        return type;
    }

    public int getCommunityId() {
        return communityId;
    }

}
