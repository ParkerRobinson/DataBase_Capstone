package edu.vt.cs4604.auto;

import java.util.*;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.repository.query.Param;

@RepositoryRestResource
interface RepairRecordRepository extends JpaRepository<RepairRecord, Long> {

    @Query(nativeQuery = true)
    public List<RepairRecord> listRepairRecords();
  @Query(nativeQuery = true)
    public RepairRecord createRepairRecord(
        @Param("new_date") Date new_date,
        @Param("new_desc") String new_desc,
        @Param("new_mechanic") int new_mechanic,
        @Param("new_vin") String new_vin);

  @Query(nativeQuery = true)
    public RepairRecord updateRepairRecord(
        @Param("new_date") Date new_date,
        @Param("new_desc") String new_desc,
        @Param("new_mechanic") int new_mechanic,
        @Param("new_vin") String new_vin,
        @Param("update_record") int update_record);

    @Query(nativeQuery = true)
    public RepairRecord deleteRepairRecord(
        @Param("delete_record") int delete_record);

    @Query(nativeQuery = true)
    public List<RepairRecord> listRepairRecordsByMechanic(@Param("mechanic") int mechanic);
}