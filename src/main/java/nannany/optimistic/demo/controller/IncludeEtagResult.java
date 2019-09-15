package nannany.optimistic.demo.controller;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;

import java.sql.Timestamp;

@Data
@JsonSerialize
public class IncludeEtagResult {

    Integer id;
    String firstName;
    String lastName;
    String career;
    Timestamp updateTime;
    Timestamp insertTime;
    String etag;
}
