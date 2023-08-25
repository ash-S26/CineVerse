package dev.farhan.movieist.movies;


import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.security.Key;
import java.util.*;

@RestController
public class WatchListController {

    @Autowired
    public WatchListRepository repository;
    @Autowired
    MongoTemplate mongoTemplate;

    @Autowired
    public UserRepository userRepository;

    @Autowired
    private MovieService movieService;

    private final String jwt_secret = "toruoruyopuropyjbtoigbntopbnyotbhrtrpgjiothjfgfogjb";

    private Key key(){
        return Keys.hmacShaKeyFor(
                Decoders.BASE64.decode(this.jwt_secret)
        );
    }

    public Map<String, String> validateToken(String token){

        Map<String, String> mp = new HashMap<>();

        try{
            Jwts.parserBuilder()
                    .setSigningKey(key())
                    .build()
                    .parse(token);

            mp.put("validity", "true");
            mp.put("message", "Valid Token");

        } catch (MalformedJwtException e) {
            mp.put("validity", "false");
            mp.put("message", "Invalid JWT token: "+ e.getMessage());
            System.out.println("Invalid JWT token: "+ e.getMessage());
        } catch (ExpiredJwtException e) {
            mp.put("validity", "false");
            mp.put("message", "JWT token is expired: "+ e.getMessage());
            System.out.println("JWT token is expired: "+ e.getMessage());
        } catch (UnsupportedJwtException e) {
            mp.put("validity", "false");
            mp.put("message", "JWT token is unsupported: "+ e.getMessage());
            System.out.println("JWT token is unsupported: "+ e.getMessage());
        } catch (IllegalArgumentException e) {
            mp.put("validity", "false");
            mp.put("message", "JWT claims string is empty: "+ e.getMessage());
            System.out.println("JWT claims string is empty: "+ e.getMessage());
        }
        return mp;
    }


    @PostMapping("/api/v1/addToWatchList")
    public ResponseEntity<Map<String , String>> addToWatchList(@RequestBody Map<String , String> body){

        System.out.println(body.get("token"));

        String token = body.get("token");
        String imdbID = body.get("imdbID");

        Map<String , String> tokenCheck = validateToken(token);

        if(tokenCheck.get("validity").equals("false")){

            Map<String , String> response = new HashMap<>();

            response.put("status", "403");
            response.put("message", tokenCheck.get("message"));

            return new ResponseEntity<Map<String , String>>(response, HttpStatus.OK);
        }

        Claims claims = Jwts.parserBuilder()
                .setSigningKey(key())
                .build()
                .parseClaimsJws(token)
                .getBody();
        String email = claims.getSubject();

        User user = userRepository.findByEmail(email);

        WatchList userWatchList =  repository.findByUserid(user.id);
        System.out.println(userWatchList);
        if(userWatchList == null){
            System.out.println("here");
            List<String> list = new ArrayList<>();
            list.add(body.get("imdbID"));
            repository.insert(new WatchList(user.id, list ));
        }
        else{
            mongoTemplate.update(WatchList.class)
                    .matching(Criteria.where("userid").is(user.id))
                    .apply(new Update().addToSet("list", body.get("imdbID")))
                    .first();
        }




        Map<String , String> response = new HashMap<>();

        response.put("status", "200");

        return new ResponseEntity<Map<String , String>>(response, HttpStatus.OK);
    }

    @PostMapping("/api/v1/getwatchlist")
    public ResponseEntity<Map<String, Object>> getUserWatchList(@RequestBody Map<String, String> body){

        String token = body.get("token");

        Map<String , String> tokenCheck = validateToken(token);

        if(tokenCheck.get("validity").equals("false")){

            Map<String , Object> response = new HashMap<>();

            response.put("status", "403");
            response.put("message", tokenCheck.get("message"));

            return new ResponseEntity<Map<String , Object>>(response, HttpStatus.OK);
        }

        Claims claims = Jwts.parserBuilder()
                .setSigningKey(key())
                .build()
                .parseClaimsJws(token)
                .getBody();
        String email = claims.getSubject();

        User user = userRepository.findByEmail(email);

        WatchList userWatchList =  repository.findByUserid(user.id);

        List<String> movieimdbIDList = userWatchList.getList();

        System.out.println(movieimdbIDList);

        List<Optional<Movie>> watchListMovieDetails = new ArrayList<>();

        for (String id: movieimdbIDList) {
            Optional<Movie> mv = movieService.findMovieByImdbId(id);
            System.out.println(mv.getClass());

            watchListMovieDetails.add(mv);

        }

        Map<String , Object> response = new HashMap<>();



        response.put("status", "200");
        response.put("message", tokenCheck.get("message"));
        response.put("movies", watchListMovieDetails);




        return new ResponseEntity<Map<String , Object>>(response, HttpStatus.OK);

    }

}
