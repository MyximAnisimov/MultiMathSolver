package app.multimathsolver.newtonmethod;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class NewtonDAO {

    private Long id;

    private int systemId;

    private int numberOfUnknowns;

    private String initialApproximation;

    private String result;
}
