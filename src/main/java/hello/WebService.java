package hello;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WebService {

    @GetMapping("/getCity")
    public String findById() {
        System.out.println("start app");
        return MainClassCriteriaWithAnnotation.mainMethod();
    }
}
