package com.udacity.jdnd.course3.critter.domain.schedule;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ScheduleRepository extends JpaRepository<Schedule,Long> {

    Optional<List<Schedule>> findAllByEmployeesId(Long id);
    Optional<List<Schedule>> findAllByPetsId(Long id);
    Optional<List<Schedule>> findAllByActivitiesId(Long id);
    Optional<List<Schedule>> findAllByCustomersId(Long id);
}
