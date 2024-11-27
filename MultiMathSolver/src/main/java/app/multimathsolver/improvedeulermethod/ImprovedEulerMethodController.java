package app.multimathsolver.improvedeulermethod;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/api")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class ImprovedEulerMethodController {
    private ImprovedEulerMethodService improvedEulerMethodService;

    public ImprovedEulerMethodController(ImprovedEulerMethodService improvedEulerMethodService){
        this.improvedEulerMethodService = improvedEulerMethodService;
    }

    @PutMapping(path = "/euler", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Double> calculateCholetsky(@RequestBody ImprovedEulerMethodDTO input){
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
