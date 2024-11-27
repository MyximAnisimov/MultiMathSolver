package app.multimathsolver.improvedeulermethod;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class ImprovedEulerMethodDTO {

    private Long id;

    private int numberOfFunction;

    private double epsilon;

    private double minValue;

    private double y_a;

    private double maxValue;

//    private String result;
}
