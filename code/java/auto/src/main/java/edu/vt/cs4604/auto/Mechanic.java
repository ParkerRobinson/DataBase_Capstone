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
 
 //=======their query to get all active scouts.
//@NamedNativeQueries({
  //@NamedNativeQuery(
    //name = "Customer.activeScouts", 
    //query = "select s.* from Scout s, Attends a, ScoutTrip st " +
      //      "where s.id = a.scout_id and st.id = a.scouttrip_id " +
        //    "and st.tripdate > now() - ((interval '1 days') * to_number(:days, '99999'))", 
    //resultClass = Customer.class
  //)
//})

//my version of all the query tog et all customers. note this won't get any phone numbers.
@NamedNativeQueries({
  @NamedNativeQuery(
    name = "Mechanic.listMechanics", 
    query = "SELECT * FROM Mechanic ORDER BY mechanic_Id ASC", 
    resultClass = Mechanic.class
  ),
   @NamedNativeQuery(
    name = "Mechanic.createMechanic", 
    query = "INSERT into Mechanic (name, years_experience, hourly_rate) " + 
            "VALUES (:new_name, :new_years, :new_rate) " +
            "RETURNING *", 
    resultClass = Mechanic.class
  ),
  @NamedNativeQuery(
    name = "Mechanic.updateMechanic", 
    query = "UPDATE Mechanic " + 
            "SET name = :new_name, years_experience = :new_years, hourly_rate = :new_rate " +
            "WHERE mechanic_Id = :update_id " +
            "RETURNING *", 
    resultClass = Mechanic.class
  ),
  @NamedNativeQuery(
    name = "Mechanic.deleteMechanic", 
    query = "DELETE FROM Mechanic WHERE (mechanic_Id = :delete_id) RETURNING *",
    resultClass = Mechanic.class
  ),
  @NamedNativeQuery(
    name = "Mechanic.listMechanicsByCert", 
    query = "SELECT * " +
      "FROM Mechanic m " +
      "WHERE m.mechanic_Id in " +
      "(SELECT mechanic_Id FROM MechCert mc WHERE (mc.name = :cert_name OR :cert_name = ''))",
    resultClass = Mechanic.class
  ),
  @NamedNativeQuery(
    name = "Mechanic.listMechanicsById", 
    query = "SELECT * " +
      "FROM Mechanic m " +
      "WHERE m.mechanic_Id = :mechanic",
    resultClass = Mechanic.class
  ),
  @NamedNativeQuery(
    name = "Mechanic.updateMechanicHourlyRate", 
    query = "UPDATE Mechanic " + 
            "SET hourly_rate = :new_rate " +
            "WHERE mechanic_Id = :update_id " +
            "RETURNING *", 
    resultClass = Mechanic.class
  )
})


@Entity
@Getter @Setter
@NoArgsConstructor
@ToString @EqualsAndHashCode
public class Mechanic {
  @Id
  @SequenceGenerator(name="mechanic_id", sequenceName="mechanic_id")
  @GeneratedValue(generator="mechanic_id")
  private int mechanic_Id;

  private String name;
  private int years_experience;
  private double hourly_rate;

  @OneToMany(cascade=CascadeType.ALL)
  @JoinColumn(name = "mechanic_Id")
  private Set<MechCert> mechCerts;

  @Transient
  private int certCount;

  public int getMechanicId() {
    return mechanic_Id;
  }
  public String getName() {
    return name;
  }
  public int getYearsExperience() {
    return years_experience;
  }
  public double getHourlyRate() {
    return hourly_rate;
  }
  public void calcCertCount() {
    certCount = mechCerts.size();
  }
  public int getCertCount() {
    return certCount;
  }
}

