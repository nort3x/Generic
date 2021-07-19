
#ifndef SOLDIER_H
#define SOLDIER_H

/*
A Soldier is an Agent that has attack and defense behaviors. It can be commanded
to start attacking another Agent and will continue the attack as long as 
it is alive and the target is alive and in range. If attacked, the Soldier will
start attacking its attacker.
*/

#include "Agent.h"

class Soldier:public Agent {
public:

    Soldier(const std::string &name, const Point &location);

    Soldier(const std::string &name, const Point &location, int health);

    Soldier(const std::string &name, const Point &location, int health, int strength):Agent(name ,location,health),strength(strength){
        constructed();
    };
    Soldier(const std::string &name, const Point &location, int health, int strength,double radius):Agent(name ,location,health),radius(radius),strength(strength){
        constructed();
    }

    ~Soldier() override{
        std::cout<<"Soldier "<<get_name()<<" destructed "<<std::endl;
    };

    // update implements Soldier behavior
    void update() override;

    // Make this Soldier start attacking the target Agent.
    // Throws an exception if the target is the same as this Agent,
    // is out of range, or is not alive.
    void start_attacking(Agent *target_ptr) override;

    // Overrides Agent's take_hit to counterattack when attacked.
    void take_hit(int attack_strength, Agent *attacker_ptr) override;

    // Overrides Agent's stop to print a message
    void stop() override;

    // output information about the current state
    void describe() const override;

private:
    Agent* target = nullptr;
    int strength=1;
    double radius = 1;
    bool isAttacking = false;
    void constructed(){
        std::cout<<"Soldier "<<get_name()<<" constructed "<<std::endl;
    }
};

#endif