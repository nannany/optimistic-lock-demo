package nannany.optimistic.demo.controller;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonSerialize
public class NormalResult {

    Integer id;
    String firstName;
    String lastName;
    String career;
    Timestamp updateTime;
    Timestamp insertTime;
}
