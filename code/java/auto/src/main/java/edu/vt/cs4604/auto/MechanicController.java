package edu.vt.cs4604.auto;

import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Collection;
import java.util.stream.Collectors;

@RestController
class MechanicController {
  @Autowired
  private MechanicRepository repository;

  public MechanicController(MechanicRepository repository) {
    this.repository = repository;
  }

  @GetMapping("/mechanics")
  public Collection<Mechanic> mechanics() {
    System.out.println("getting mechanics");
    Collection<Mechanic> list = repository.listMechanics().stream()
      .collect(Collectors.toList());
    for (Mechanic mech : list) {
      mech.calcCertCount();
    }
    return list;
  }
  @PostMapping("/mechanics")
  public Mechanic addMechanic(@RequestBody Mechanic mechanic) {
    System.out.println(mechanic.toString());
    double hourlyRate = 13.0 + (mechanic.getYearsExperience() * 0.5);
    Mechanic mech = repository.createMechanic(mechanic.getName(), 
        mechanic.getYearsExperience(), hourlyRate);
    mech.calcCertCount();
    return mech;
  }
  @PostMapping("/updateMechanic")
  public Mechanic updateMechanic(@RequestBody Mechanic mechanic) {
    System.out.println(mechanic.toString());
    double hourlyRate = 13.0 + (mechanic.getYearsExperience() * 0.5) + mechanic.getCertCount();
    Mechanic mech =  repository.updateMechanic(mechanic.getName(), 
        mechanic.getYearsExperience(), hourlyRate,
        mechanic.getMechanicId());
    mech.calcCertCount();
    return mech;
  }
  @PostMapping("/updateMechanicHourlyRate")
  public Mechanic updateMechanicHourlyRate(@RequestBody Mechanic mechanic) {
    System.out.println(mechanic.toString());
    double hourlyRate = 13.0 + (mechanic.getYearsExperience() * 0.5) + mechanic.getCertCount();
    Mechanic mech =  repository.updateMechanicHourlyRate(
        hourlyRate,
        mechanic.getMechanicId());
    mech.calcCertCount();
    return mech;
  }
  @PostMapping("/deleteMechanic")
  public Mechanic deleteMechanic(@RequestBody Mechanic mechanic) {
    System.out.println("Mechanic ID: " + mechanic.getMechanicId());
    return repository.deleteMechanic(mechanic.getMechanicId());
  }

  @GetMapping("/mechanicsByCertification")
  public Collection<Mechanic> listMechanicsByCert(
    @RequestParam("cert") String cert) {
    System.out.println("getting mechanic with cert: " + cert);
    Collection<Mechanic> list = repository.listMechanicsByCert(cert).stream()
      .collect(Collectors.toList());
    for (Mechanic mech : list) {
      mech.calcCertCount();
    }
    return list;
  }

  @GetMapping("/mechanicsById")
  public Collection<Mechanic> listMechanicsById(
    @RequestParam("mechanicId") int mechanic) {
    System.out.println("getting mechanic with id: " + mechanic);
    Collection<Mechanic> list = repository.listMechanicsById(mechanic).stream()
      .collect(Collectors.toList());
    for (Mechanic mech : list) {
      mech.calcCertCount();
    }
    return list;
  }
}
