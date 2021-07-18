

#ifndef AGENT_H
#define AGENT_H
/*
Agents are a kind of Sim_object, and privately inherit from Moving_object.
Agents can be commanded to move to a destination. Agents have a health, which
is decreased when they take a hit. If the Agent's health > 0, it is alive.
If its heath <= 0, it starts dying, then on subsequent updates, 
it becomes dead, and finally disappearing.
*/

#include <iostream>
#include "Moving_object.h"
#include "Sim_object.h"
#include <stdexcept>

class Structure;

class Agent : public Sim_object, private Moving_object {

    enum State {
        ALIVE,
        DYING,
        DEAD,
        Disappearing
    };


public:
    ~Agent() override {
        std::cout << "Agent " << get_name() << " destructed" << std::endl;
    };

    // *** provide the definition of the following reader functions here in the class declaration
    // return true if this agent is Alive or Disappearing
    bool is_alive() const {
        return (s == State::ALIVE);
    }

    bool is_disappearing() const {
        return s == Disappearing;
    }

    // return this Agent's location
    Point get_location() const override {
        return Moving_object::get_current_location();
    }

    // return true if this Agent is in motion
    bool is_moving() const {
        return Moving_object::is_currently_moving();
    }

    // tell this Agent to start moving to location destination_
    virtual void move_to(Point destination_) {
        Moving_object::start_moving(destination_);
    }

    // tell this Agent to stop its activity
    virtual void stop() {
        Moving_object::stop_moving();
    }

    // Tell this Agent to accept a hit from an attack of a specified strength
    // The attacking Agent identifies itself with its this pointer.
    // A derived class can override this function.
    // The function lose_health is called to handle the effect of the attack.
    virtual void take_hit(int attack_strength, Agent *attacker_ptr) {
        if(attacker_ptr!=this)
            lose_health(attack_strength);
    }

    // update the moving state and Agent state of this object.
    void update() override {
        if (s == DYING)
            s = DEAD;
        else if (is_alive() && health <= 0)
            s = DYING;
        else if (s == DEAD)
            s = Disappearing;
    };

    // output information about the current state
    void describe() const override {
        using namespace std;
        cout << "   at " << get_location() << endl;
        cout << "   Health is " << health << endl;
        cout << "   Moving at speed " << get_current_speed() << " to " << get_current_destination() << endl;
        cout << "   Stopped" << !is_moving() << endl;
        cout << "   Is dying" << (s == DYING) << endl;
        cout << "   Is dead" << (s == DEAD) << endl;
        cout << "   Is disappearing" << (s == Disappearing) << endl; // not expected to be visible in this project
    };

    // ask Model to broadcast our current state to all Views
    void broadcast_current_state() const override { //TODO

    }
    /* Fat Interface for derived classes */
    // Throws exception that an Agent cannot work.
    virtual void start_working(Structure *, Structure *){
        throw std::invalid_argument("i dont work at all");
    };

    // Throws exception that an Agent cannot attack.
    virtual void start_attacking(Agent *){
        throw std::invalid_argument("i dont attack anyone");
    };

protected:
    Agent(const std::string &name_, Point location_) : Sim_object(name_), Moving_object(location_, 5) {
        health = 5;
        std::cout << "Agent " << get_name() << " constructed" << std::endl;
    }

    Agent(const std::string &name_, Point location_,int health) :health(health), Sim_object(name_), Moving_object(location_, 5) {
        std::cout << "Agent " << get_name() << " constructed" << std::endl;
    }

    // calculate loss of health due to hit.
    // if health decreases to zero or negative, Agent state becomes Dying, and any movement is stopped.
    void lose_health(int attack_strength) {
        health -= attack_strength;
    };

private:
    int health;
    State s = ALIVE;
};

#endif