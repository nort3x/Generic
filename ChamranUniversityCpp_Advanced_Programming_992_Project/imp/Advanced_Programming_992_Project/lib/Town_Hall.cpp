#include "../include/Town_Hall.h"

Town_Hall::Town_Hall(const std::string &name_, Point location_):Structure(name_,location_) {
    std::cout<<"TownHall "<<get_name()<<" constructed"<<std::endl;
}
Town_Hall::~Town_Hall()  {
    std::cout<<"TownHall "<<get_name()<<" destructed"<<std::endl;
}

void Town_Hall::deposit(double deposit_amount) {
    foodInside+= deposit_amount;
}
double Town_Hall::withdraw(double amount_to_obtain) {
    double givableFood = foodInside/10;
    if(givableFood<1.0)
        return 0;
    if(givableFood<=amount_to_obtain)
        amount_to_obtain = givableFood;

    foodInside-= amount_to_obtain;
    return amount_to_obtain;

}

void Town_Hall::describe() const {
    std::cout<<"Town Hall";
    Structure::describe();
    std::cout<<" FoodInside" << foodInside<<std::endl;
}
