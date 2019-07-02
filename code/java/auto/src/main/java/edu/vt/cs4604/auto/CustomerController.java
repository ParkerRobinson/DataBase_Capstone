package edu.vt.cs4604.auto;

import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Collection;
import java.util.stream.Collectors;

@RestController
class CustomerController {
  @Autowired
  private CustomerRepository repository;

  public CustomerController(CustomerRepository repository) {
    this.repository = repository;
  }

  @GetMapping("/customers")
  public Collection<Customer> customers() {
    System.out.println("getting customers");
    return repository.findAll().stream()
      .collect(Collectors.toList());
  }
  @PostMapping("/customers")
  public Customer addCustomer(@RequestBody Customer customer) {
    System.out.println(customer.toString());
    return repository.createCustomer(customer.getName(), customer.getAddress());
  }
  @PostMapping("/updateCustomer")
  public Customer updateCustomer(@RequestBody Customer customer) {
    System.out.println(customer.toString());
    return repository.updateCustomer(customer.getName(), customer.getAddress(), customer.getCustomerId());
  }
  @PostMapping("/deleteCustomer")
  public Customer deleteCustomer(@RequestBody int customer_Id) {
    System.out.println("Customer ID: " + customer_Id);
    return repository.deleteCustomer(customer_Id);
  }
}
