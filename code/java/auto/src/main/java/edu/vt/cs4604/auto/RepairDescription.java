package edu.vt.cs4604.auto;

import lombok.*;
import java.time.LocalDate;

import javax.persistence.*;
import java.util.*;

/*
 * NamedNativeQueries allow us to use _SQL_ queries
 * to perform complex queries against the database.
 * Alternatively NamedQueries use _JPQL_ to accomplish
 * the same thing.
 * https://en.wikipedia.org/wiki/Java_Persistence_Query_Language
 */
 //my version of all the query tog et all customers. note this won't get any phone numbers.
@NamedNativeQueries({
  @NamedNativeQuery(
    name = "RepairDescription.listRepairDescriptions", 
    query = "SELECT * FROM RepairDescription", 
    resultClass = RepairDescription.class
  ),
   @NamedNativeQuery(
    name = "RepairDescription.createRepairDescription", 
    query = "INSERT into RepairDescription (description, hours_needed, name, parts_cost) " + 
            "VALUES (:new_desc, :new_hours, :new_name, :new_parts) " +
            "RETURNING *", 
    resultClass = RepairDescription.class
  ),
  @NamedNativeQuery(
    name = "RepairDescription.updateRepairDescription", 
    query = "UPDATE RepairDescription " + 
            "SET hours_needed = :new_hours, name = :new_name, parts_cost = :new_parts " +
            "WHERE description = :update_desc " +
            "RETURNING *", 
    resultClass = RepairDescription.class
  ),
  @NamedNativeQuery(
    name = "RepairDescription.deleteRepairDescription", 
    query = "DELETE FROM RepairDescription WHERE description = :delete_desc RETURNING * ",
    resultClass = RepairDescription.class
  )
})


@Entity
@Getter @Setter
@NoArgsConstructor
@ToString @EqualsAndHashCode
public class RepairDescription {
  @Id
  private String description;

  private int hours_needed;
  private String name;
  private double parts_cost;

  public String getDescription() {
    return description;
  }
  public int getHoursNeeded() {
    return hours_needed;
  }
  public String getName() {
    return name;
  }
  public double getPartsCost() {
    return parts_cost;
  }
}
