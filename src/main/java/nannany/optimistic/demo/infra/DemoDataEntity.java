package nannany.optimistic.demo.infra;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.sql.Timestamp;

@Entity
@Table(name = "demodata")
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class DemoDataEntity {

    @Id
    Integer id;
    String firstName;
    String lastName;
    String career;
    Timestamp updateTime;
    Timestamp insertTime;


}
