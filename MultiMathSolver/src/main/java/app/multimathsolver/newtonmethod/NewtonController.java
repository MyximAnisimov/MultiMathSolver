package app.multimathsolver.newtonmethod;

import app.multimathsolver.choletskymethod.CholetskyDAO;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(path = "/api")
public class NewtonController {
    public NewtonService newtonService;

    public NewtonController(NewtonService newtonService) {
        this.newtonService = newtonService;
    }

    @PutMapping(path = "/newton", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Double>> calculateNewton(@RequestBody NewtonDAO input) {
        List<Double> result;
        try {
            int systemId = input.getSystemId();
            int numberOfUnknowns = input.getNumberOfUnknowns();

            String[] approximation = input.getInitialApproximation().split("\n");
            List<Double> parsedNumbers = new ArrayList<>();
            for (int i = 0; i < approximation.length; i++) {
                parsedNumbers.add(Double.parseDouble(approximation[i]));
            }
            result = newtonService.solve_by_fixed_point_iterations(systemId, numberOfUnknowns, parsedNumbers);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}

