package dev.farhan.movieist.movies;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface WatchListRepository extends MongoRepository<WatchList, Integer> {


    WatchList findByUserid(int i);
}
