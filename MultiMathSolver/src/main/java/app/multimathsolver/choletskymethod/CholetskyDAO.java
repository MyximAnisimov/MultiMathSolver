package app.multimathsolver.choletskymethod;


import app.multimathsolver.user.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Id;


@Getter
@Setter
@ToString
@NoArgsConstructor
public class CholetskyDAO {

    private int size;

    private String matrix;

    private String vector;


}
