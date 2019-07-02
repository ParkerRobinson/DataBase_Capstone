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
    name = "Customer.createCustomer", 
    query = "INSERT into Customer (name, address) " + 
            "VALUES (:new_name, :new_address) " +
            "RETURNING *", 
    resultClass = Customer.class
  ),
  @NamedNativeQuery(
    name = "Customer.updateCustomer", 
    query = "UPDATE Customer " + 
            "SET name = :new_name, address = :new_address " +
            "WHERE customer_Id = :update_id " +
            "RETURNING *", 
    resultClass = Customer.class
  ),
  @NamedNativeQuery(
    name = "Customer.deleteCustomer", 
    query = "DELETE FROM Customer WHERE customer_Id = :delete_id RETURNING * ",
    resultClass = Customer.class
  )
})


@Entity
@Getter @Setter
@NoArgsConstructor
@ToString @EqualsAndHashCode
public class Customer {
  @Id
  @SequenceGenerator(name="customer_id", sequenceName="customer_id")
  @GeneratedValue(generator="customer_id")
  private int customer_Id;

  private String name;
  private String address;

  public int getCustomerId() {
    return customer_Id;
  }
  public String getName() {
    return name;
  }
  public String getAddress() {
    return address;
  }
}
