#ifndef STRUCTURE_H
#define STRUCTURE_H

#include "Sim_object.h"
#include "Moving_object.h"
#include <iostream>

/* A Structure is a Sim_object with a location and interface to derived types */
class Structure:public Sim_object, private Moving_object{
public:
    // *** provide a constructor and destructor
    // *** first constructor parameter should be the name, specified as a string, the second should be a Point for the location.

    Structure(const std::string & name,Point location):Moving_object(location,0),Sim_object(name){
        std::cout<<"Structure "<<get_name()<<" constructed"<<std::endl;
    }
    virtual ~Structure(){
        std::cout<<"Structure "<<get_name()<<" destructed"<<std::endl;
    };

    // *** declare and define here appropriately
    Point get_location(){
        return Moving_object::get_current_location();
    }

    // *** declare and define the following functions as specified
    virtual void update() override{

    };

    // output information about the current state
    virtual void describe() const {
        std::cout<<get_name()<< " at " << Moving_object::get_current_location()<< std::endl;
    }

    // ask model to notify views of current state
    void broadcast_current_state(){
        //todo
    }

    // fat interface for derived types
     virtual double withdraw(double amount_to_get){};

     virtual void deposit(double amount_to_give){};

    Point get_location() const override {
        return get_current_location();
    }

    void broadcast_current_state() const override {

    }

protected:
    double foodInside = 0;

};
#endif