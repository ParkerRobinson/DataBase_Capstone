package edu.vt.cs4604.auto;

import java.util.*;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.repository.query.Param;

@RepositoryRestResource
interface PhoneNumberRepository extends JpaRepository<PhoneNumber, Long> {


    @Query(nativeQuery = true)
    public List<PhoneNumber> listPhoneNumbersByCustomer(@Param("customer") int customer);

  @Query(nativeQuery = true)
    public PhoneNumber createPhoneNumber( 
        @Param("new_number") long new_number,
        @Param("new_customer_id") int new_customer_id);

  @Query(nativeQuery = true)
    public PhoneNumber deletePhoneNumber(@Param("delete_number") long delete_number);
}
