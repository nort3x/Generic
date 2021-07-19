#ifndef ADVANCEOOPCPP_PEASANT_H
#define ADVANCEOOPCPP_PEASANT_H

#include "Agent.h"

/*
A Peasant is an Agent that can move food between Structures. It can be commanded to
start_working, whereupon it moves to the source, picks up food, returns to destination,
deposits the food, returns to source.  If picks up zero food at the source, it waits there
and tries again on the next update.
If commanded to move_to somewhere, it stops working, and goes there.
*/


class Peasant:public Agent {
public:
// *** define these functions in .cpp; initialize with zero amount being carried
    Peasant(const std::string &name_, Point location_);

    ~Peasant();

// implement Peasant behavior
    void update() override;

// overridden to suspend working behavior
    void move_to(Point dest) override;

// stop moving and working
    void stop() override;

// starts the working process
// Throws an exception if the source is the same as the destination.
    void start_working(Structure *source_, Structure *destination_) override;

// output information about the current state
    void describe() const override;

private:
    enum State{
        Collecting,
        OutBound,
        Inbound,
        Depositing,
        NotWorking
    };
    double carryingFood=0;
    Structure* src;
    Structure* des;
    State s = NotWorking;
};


#endif //ADVANCEOOPCPP_PEASANT_H
