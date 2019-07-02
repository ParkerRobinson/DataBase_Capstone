package edu.vt.cs4604.auto;

import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Collection;
import java.util.stream.Collectors;
import java.util.*;

@RestController
class RepairRecordController {
  @Autowired
  private RepairRecordRepository repository;

  public RepairRecordController(RepairRecordRepository repository) {
    this.repository = repository;
  }

  @GetMapping("/repairRecords")
  public Collection<RepairRecord> repairRecords() {
    System.out.println("getting respDesc");
    Collection<RepairRecord> list = repository.listRepairRecords().stream()
      .collect(Collectors.toList());
    for (RepairRecord record : list) {
      record.generateCost();
    }
    return list;
  }
  @PostMapping("/repairRecords")
  public RepairRecord addRepairRecord(
    @RequestBody RepairRecord repairRecord) {
    System.out.println(repairRecord.toString());
    RepairRecord record = repository.createRepairRecord(
      repairRecord.getDate(), 
      repairRecord.getDescription(),
      repairRecord.getMechanicId(),
      repairRecord.getVin());
    record.generateCost();
    return record;
  }
  @PostMapping("/updateRepairRecord")
  public RepairRecord updateRepairRecord(
    @RequestBody RepairRecord repairRecord) {
    System.out.println(repairRecord.toString());
    RepairRecord record = repository.updateRepairRecord(
      repairRecord.getDate(), 
      repairRecord.getDescription(),
      repairRecord.getMechanicId(),
      repairRecord.getVin(),
      repairRecord.getRecordId());
    record.generateCost();
    return record;
  }
  @PostMapping("/deleteRepairRecord")
  public RepairRecord deleteRepairRecord(
    @RequestBody RepairRecord record) {
    return repository.deleteRepairRecord(record.getRecordId());
  }

  @GetMapping("/mechanicHours")
  public int getMechanicHours(@RequestParam("mechanicId") int mechanicId) {
    int hours = 0;
    List<RepairRecord> list = repository.listRepairRecordsByMechanic(mechanicId);
    for (RepairRecord record : list) {
      hours += record.getHoursNeeded();
    }
    return hours;
  }
}
