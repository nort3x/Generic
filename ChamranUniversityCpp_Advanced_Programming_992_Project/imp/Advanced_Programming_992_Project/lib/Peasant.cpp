#include "../include/Peasant.h"
#include "../include/Structure.h"

Peasant::Peasant(const std::string &name_, Point location_) : Agent(name_, location_) {
    std::cout<<"Peasant "<<get_name()<<" constructed "<<std::endl;
}

Peasant::~Peasant() {
    std::cout<<"Peasant "<<get_name()<<" destructed "<<std::endl;
}

void Peasant::move_to(Point dest) {
    Agent::move_to(dest);
}

void Peasant::stop() {
    Agent::stop();
    src= nullptr;
    des= nullptr;
    carryingFood = 0;
}

void Peasant::update() {
    Agent::update();
    if(s != NotWorking){
        switch (s) {
            case State::Collecting: {
                double amount = this->src->withdraw(10);
                if (amount != 0) { // wait until you getSomething
                    Agent::move_to(des->get_location());
                    s = OutBound;
                    carryingFood+=amount;
                }
                break;
            }
            case State::OutBound: {
                if(cartesian_distance(des->get_location(),get_location())<0.0001){
                    Agent::stop();
                    s = Depositing;
                }
                break;
            }
            case State::Depositing:{
                des->deposit(carryingFood);
                Agent::move_to(src->get_location());
                s = Inbound;
                break;
            }
            case State::Inbound:{
                if(cartesian_distance(src->get_location(),get_location())<0.0001){
                    Agent::stop();
                    s = Collecting;
                }
                break;
            }
        }
    }
}

void Peasant::start_working(Structure *source_, Structure *destination_) {
    src = source_;
    des = destination_;

    s = Inbound;
}

void Peasant::describe() const {
    using namespace std;
    Agent::describe();

    cout << "Peasant ";
    cout << "   Carrying " << carryingFood << endl;
    cout << "   Outbound to destination " << des->get_name()  << endl;
    cout << "   Inbound to source " << src->get_name() << endl;
    cout << "   Collecting at source " << src->get_location() << endl;
    cout << "   Depositing at destination " << des->get_location()  << endl;
}
