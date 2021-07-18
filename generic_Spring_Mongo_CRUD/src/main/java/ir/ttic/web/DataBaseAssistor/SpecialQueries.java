package ir.ttic.web.DataBaseAssistor;

import com.mongodb.client.MongoClient;
import ir.ttic.web.Models.MovieDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;


@Component
public class SpecialQueries extends BasicMongoDB {
    public SpecialQueries(@Autowired MongoClient mc) {
        super(mc);
    }

    public String[] nameOfEveryUSMovie() {
        return find(new Query().addCriteria(Criteria.where("country").is("USA")))
                .stream().map(item -> item.title).toArray(String[]::new);
    }

    public String[] nameOfEveryAfter2000Movies() {
        return find(new Query().addCriteria(Criteria.where("year").gt(2000)))
                .stream().map(item -> item.title).toArray(String[]::new);

    }

    public MovieDocument[] everyThing() {
        return super.mt.findAll(MovieDocument.class, "movies").toArray(new MovieDocument[0]);
    }

    public String[][] everyYearTitleCountry() {
        return super.mt.findAll(MovieDocument.class, "movies")
                .stream()
                .map(item -> new String[]{String.valueOf(item.year), item.country, item.title})
                .toArray(String[][]::new);
    }

    public MovieDocument[] firstNMovies(String country, int n) {
        return find(new Query(Criteria.where("country").is(country)).limit(n)).toArray(MovieDocument[]::new);
    }


    public String[] everyMovieInUSAfter2000() {
        return find(new Query(Criteria.where("country").is("USA").and("year").gt(2000)))
                .stream().map(item -> item.title).toArray(String[]::new);
    }

    public String[][] everyMovieStartingByTGenreAndTitle() {
        return find(new Query(Criteria.where("title").regex("^T"))).stream().map(item -> new String[]{item.title, item.genre}).toArray(String[][]::new);
    }

    public String[] whichDirectorsBDDoesntExist() {
        return find(new Query(Criteria.where("director.birth_date").is(null)))
                .stream().map(item -> item.director.first_name + " " + item.director.first_name)
                .toArray(String[]::new);
    }

    public String[] everyMovieByDirectorSteven() {
        return find(new Query(Criteria.where("director.first_name").is("Steven")))
                .stream().map(item -> item.title)
                .toArray(String[]::new);
    }

    public String[] everyMovieByPlayedByTomCruise1962() {
        return find(
                new Query(
                        Criteria.where("actors").elemMatch(
                                Criteria.where("first_name").is("Tom")
                                        .and("last_name").is("Cruise")
                                        .and("birth_date").is("1962")
                        )
                )
        )
                .stream().map(item -> item.title)
                .toArray(String[]::new);
    }
}
