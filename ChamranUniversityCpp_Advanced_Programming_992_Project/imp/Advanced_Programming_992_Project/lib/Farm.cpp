#include "../include/Farm.h"

Farm::Farm(const std::string &name_, Point location_) : Structure(name_,location_){
    foodInside = 50;
    std::cout<<"Farm "<<get_name()<<" constructed"<<std::endl;
}

Farm::~Farm() {
    std::cout<<"Farm "<<get_name()<<" destructed"<<std::endl;
}

double Farm::withdraw(double amount_to_get) {
    double takingFood = foodInside-amount_to_get;
    if(takingFood<0)
        takingFood = foodInside;
    foodInside-= takingFood;
    return takingFood;
}

void Farm::update() {
    Structure::update();
    foodInside+=2;
    std::cout<<"Farm "<<get_name()<<" FoodLevel "<<foodInside<<std::endl;
}

void Farm::describe() const {
    std::cout<<"Farm ";
    Structure::describe();
    std::cout<<"FoodLevel "<<foodInside<<std::endl;
}
