package edu.vt.cs4604.auto;

import java.util.*;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.repository.query.Param;

@RepositoryRestResource
interface RepairDescriptionRepository extends JpaRepository<RepairDescription, Long> {

    @Query(nativeQuery = true)
    public List<RepairDescription> listRepairDescriptions();
  @Query(nativeQuery = true)
    public RepairDescription createRepairDescription(
        @Param("new_desc") String new_desc,
        @Param("new_hours") int new_hours,
        @Param("new_name") String new_name,
        @Param("new_parts") double new_parts);

  @Query(nativeQuery = true)
    public RepairDescription updateRepairDescription(
        @Param("new_hours") int new_hours,
        @Param("new_name") String new_name,
        @Param("new_parts") double new_parts,
        @Param("update_desc") String update_desc);

    @Query(nativeQuery = true)
    public RepairDescription deleteRepairDescription(
        @Param("delete_desc") String delete_desc);
}
