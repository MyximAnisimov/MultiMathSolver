package app.multimathsolver.simpsonmethod;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class SimpsonDTO {

    private Long Id;

    private double minValue;

    private double maxValue;

    private int functionNumbers;

    private double epsilon;

//    private String result;
}
