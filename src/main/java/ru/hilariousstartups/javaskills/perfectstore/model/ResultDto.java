package ru.hilariousstartups.javaskills.perfectstore.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResultDto {

    private String status;
    private Double score;
    private String logs;
    private String[] errors;

}
