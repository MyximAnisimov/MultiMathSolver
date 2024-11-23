package app.multimathsolver.improvedeulermethod;

import jakarta.persistence.Column;
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
public class ImprovedEulerMethodController {
    private ImprovedEulerMethodService improvedEulerMethodService;

    public ImprovedEulerMethodController(ImprovedEulerMethodService improvedEulerMethodService){
        this.improvedEulerMethodService = improvedEulerMethodService;
    }

    @PutMapping(path = "/euler", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Double> calculateCholetsky(@RequestBody ImprovedEulerMethodDAO input){
        double result;
        try {
            int numberOfFunction = input.getNumberOfFunction();

            double epsilon= input.getEpsilon();

            double minValue = input.getMinValue();

            double y_a = input.getY_a();

            double maxValue= input.getMaxValue();
            result = improvedEulerMethodService.solveByEulerImproved(numberOfFunction, epsilon, minValue, y_a, maxValue);
        }
        catch(Exception e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

}