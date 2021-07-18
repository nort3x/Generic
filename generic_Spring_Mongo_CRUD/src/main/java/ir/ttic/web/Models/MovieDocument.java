package ir.ttic.web.Models;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document
public class MovieDocument{
    @Id
    @Indexed(unique = true)
    public String _id;
    public String title;
    public int year;
    public String genre;
    public String summary;
    public String country;
    public Director director;
    public List<Actor> actors;
}
