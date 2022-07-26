package com.udacity.jdnd.course3.critter.controller.schedule;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Represents the form that schedule request and response data takes. Does not map
 * to the database directly.
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ScheduleDTO {
    private long id;
    private Set<Long> employeeIds = new HashSet<>();
    private Set<Long> petIds = new HashSet<>();
    private LocalDate date;
    private LocalTime startTime;
    private LocalTime endTime;
    private Set<Long> activityIds = new HashSet<>();
    private Set<Long> customersIds = new HashSet<>();
}
