package edu.vt.cs4604.auto;

import java.util.*;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.repository.query.Param;

@RepositoryRestResource
interface MechanicRepository extends JpaRepository<Mechanic, Long> {

  @Query(nativeQuery = true)
  public Mechanic createMechanic( 
    @Param("new_name") String new_name,
    @Param("new_years") int new_years,
    @Param("new_rate") double new_rate);

  @Query(nativeQuery = true)
  public Mechanic updateMechanic( 
    @Param("new_name") String new_name,
    @Param("new_years") int new_years,
    @Param("new_rate") double new_rate, 
    @Param("update_id") int update_id);

  @Query(nativeQuery = true)
  public Mechanic updateMechanicHourlyRate( 
    @Param("new_rate") double new_rate, 
    @Param("update_id") int update_id);

  @Query(nativeQuery = true)
  public Mechanic deleteMechanic(@Param("delete_id") int delete_id);

  @Query(nativeQuery = true)
  public List<Mechanic> listMechanicsByCert(@Param("cert_name") String cert_name);

  @Query(nativeQuery = true)
  public List<Mechanic> listMechanicsById(@Param("mechanic") int mechanic);
  @Query(nativeQuery = true)
  public List<Mechanic> listMechanics();
}
