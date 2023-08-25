package dev.farhan.movieist.movies;

import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;


@NoArgsConstructor
@AllArgsConstructor
@Document( collection = "watchlist")
@Data
public class WatchList {


    private ObjectId id;
    private int userid;

    private List<String> list;

    public WatchList(int userid, List<String> list){
        this.userid = userid;
        this.list = list;
    }

}
