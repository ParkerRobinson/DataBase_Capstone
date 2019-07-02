package edu.vt.cs4604.auto;

import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Collection;
import java.util.stream.Collectors;
import java.util.*;

@RestController
class RepairDescriptionController {
  @Autowired
  private RepairDescriptionRepository repository;

  public RepairDescriptionController(RepairDescriptionRepository repository) {
    this.repository = repository;
  }

  @GetMapping("/repairDescriptions")
  public Collection<RepairDescription> repairDecriptions() {
    System.out.println("getting respDesc");
    return repository.listRepairDescriptions().stream()
      .collect(Collectors.toList());
  }
  @PostMapping("/repairDescriptions")
  public RepairDescription addRepairDescription(
    @RequestBody RepairDescription repairDescription) {
    String name = repairDescription.getName().equals("") ? null : repairDescription.getName();
    System.out.println(repairDescription.toString());
    return repository.createRepairDescription(
      repairDescription.getDescription(), 
      repairDescription.getHoursNeeded(),
      name,
      repairDescription.getPartsCost());
  }
  @PostMapping("/updateRepairDescription")
  public RepairDescription updateRepairDescription(
    @RequestBody RepairDescription repairDescription) {
    System.out.println(repairDescription.toString());
    String name = repairDescription.getName().equals("") ? null : repairDescription.getName();
    return repository.updateRepairDescription(
      repairDescription.getHoursNeeded(), 
      name,
      repairDescription.getPartsCost(),
      repairDescription.getDescription());
  }
  @PostMapping("/deleteRepairDescription")
  public RepairDescription deleteRepairDescription(
    @RequestBody String description) {
    System.out.println("Description: " + description);
    return repository.deleteRepairDescription(description);
  }
  @GetMapping("/descNames")
  public Collection<String> listDescNames() {
    Collection<RepairDescription> descList = repository.findAll().stream()
      .collect(Collectors.toList());
    ArrayList<String> nameList = new ArrayList<String>();
    for (RepairDescription desc : descList) {
      nameList.add(desc.getDescription());
    }
    return nameList;
  }
}
