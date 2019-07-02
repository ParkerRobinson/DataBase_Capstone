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
 *///my version of all the query tog et all customers. note this won't get any phone numbers.
@NamedNativeQueries({
   @NamedNativeQuery(
    name = "Certification.createCertification", 
    query = "INSERT into Certification (name) " + 
            "VALUES (:new_name) " +
            "RETURNING *", 
    resultClass = Certification.class
  ),
  @NamedNativeQuery(
    name = "Certification.deleteCertification", 
    query = "DELETE FROM Certification WHERE name = :delete_name RETURNING * ",
    resultClass = Certification.class
  )
})


@Entity
@Getter @Setter
@NoArgsConstructor
@ToString @EqualsAndHashCode
public class Certification {
  @Id
  private String name;

  public String getName() {
    return name;
  }
}
