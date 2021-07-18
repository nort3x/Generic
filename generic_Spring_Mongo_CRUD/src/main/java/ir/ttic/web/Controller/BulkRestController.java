package ir.ttic.web.Controller;

import ir.ttic.web.DataBaseAssistor.BasicMongoDB;
import ir.ttic.web.Models.MovieDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

@RestController
@RequestMapping("/api/bulk")
public class BulkRestController {

    @Qualifier("basicMongoDB")
    @Autowired
    BasicMongoDB mongoDB;
    @GetMapping("reload")
    public ResponseEntity<String> reloadFromFile() throws IOException {
        File f = new File("movies.json");
        if(!f.exists())
            return new ResponseEntity<String>("Document File Doesnt exist", HttpStatus.NOT_FOUND);

        FileInputStream fis = new FileInputStream(f);
        String source = Utils.readWholeFile(fis);
        MovieDocument[] movies = (MovieDocument[]) Utils.JSONStringToObject(source, MovieDocument[].class);

        mongoDB.removeEverythingInDatabase();
        for (MovieDocument movie : movies) {
            mongoDB.insertMovieToDB(movie);
        }
        return ResponseEntity.ok("movies database reload from file successfully");
    }


}
