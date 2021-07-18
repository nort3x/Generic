
#include "../include/Soldier.h"
#include "../include/Geometry.h"
#include "../include/Moving_object.h"

Soldier::Soldier(const std::string &name, const Point &location) : Agent(name, location) {constructed();}

Soldier::Soldier(const std::string &name, const Point &location, int health) : Agent(name, location, health) {constructed();}

void Soldier::update() {
    Agent::update();
    if (is_alive() && target != nullptr) { // being alive and having someone to attack
        if (cartesian_distance(target->get_location(), this->get_location()) < radius)
            target->take_hit(strength, this);
        else
            std::cout << get_name() << ": Target is out of range!" << std::endl;
    }
}


void Soldier::start_attacking(Agent *target_ptr) {
    if (target_ptr == this)
        throw std::invalid_argument(std::string(get_name()).append(": I cannot attack myself!"));
    if (target_ptr != nullptr) {
        if (target_ptr->is_alive()) {
            std::cout << this->get_name() << ": I'm attacking! ->" << target_ptr->get_name() << std::endl;
            target = target_ptr;
        } else
            throw std::invalid_argument(
                    std::string(get_name()).append(": target is not alive! -> ").append(target_ptr->get_name()));
    }
}

void Soldier::take_hit(int attack_strength, Agent *attacker_ptr) {
    Agent::take_hit(attack_strength, attacker_ptr);
    if (attacker_ptr != this) {
        if (target != nullptr && target->is_alive())
            return;
        else
            start_attacking(attacker_ptr);
    }
}

void Soldier::stop() {
    std::cout<<get_name()<<": Don't bother me" << std::endl;
}

void Soldier::describe() const {
    Agent::describe();
    std::cout << "Soldier "<<get_name()<<std::endl;
    std::cout << (isAttacking?"Attacking":"not Attacking")<<std::endl;
}
