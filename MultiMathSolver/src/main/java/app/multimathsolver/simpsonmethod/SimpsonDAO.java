package app.multimathsolver.simpsonmethod;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
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
public class SimpsonDAO {

    @Id
    private Long Id;

    @Column
    private double minValue;

    @Column
    private double maxValue;

    @Column
    private int functionNumber;

    @Column
    private double epsilon;

    @Column
    private String result;
}
