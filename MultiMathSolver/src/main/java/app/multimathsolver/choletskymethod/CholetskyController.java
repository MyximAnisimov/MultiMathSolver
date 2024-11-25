package app.multimathsolver.choletskymethod;

import app.multimathsolver.jwt.JwtFilter;
import app.multimathsolver.jwt.JwtUtils;
import app.multimathsolver.user.User;
import app.multimathsolver.user.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(path = "/api")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class CholetskyController {

    @Autowired
    private CholetskyService choletskyService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private JwtFilter jwtFilter;

    public CholetskyController(CholetskyService choletskyService){
        this.choletskyService = choletskyService;
    }

    @PutMapping(path = "/choletsky", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> calculateCholetsky(@Valid @RequestBody CholetskyDAO input, HttpServletRequest request){
        //Вынести парсер в отдельный метод или класс
        String convertedResult = "";
        User user = userRepository.findByEmail(jwtUtils.getUserNameFromJwtToken(jwtFilter.parseJwt(request)));
        if(user == null){
            return new ResponseEntity<>("Вы не авторизированы в системе!",HttpStatus.UNAUTHORIZED);
        }
        List<Double> resultVector;
        try {
            List<List<Double>> matrixValues = new ArrayList<>();
            List<Double> vector = new ArrayList<>();
            String[] arrMatrixValues = input.getMatrix().split("\n");
            String[] arrVectorValues = input.getVector().split("\n");
            int n = input.getSize();

            for (int i = 0; i < n; i++) {
                String[] matrixRow = arrMatrixValues[i].split(" ");
                vector.add(Double.parseDouble(arrVectorValues[i]));
                matrixValues.add(i, new ArrayList<>());
                for (int j = 0; j < n; j++) {
                    matrixValues.get(i).add(j, Double.parseDouble(matrixRow[j]));
                }
            }


            resultVector = choletskyService.solveByCholeskyDecomposition(n, matrixValues, vector);
            if(resultVector == null) return new ResponseEntity<>("Вы ввели некорректные данные!",HttpStatus.BAD_REQUEST);
            convertedResult = resultVector.toString();
        }
        catch(Exception e){
            return new ResponseEntity<>("Вы ввели некорректные данные!",HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(convertedResult, HttpStatus.OK);
    }


}
