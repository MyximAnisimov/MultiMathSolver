package app.multimathsolver.improvedeulermethod;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@Table(name = "newton")
public class ImprovedEulerMethodDAO {

    @Id
    private Long id;

    @Column
    private int numberOfFunction;

    @Column
    private double epsilon;

    @Column
    private double minValue;

    @Column
    private double y_a;

    @Column
    private double maxValue;

    @Column
    private String result;
}
