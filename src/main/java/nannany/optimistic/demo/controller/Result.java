package nannany.optimistic.demo.controller;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Result {

    Integer id;
    String firstName;
    String lastName;
    String career;
    Timestamp updateDate;
    Timestamp insertDate;
}
