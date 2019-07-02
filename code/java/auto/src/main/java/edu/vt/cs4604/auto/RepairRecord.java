package edu.vt.cs4604.auto;

import lombok.*;
import java.time.LocalDate;
import javax.persistence.*;
import java.util.Date;

/*
 * NamedNativeQueries allow us to use _SQL_ queries
 * to perform complex queries against the database.
 * Alternatively NamedQueries use _JPQL_ to accomplish
 * the same thing.
 * https://en.wikipedia.org/wiki/Java_Persistence_Query_Language
 */
@NamedNativeQueries({
  @NamedNativeQuery(
    name = "RepairRecord.listRepairRecords", 
    query = "SELECT * FROM RepairRecord", 
    resultClass = RepairRecord.class
  ),
  @NamedNativeQuery(
    name = "RepairRecord.listRepairRecordsByCar", 
    query = "SELECT * FROM RepairRecord rr " +
            "WHERE rr.vin = :vin", 
    resultClass = RepairRecord.class
  ),
  @NamedNativeQuery(
    name = "RepairRecord.listRepairRecordsByMechanic", 
    query = "SELECT * FROM RepairRecord rr " +
            "WHERE rr.mechanic_Id = :mechanic", 
    resultClass = RepairRecord.class
  ),
  @NamedNativeQuery(
    name = "RepairRecord.createRepairRecord", 
    query = "INSERT into RepairRecord (date, description, mechanic_Id, vin) " + 
            "VALUES (:new_date, :new_desc, :new_mechanic, :new_vin) " +
            "RETURNING *", 
    resultClass = RepairRecord.class
  ),
  @NamedNativeQuery(
    name = "RepairRecord.updateRepairRecord", 
    query = "UPDATE RepairRecord " + 
            "SET date = :new_date, description = :new_desc, mechanic_Id = :new_mechanic, vin = :new_vin  " +
            "WHERE record_Id = :update_record " +
            "RETURNING *", 
    resultClass = RepairRecord.class
  ),
  @NamedNativeQuery(
    name = "RepairRecord.deleteRepairRecord", 
    query = "DELETE FROM RepairRecord WHERE record_Id = :delete_record RETURNING *",
    resultClass = RepairRecord.class
  )
})

@Entity
@Getter @Setter
@NoArgsConstructor
@ToString @EqualsAndHashCode
public class RepairRecord {
  @Id
  @SequenceGenerator(name="record_id", sequenceName="record_id")
  @GeneratedValue(generator="record_id")
  private int record_Id;

  private Date date;

  @ManyToOne()
  @JoinColumn(name="description")
  private RepairDescription repairDescription;

  @ManyToOne()
  @JoinColumn(name="mechanic_Id")
  private Mechanic mechanic;

  @ManyToOne()
  @JoinColumn(name="vin")
  private Car car;

  @Transient
  private double totalCost;

  public int getRecordId() {
    return record_Id;
  }
  public Date getDate() {
    return date;
  }
  public RepairDescription getRepairDescription() {
    return repairDescription;
  }
  public Mechanic getMechanic() {
    return mechanic;
  }
  public Car getCar() {
    return car;
  }
  public int getMechanicId() {
    return mechanic.getMechanicId();
  }
  public String getDescription() {
    return repairDescription.getDescription();
  }
  public String getVin() {
    return car.getVin();
  }
  public double getTotalCost() {
    return totalCost;
  }
  public int getHoursNeeded() {
    return repairDescription.getHoursNeeded();
  }

  public void generateCost() {
    totalCost = repairDescription.getPartsCost() + (mechanic.getHourlyRate() * repairDescription.getHoursNeeded() * 1.5);
  }

}