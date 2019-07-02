package edu.vt.cs4604.auto;

import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Collection;
import java.util.stream.Collectors;
import java.util.*;

@RestController
class CertificationController {
  @Autowired
  private CertificationRepository repository;

  public CertificationController(CertificationRepository repository) {
    this.repository = repository;
  }

  @GetMapping("/certifications")
  public Collection<Certification> certifications() {
    System.out.println("getting certifications");
    return repository.findAll().stream()
      .collect(Collectors.toList());
  }
  @PostMapping("/certifications")
  public Certification addCertification(@RequestBody Certification certification) {
    System.out.println(certification.toString());
    return repository.createCertification(certification.getName());
  }
  @PostMapping("/deleteCertification")
  public Certification deleteCertification(@RequestBody String cert_name) {
    System.out.println("Certification Name: " + cert_name);
    return repository.deleteCertification(cert_name);
  }

  @GetMapping("/certNames")
  public Collection<String> listCertNames() {
    Collection<Certification> certList = repository.findAll().stream()
      .collect(Collectors.toList());
    ArrayList<String> nameList = new ArrayList<String>();
    nameList.add("");
    for (Certification cert : certList) {
      nameList.add(cert.getName());
    }
    return nameList;
  }
}
