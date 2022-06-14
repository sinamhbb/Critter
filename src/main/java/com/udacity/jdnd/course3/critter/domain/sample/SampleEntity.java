package com.udacity.jdnd.course3.critter.domain.sample;


import org.hibernate.annotations.Nationalized;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;



/**
Ways to Associate Data
Value Types: Become single columns in containing Entity’s table.
Embeddable: Add their attributes as columns in the containing Entity’s table.
Entities: Become new tables which relate to a containing entity by a Join Column.

So In here we have to either mention Entities or Embeddable

Association Reciprocity:

Unidirectional - Association specified on one side of the relationship only.
You only need to specify the Entity on a single side of the relationship with @OneToMany.
Doesn't retrieve data you won’t use.
Should use Set collection type for most efficient SQL.

Bidirectional - Association specified on both sides of the relationship. Use @OneToMany(mappedBy = "person") on the containing Entity side and @ManyToOne on the other.

Access both sides of relationship with a single query.
Hibernate recommends for @OneToMany, because it allows the foreign key constraint to exist only on the table of the contained object.

Many associations can be stored in a single @JoinColumn on one of the two entity tables, but you may also elect to store the relationship in a table designated for that purpose.
   @ManyToMany
   @JoinTable(
      name = "person_outfit",
      joinColumns = { @JoinColumn(name = "person_id")},
      inverseJoinColumns = { @JoinColumn(name = "outfit_id")}
   )
   private List<Outfit> outfits;

You can use the @ElementCollection annotation to denote an association between a single Entity and a list of values
that are not themselves Entities. This annotation lets you persist Lists of Embeddable or enum, for example.

 @ElementCollection
   private List<Outfit> outfits;

@Embeddable
public class Outfit {
   private String hat;
   private String gloves;
   private String shoes;
   private String legs;
   private String top;
}


Types of Entity Associations:
OneToOne: Single Entity on each side of the relationship.
OneToMany and ManyToOne: List of Entities on one side, single Entity on the other.
ManyToMany: Lists of Entities on both sides.

Inheritance:

@Inheritance(strategy = Strategy)
Strategies = InheritanceType.SINGLE_TABLE, InheritanceType.JOINED, and InheritanceType.TABLE_PER_CLASS

Fetch types:

FetchType.LAZY:
@OneToMany
@ManyToMany

FetchType.EAGER:
@ManyToOne
@OneToOne

Cascade types:
Valid CascadeTypes correspond to the different persistence operations, such as Persist, Merge, and Remove
https://docs.jboss.org/hibernate/orm/5.4/userguide/html_single/Hibernate_User_Guide.html#pc-cascade


 */

@Entity
//Indicates that this class is an entity
//@Table(name = "plant")
// allows you to specify the details of the table that will be used to persist the entity in the database.
@Embeddable
//It allows this class top be embedded into another entity Check the other end on SampleComposite.java

public class SampleEntity implements Serializable {
    // To serialize an object means to convert its state to a byte stream so that the byte stream can be reverted back into a copy of the object.
    @Id
//    All Entities must define an identifier that uniquely identifies them. We express this by using the @Id annotation.
    @GeneratedValue
//     causes this value to be assigned automatically
    private Long id;

    @Nationalized
    // allows different characters of other languages to be used
    private String name;

    @Column(name = "sample_price", precision = 12, scale = 4)
//    Numeric precision refers to the maximum number of digits that are present in the number
//    name allows to mention the column name on the database
    private BigDecimal price;

    @Type(type = "yes_no")
//   check here for more types https://docs.jboss.org/hibernate/stable/core.old/reference/en/html_single/#mapping-types-basictypes
    private Boolean completed;

    public SampleEntity() {
    }

    public SampleEntity(Long id, String name, BigDecimal price, Boolean completed) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.completed = completed;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Boolean getCompleted() {
        return completed;
    }

    public void setCompleted(Boolean completed) {
        this.completed = completed;
    }
}
