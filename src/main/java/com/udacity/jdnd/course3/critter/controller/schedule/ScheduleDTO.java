package com.udacity.jdnd.course3.critter.controller.schedule;

import java.time.LocalDate;
import java.util.List;

/**
 * Represents the form that schedule request and response data takes. Does not map
 * to the database directly.
 */
public class ScheduleDTO {
    private long id;
//    private List<Long> employeeIds;
    private List<Long> petIds;
    private LocalDate date;
    private List<Long> activityIds;

    public long getId(){
        return id;
    }
    
    public void setId(long id){
        this.id = id;
    }
    
//    public List<Long> getEmployeeIds() {
//        return employeeIds;
//    }
//
//    public void setEmployeeIds(List<Long> employeeIds) {
//        this.employeeIds = employeeIds;
//    }

    public List<Long> getPetIds() {
        return petIds;
    }

    public void setPetIds(List<Long> petIds) {
        this.petIds = petIds;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public List<Long> getActivityIds() {
        return activityIds;
    }

    public void setActivityIds(List<Long> activityIds) {
        this.activityIds = activityIds;
    }
}
