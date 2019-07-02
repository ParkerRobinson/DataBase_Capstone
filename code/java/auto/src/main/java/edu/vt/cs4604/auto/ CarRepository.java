package edu.vt.cs4604.auto;

import java.util.*;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.repository.query.Param;

@RepositoryRestResource
interface CarRepository extends JpaRepository<Car, Long> 
{

    /*@Query(nativeQuery = true)
    public List<Car> cars();*/

    @Query(nativeQuery = true)
    public Car createCar(@Param("new_vin") String new_vin, 
        @Param("new_make") String new_make,
        @Param("new_model") String new_model,
        @Param("new_year") int new_year,
        @Param("new_customer_Id") int new_customer_Id);

    @Query(nativeQuery = true)
    public Car updateCar( 
        @Param("new_make") String new_make,
        @Param("new_model") String new_model,
        @Param("new_year") int new_year,
        @Param("new_customer_Id") int new_customer_Id,
        @Param("update_vin") String update_vin);

    @Query(nativeQuery = true)
    public Car deleteCar(@Param("delete_vin") String delete_vin);

    /*@Modifying
    @Query("delete from Car where vin = ?1")
    int deleteCar(String vin);
    
    @Modifying
    @Query("update Car c set c.year = ?1, c.make = ?2, c.model = ?3 where c.vin = ?4")
    int updateCar(int year, String make, String model, String vin);*/
}
