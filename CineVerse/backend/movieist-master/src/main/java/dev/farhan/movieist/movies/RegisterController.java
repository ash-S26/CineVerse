package dev.farhan.movieist.movies;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

@RestController
public class RegisterController {

//d3BCseokW-cani-YFe3oSztJgaux4y

    @Autowired
    public UserRepository repository;
    @PostMapping("/api/v1/register")
    public ResponseEntity<String> registerUser(@RequestBody Map<String, String> body){
        System.out.println(body.get("name") + " " + body.get("email") + " " + body.get("password"));

        try{
            User user = repository.save(new User(body.get("email"), body.get("password"), body.get("name")));
            return new ResponseEntity<String>("Registered Successfully",HttpStatus.OK);
        }
        catch (Exception e) {
            System.out.println(e);
            return new ResponseEntity<String>("500",HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
