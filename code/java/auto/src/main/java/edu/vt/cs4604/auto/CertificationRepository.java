package edu.vt.cs4604.auto;

import java.util.*;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.repository.query.Param;

@RepositoryRestResource
interface CertificationRepository extends JpaRepository<Certification, Long> {

  @Query(nativeQuery = true)
    public Certification createCertification( 
        @Param("new_name") String new_name);
  @Query(nativeQuery = true)
    public Certification deleteCertification(@Param("delete_name") String cert_name);
}
