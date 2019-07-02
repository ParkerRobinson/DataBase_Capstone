package edu.vt.cs4604.auto;

import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Collection;
import java.util.stream.Collectors;
import java.util.ArrayList;

@RestController
class CarController {
  @Autowired
  private CarRepository repository;

  public CarController(CarRepository repository) {
    this.repository = repository;
  }

  @GetMapping("/cars")
  public Collection<Car> cars() {
    System.out.println("getting cars");
    return repository.findAll().stream()
      .collect(Collectors.toList());
  }
  @PostMapping("/cars")
  public Car addCar(@RequestBody Car car) {
    System.out.println(car.toString());
    return repository.createCar(car.getVin(), car.getMake(), car.getModel(), car.getYear(),
        car.getCustomerId());
  }
  @PostMapping("/updateCar")
  public Car updateCar(@RequestBody Car car) {
    System.out.println(car.toString());
    return repository.updateCar(car.getMake(), car.getModel(), car.getYear(),
        car.getCustomerId(), car.getVin());
  }
  @PostMapping("/deleteCar")
  public Car deleteCar(@RequestBody String vin) {
    System.out.println("Vin " + vin);
    return repository.deleteCar(vin);
  }

  @GetMapping("/vins")
  public Collection<String> listVins() {
    System.out.println("Getting vin numbers");
    Collection<Car> carList = repository.findAll().stream()
      .collect(Collectors.toList());
    ArrayList<String> vinList = new ArrayList<String>();
    for( Car car : carList) {
      vinList.add(car.getVin());
    }
    return vinList;
  }

  //WE should not need this method .
  
  //@GetMapping("/activeScouts")
  //public Collection<Scout> activeScouts(@RequestParam("days") String days) {
    //System.out.println("getting active scouts");
    //return repository.activeScouts(days).stream()
      //.collect(Collectors.toList());
  //}
}
