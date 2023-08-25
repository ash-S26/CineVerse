package dev.farhan.movieist.movies;


import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.security.Key;
import java.security.SecureRandom;
import java.util.*;

@RestController
public class LoginController {



    private final String jwt_secret = "toruoruyopuropyjbtoigbntopbnyotbhrtrpgjiothjfgfogjb";
    private final long jwtExpirationTime = 1000*60*100;

    @Autowired
    private UserRepository repository;

    private Key key(){
        return Keys.hmacShaKeyFor(
                Decoders.BASE64.decode(this.jwt_secret)
        );
    }
    @PostMapping("/api/v1/login")
    public ResponseEntity<Map<String , String>> loginUser(@RequestBody Map<String, String> body){

            System.out.println(body.get("email") + " " + body.get("password"));
        try{
            List<User> list = (List<User>) repository.findAll();
            for (User u: list) {
                System.out.println(u.toString());
            }

            User user = repository.findByEmail(body.get("email"));
            if (user.password.equals(body.get("password"))) {


                Date currentDate = new Date();
                Date expireDate = new Date(currentDate.getTime() + jwtExpirationTime);
                String token = Jwts.builder()
                        .setSubject(user.email)
                        .setIssuedAt(new Date())
                        .setExpiration(expireDate)
                        .signWith(key())
                        .compact();

                Map<String, String> response = new HashMap<>();

                response.put("status", "200");
                response.put("token", token);
                response.put("message", "Login Success");
                System.out.println(token);

                return new ResponseEntity<Map<String, String>>(response, HttpStatus.OK);
            } else {

                Map<String, String> response = new HashMap<>();

                response.put("status", "403");

                response.put("message", "Login Failed, Invalid Username or Password");

                return new ResponseEntity<Map<String, String>>(response, HttpStatus.OK);
            }
        }
        catch(Exception e){
                System.out.println(e);
                Map<String, String> response = new HashMap<>();

                response.put("status", "500");
                response.put("message", "Something went wrong");
                return new ResponseEntity<Map<String, String>>(response, HttpStatus.OK);
        }
    }


}
