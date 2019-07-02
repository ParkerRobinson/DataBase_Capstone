package edu.vt.cs4604.auto;

import java.util.*;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.repository.query.Param;

@RepositoryRestResource
interface CustomerRepository extends JpaRepository<Customer, Long> {

  @Query(nativeQuery = true)
    public Customer createCustomer( 
        @Param("new_name") String new_name,
        @Param("new_address") String new_address);

  @Query(nativeQuery = true)
    public Customer updateCustomer( 
        @Param("new_name") String new_name,
        @Param("new_address") String new_address, 
        @Param("update_id") int update_id);

    @Query(nativeQuery = true)
    public Customer deleteCustomer(@Param("delete_id") int delete_id);
}
