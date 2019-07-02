package edu.vt.cs4604.auto;

import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Collection;
import java.util.stream.Collectors;

@RestController
class PhoneNumberController {
  @Autowired
  private PhoneNumberRepository repository;

  public PhoneNumberController(PhoneNumberRepository repository) {
    this.repository = repository;
  }

  @GetMapping("/phoneNumbers")
  public Collection<PhoneNumber> phoneNumbers(@RequestParam("customerId") int customer_Id) {
    System.out.println("getting number");
    return repository.listPhoneNumbersByCustomer(customer_Id).stream()
      .collect(Collectors.toList());
  }
  @PostMapping("/phoneNumbers")
  public PhoneNumber addPhoneNumber(@RequestBody PhoneNumber phoneNumber) {
    System.out.println(phoneNumber.toString());
    return repository.createPhoneNumber(phoneNumber.getPhoneNumber(), phoneNumber.getCustomerId());
  }
  @PostMapping("/deletePhoneNumber")
  public PhoneNumber deletePhoneNumber(@RequestBody PhoneNumber phoneNumber) {
    System.out.println("Phone Number: " + phoneNumber.getPhoneNumber());
    return repository.deletePhoneNumber(phoneNumber.getPhoneNumber());
  }
}
