package nannany.optimistic.demo.infra;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.sql.Timestamp;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DemoDataEntity {

    @Id
    Integer id;
    String firstName;
    String lastName;
    String career;
    Timestamp updateDate;
    Timestamp insertDate;


}
