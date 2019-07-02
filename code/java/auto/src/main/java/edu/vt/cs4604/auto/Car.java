//================== THE NAMEDNARATIVEQUERY NEEDS TO BE CHANGED

package edu.vt.cs4604.auto;

import lombok.*;
import java.time.LocalDate;
import javax.persistence.*;

/*
 * NamedNativeQueries allow us to use _SQL_ queries
 * to perform complex queries against the database.
 * Alternatively NamedQueries use _JPQL_ to accomplish
 * the same thing.
 * https://en.wikipedia.org/wiki/Java_Persistence_Query_Language
 */
@NamedNativeQueries({
  @NamedNativeQuery(
    name = "Car.createCar", 
    query = "INSERT into Car (vin, make, model, year, customer_Id) " + 
            "VALUES (:new_vin, :new_make, :new_model, :new_year, :new_customer_Id) " +
            "RETURNING *", 
    resultClass = Car.class
  ),
  @NamedNativeQuery(
    name = "Car.updateCar", 
    query = "UPDATE Car " + 
            "SET make = :new_make, model = :new_model, year = :new_year, customer_Id = :new_customer_Id " +
            "WHERE vin = :update_vin " +
            "RETURNING *", 
    resultClass = Car.class
  ),
  @NamedNativeQuery(
    name = "Car.deleteCar", 
    query = "DELETE FROM Car WHERE vin = :delete_vin RETURNING *",
    resultClass = Car.class
  )
})
@Entity
@Getter @Setter
@NoArgsConstructor
@ToString @EqualsAndHashCode
public class Car {
  @Id
  private String vin;

  
  private int year;
  private String make;
  private String model;

  /*@Column(name="customer_Id")
  private int customer_Id;
  private String name;*/
  @ManyToOne()
  @JoinColumn(name="customer_Id")
  private Customer customer;


  public String getVin() {
    return vin;
  }

  public int getYear() {
    return year;
  }

  public String getMake() {
    return make;
  }

  public String getModel() {
    return model;
  }

  public int getCustomerId() {
    return customer.getCustomerId();
  }

  public Customer getCustomer() {
    return customer;
  }

  /*public int getCustomerId() {
    return customer_Id;
  }*/
}
