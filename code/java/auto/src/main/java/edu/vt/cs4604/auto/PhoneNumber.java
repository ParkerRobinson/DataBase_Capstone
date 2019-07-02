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
      name = "PhoneNumber.listPhoneNumbersByCustomer", 
      query = "SELECT * " + 
              "FROM PhoneNumber " +
              "WHERE customer_Id = :customer", 
      resultClass = PhoneNumber.class
    ),
   @NamedNativeQuery(
    name = "PhoneNumber.createPhoneNumber", 
    query = "INSERT into PhoneNumber (phone_Number, customer_Id) " + 
            "VALUES (:new_number, :new_customer_id) " +
            "RETURNING *", 
    resultClass = PhoneNumber.class
  ),
  @NamedNativeQuery(
    name = "PhoneNumber.deletePhoneNumber", 
    query = "DELETE FROM PhoneNumber WHERE phone_Number = :delete_number RETURNING * ",
    resultClass = PhoneNumber.class
  )
})


@Entity
@Getter @Setter
@NoArgsConstructor
@ToString @EqualsAndHashCode
public class PhoneNumber {
  @Id
  private long phone_Number;

  private int customer_Id;

  public long getPhoneNumber() {
    return phone_Number;
  }
  public int getCustomerId() {
    return customer_Id;
  }
}
