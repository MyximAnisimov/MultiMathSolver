package app.multimathsolver.newtonmethod;

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
public class NewtonDAO {

    @Id
    private Long id;

    @Column
    private int systemId;

    @Column
    private int numberOfUnknowns;

    @Column
    private String initialApproximation;

    @Column
    private String result;
}
