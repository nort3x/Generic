#include "../include/View.h"
#include "../include/Geometry.h"

View::View():viewMat(std::vector<std::vector<Sim_object*>>(rowSize,std::vector<Sim_object*>(colSize, nullptr))) {

}
View::~View() {
    // view is not responsible for deleting objects
}

void View::update_location(const std::string &name, Point location) {

}

void View::update_remove(const std::string &name) {

}

void View::draw() const {

}

void View::set_size(int size_) {

}

void View::set_scale(double scale_) {

}

void View::set_origin(Point origin_) {

}

void View::set_defaults() {

}
