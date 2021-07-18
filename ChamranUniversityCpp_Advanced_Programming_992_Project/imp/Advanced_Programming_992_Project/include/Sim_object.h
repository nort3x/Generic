
#ifndef SIM_OBJECT_H
#define SIM_OBJECT_H

#include <string>

struct Point;

/* The Sim_object class provides the interface for all of simulation objects.
It also stores the object's name, and has pure virtual accessor functions for
the object's position and other information. */
class Sim_object {
public:
	Sim_object(const std::string& name_): name(name_){
        std::cout << "Sim_object " << name << " constructed" << std::endl;
	};

	virtual ~Sim_object(){  // for recursive destruction when dealing with raw pointers
        std::cout << "Sim_object " << name << " destructed" << std::endl;
    };


	const std::string& get_name() const
		{return name;}

	virtual Point get_location() const = 0;
	virtual void describe() const = 0;
	virtual void update() = 0;
	// ask model to notify views of current state
    virtual void broadcast_current_state() const = 0;

	// Sim_objects must be unique, so disable copy/move construction, assignment
    // of base class, which means that derived class objects can't be copied or moved either.
    Sim_object(const Sim_object&) = delete;
    Sim_object& operator= (const Sim_object&) = delete;
    Sim_object(Sim_object&&) = delete;
    Sim_object& operator= (Sim_object&&) = delete;

private:
	std::string name;
};

#endif