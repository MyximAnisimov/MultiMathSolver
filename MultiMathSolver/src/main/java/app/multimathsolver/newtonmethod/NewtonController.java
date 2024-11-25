package app.multimathsolver.newtonmethod;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(path = "/api")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class NewtonController {
    public NewtonService newtonService;

    public NewtonController(NewtonService newtonService) {
        this.newtonService = newtonService;
    }

    @PutMapping(path = "/newton", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> calculateNewton(@RequestBody NewtonDAO input) {
        String result;
        try {
            int systemId = input.getSystemId();
            int numberOfUnknowns = input.getNumberOfUnknowns();

            String[] approximation = input.getInitialApproximation().split("\n");
            List<Double> parsedNumbers = new ArrayList<>();
            for (int i = 0; i < approximation.length; i++) {
                parsedNumbers.add(Double.parseDouble(approximation[i]));
            }
            result = newtonService.solve_by_fixed_point_iterations(systemId, numberOfUnknowns, parsedNumbers).toString();
        } catch (Exception e) {
            return new ResponseEntity<>("Введены некорректные данные!", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}

