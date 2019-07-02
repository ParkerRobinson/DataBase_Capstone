package edu.vt.cs4604.auto;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@SpringBootApplication
public class AutoRepairApplication {
  private static final Logger logger = LoggerFactory.getLogger(AutoRepairApplication.class); 

  public static void main(String[] args) {
    SpringApplication.run(AutoRepairApplication.class, args);
    logger.info("--Application Started--");
  }
}