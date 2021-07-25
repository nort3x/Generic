package objects;

import exceptions.AlreadyExist;
import exceptions.NotFoundException;
import interfaces.IDataBase;

public class Student {

    public String getId() {
        return id;
    }

    public String getPassword() {
        return password;
    }

    final private String id,password;
    final private IDataBase dbInstance;
    private Student(String id, String password,IDataBase dbInstance) {
        this.id = id;
        this.password = password;
        this.dbInstance = dbInstance;
    }


    /// creators
    public static Student signUp(String id, String password, IDataBase db) throws AlreadyExist {
        Student s = new Student(id,password,db);
        db.insertIfNotExist(s);
        return s;
    }

    public static Student login(String id, String password, IDataBase db) throws NotFoundException {
        Student s = new Student(id,password,db);
        if(!db.isAuthAble(s))
            throw new NotFoundException();
        return s;
    }
}
