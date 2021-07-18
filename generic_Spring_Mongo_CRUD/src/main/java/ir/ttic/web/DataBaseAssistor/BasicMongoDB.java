package ir.ttic.web.DataBaseAssistor;

import com.mongodb.DuplicateKeyException;
import com.mongodb.client.MongoClient;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Updates;
import ir.ttic.web.Models.MovieDocument;
import org.bson.BsonDocument;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.mongo.MongoClientFactory;
import org.springframework.data.mongodb.MongoDatabaseFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;


import java.util.List;

@Component
public class BasicMongoDB {


    protected MongoTemplate mt;

    public BasicMongoDB(@Autowired MongoClient mc){
        mt = new MongoTemplate(mc,"movies");
    }
    public void insertMovieToDB(MovieDocument md){
        try {
            mt.insert(md, "movies");
        }catch (DuplicateKeyException ignored){
        }
    }
    public void removeMovieFromDB(MovieDocument md){
        mt.remove(md,"movies");
    }
    public void replaceMovieInDB(MovieDocument md){
        mt.findAndReplace(new Query(Criteria.where("_id").is(md._id)),md,"movies");
    }
    public void updateMovieInDB(Bson filter, Bson update){
        mt.getCollection("movies").updateOne(filter,update);
    }

    public List<MovieDocument> find(Query q){
        return mt.find(q,MovieDocument.class,"movies");
    }

    public void removeEverythingInDatabase(){
        mt.dropCollection("movies");
    }


}
