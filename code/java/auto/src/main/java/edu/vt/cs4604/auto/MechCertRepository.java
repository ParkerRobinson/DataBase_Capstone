package edu.vt.cs4604.auto;

import java.util.*;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.repository.query.Param;

@RepositoryRestResource
interface MechCertRepository extends JpaRepository<MechCert, Long> {


    @Query(nativeQuery = true)
    public List<MechCert> listCertsByMechanic(
      @Param("mechanic") int mechanic);

  @Query(nativeQuery = true)
    public MechCert createMechCert( 
        @Param("new_mechanic_id") int new_mechanic_id,
        @Param("new_name") String new_name);

  @Query(nativeQuery = true)
    public MechCert deleteMechCert(
      @Param("delete_Id") int delete_id,
      @Param("delete_name") String delete_name);
}
