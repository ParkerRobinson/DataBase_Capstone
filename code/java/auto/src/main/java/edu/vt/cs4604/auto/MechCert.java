package edu.vt.cs4604.auto;

import lombok.*;
import java.time.LocalDate;

import javax.persistence.*;
import java.util.*;
import java.io.Serializable;

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
      name = "MechCert.listCertsByMechanic", 
      query = "SELECT * " + 
              "FROM MechCert " +
              "WHERE mechanic_Id = :mechanic", 
      resultClass = MechCert.class
    ),
   @NamedNativeQuery(
    name = "MechCert.createMechCert", 
    query = "INSERT into MechCert (mechanic_Id, name) " + 
            "VALUES (:new_mechanic_id, :new_name) " +
            "RETURNING *", 
    resultClass = MechCert.class
  ),
  @NamedNativeQuery(
    name = "MechCert.deleteMechCert", 
    query = "DELETE FROM MechCert WHERE (mechanic_Id = :delete_Id AND name = :delete_name) RETURNING * ",
    resultClass = MechCert.class
  )
})


@Entity
@Getter @Setter
@NoArgsConstructor
@ToString @EqualsAndHashCode
@IdClass(MechCert.IdClass.class)
public class MechCert {
  @Id
  private int mechanic_Id;

  @Id
  private String name;

  public int getMechanicId() {
    return mechanic_Id;
  }
  public String getName() {
    return name;
  }

  @Data
  static class IdClass implements Serializable {
    private static final long serialVersionUID = 1L;

    private int mechanic_Id;
    private String name;

    public int getMechanicId() {
      return mechanic_Id;
    }
    public String getName() {
      return name;
    }
    @Override
    public boolean equals(Object o) {
      if (this == o) return true;
      if (o == null || getClass() != o.getClass()) return false;

      MechCert that = (MechCert) o;

      if (!(mechanic_Id == that.getMechanicId())) return false;
      return name.equals(that.getName());
    }

    @Override
    public int hashCode() {
      int result = mechanic_Id * 11;
      result = 31 * result + name.hashCode();
      return result;
    }
  }

}
