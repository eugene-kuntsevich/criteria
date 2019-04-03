import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/get")
public class WebService {

    @GetMapping("/city")
    public ResponseEntity<?> findById() {
        System.out.println("start app");
        return new ResponseEntity<>(MainClassCriteriaWithAnnotation.mainMethod(), HttpStatus.OK);
    }
}
