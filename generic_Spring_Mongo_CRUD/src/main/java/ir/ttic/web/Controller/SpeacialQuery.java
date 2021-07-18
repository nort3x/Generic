package ir.ttic.web.Controller;

import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Updates;
import ir.ttic.web.DataBaseAssistor.BasicMongoDB;
import ir.ttic.web.DataBaseAssistor.SpecialQueries;
import ir.ttic.web.Models.MovieDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/query")
public class SpeacialQuery {
    @Autowired
    SpecialQueries sp;


    @GetMapping("us-movies")
    public String[] getUSMoviesNames() {
        return sp.nameOfEveryUSMovie();
    }

    @GetMapping("all")
    public MovieDocument[] getUSMoviesNames2() {
        return sp.everyThing();
    }

    @GetMapping("after2000")
    public String[] getUSMoviesNames3() {
        return sp.nameOfEveryAfter2000Movies();
    }

    @GetMapping("every-year-country-title")
    public String[][] getUSMoviesNames4() {
        return sp.everyYearTitleCountry();
    }

    @GetMapping("first-3-france")
    public MovieDocument[] getUSMoviesNames5() {
        return sp.firstNMovies("FR", 3);
    }

    @GetMapping("us-after2000")
    public String[] getUSMoviesNames6() {
        return sp.everyMovieInUSAfter2000();
    }

    @GetMapping("movie-with-t")
    public String[][] getUSMoviesNames7() {
        return sp.everyMovieStartingByTGenreAndTitle();
    }

    @GetMapping("any-null-director-birth-date")
    public String[] getUSMoviesNames8() {
        return sp.whichDirectorsBDDoesntExist();
    }

    @GetMapping("director-steven")
    public String[] getUSMoviesNames9() {
        return sp.everyMovieByDirectorSteven();
    }

    @GetMapping("tom-cruise-1962")
    public String[] getUSMoviesNames10() {
        return sp.everyMovieByPlayedByTomCruise1962();
    }

    @GetMapping("insert")
    public String getInsert() {
        MovieDocument md = new MovieDocument();
        md._id = "testing";
        md.year = 2021;
        md.title = "mongoQuery";
        sp.insertMovieToDB(md);
        return "done";
    }

    @GetMapping("update")
    public String getUpdate() {
        MovieDocument mdo = new MovieDocument();
        mdo._id = "testing";
        mdo.year = 2021;
        mdo.title = "mongoQuery";
        MovieDocument mdn = new MovieDocument();
        mdn._id = "testing";
        mdn.year = 2022;
        sp.updateMovieInDB(Filters.eq("_id", mdo._id), Updates.set("year", "2022"));
        return "done";
    }


    @GetMapping("replace")
    public String getReplace() {
        MovieDocument md = new MovieDocument();
        md._id = "testing";
        md.year = 200002;
        md.title = "replacedddd!";
        sp.replaceMovieInDB(md);
        return "done";
    }


    @GetMapping("remove")
    public String getRemove() {
        sp.removeMovieFromDB(new MovieDocument() {
            {
                this._id = "testing";
                this.year = 200002;
                this.title = "replacedddd!";
            }
        });
        return "done";
    }




}
