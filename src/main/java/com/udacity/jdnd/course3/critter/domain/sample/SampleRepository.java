package com.udacity.jdnd.course3.critter.domain.sample;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;



/**
Flushing and Transactions:
Flushing: The process of synchronizing the state of the persistence context with the underlying database.
Transaction: A set of operations that either succeed or fail as a group.
Level 1 Cache: The Persistence Context functions as a Level 1 Cache, because it does not write changes to the database until Flushing occurs.

Flushing Triggers
Transaction Ends
Query overlaps with queued Entity actions
Native SQL Query executes without registering affected Entities

@Transactional
A good practice is to start one Transaction for each request that interacts with the database. The simplest way to do
this in Spring is through the @Transactional annotation. You can annotate methods to begin a transaction when the
method starts and close it when you leave. You can also annotate classes to treat all their methods as @Transactional.
This annotation is best done at the Service layer, so a new transaction is started whenever the Controller classes
request operations that may involve the database.
 */
@Repository
//Indicates that this interface is a Repository
public interface SampleRepository extends JpaRepository<SampleEntity,Long> {
}
