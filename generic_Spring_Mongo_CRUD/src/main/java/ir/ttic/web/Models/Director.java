package ir.ttic.web.Models;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;

public class Director{
    @Id
    @Indexed(unique = true)
    public String _id;
    public String last_name;
    public String first_name;
    public String birth_date;
}
