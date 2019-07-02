package edu.vt.cs4604.auto;

import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Collection;
import java.util.stream.Collectors;

@RestController
class MechCertController {
  @Autowired
  private MechCertRepository repository;

  public MechCertController(MechCertRepository repository) {
    this.repository = repository;
  }

  @GetMapping("/mechcerts")
  public Collection<MechCert> mechCerts(
    @RequestParam("mechanicId") int mechanic_Id) {
    System.out.println("getting cert" + mechanic_Id);
    return repository.listCertsByMechanic(mechanic_Id).stream()
      .collect(Collectors.toList());
  }
  @PostMapping("/mechcerts")
  public MechCert addMechCert(@RequestBody MechCert mechCert) {
    System.out.println(mechCert.toString());
    return repository.createMechCert(mechCert.getMechanicId(), 
      mechCert.getName());
  }
  @PostMapping("/deleteMechCert")
  public MechCert deleteMechCert(@RequestBody MechCert mechCert) {
    return repository.deleteMechCert(mechCert.getMechanicId(), 
      mechCert.getName());
  }
}
