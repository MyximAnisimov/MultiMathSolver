package app.multimathsolver.simpsonmethod;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping(path = "/api")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class SimpsonController {
    public SimpsonService simpsonService;

    public SimpsonController(SimpsonService simpsonService) {
        this.simpsonService = simpsonService;
    }

    @PutMapping(path = "/simpson", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> calculateSimpson(@RequestBody SimpsonDTO input){
        double result;

        try {
            double aValue = input.getMinValue();
            double bValue = input.getMaxValue();
            int numberOfFunction = input.getFunctionNumbers();
            double epsilon = input.getEpsilon();

            result = simpsonService.calculateIntegral(aValue, bValue, numberOfFunction, epsilon);
        }
        catch(Exception e){
            return new ResponseEntity<>("Невозможно вычислить интеграл!", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(Double.toString(result), HttpStatus.OK);
    }
}
