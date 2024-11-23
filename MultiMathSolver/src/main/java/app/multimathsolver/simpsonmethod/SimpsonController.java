package app.multimathsolver.simpsonmethod;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping(path = "/api")
public class SimpsonController {
    public SimpsonService simpsonService;

    public SimpsonController(SimpsonService simpsonService) {
        this.simpsonService = simpsonService;
    }
    @PutMapping(path = "/simpson", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Double> calculateSimpson(@RequestBody SimpsonDAO input){
        Double result;

        try {
            double minValue = input.getMinValue();
            double maxValue = input.getMaxValue();
            int numberOfFunction = input.getFunctionNumber();
            double epsilon = input.getEpsilon();

            result = simpsonService.calculate_integral(minValue, maxValue, numberOfFunction, epsilon);
        }
        catch(Exception e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}
