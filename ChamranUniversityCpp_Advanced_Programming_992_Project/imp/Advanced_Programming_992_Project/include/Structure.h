#ifndef STRUCTURE_H
#define STRUCTURE_H

/* A Structure is a Sim_object with a location and interface to derived types */

class Structure:public Sim_object, private Moving_object{
public:
    // *** provide a constructor and destructor
    // *** first constructor parameter should be the name, specified as a string, the second should be a Point for the location.

    Structure(const std::string & name,Point location):Moving_object(location,0),Sim_object(name){}
    virtual ~Structure()=default;

    // *** declare and define here appropriately
    Point get_location(){
        return Moving_object::get_current_location();
    }

    // *** declare and define the following functions as specified
    void update()

    // output information about the current state
    void describe()

    // ask model to notify views of current state
    void broadcast_current_state()

    // fat interface for derived types
    double withdraw(double amount_to_get);

    void deposit(double amount_to_give);

};
#endif