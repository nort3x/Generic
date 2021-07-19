#include "../include/Controller.h"

#include <iostream>
#include <string>
#include "../include/Model.h"
#include "../include/View.h"
#include "../include/Utility.h"
#include "../include/Agent.h"
using namespace std;

void getInput(std::string& s){
    cout<<"#~> ";
    cin >> s;
}
void Controller::run() {
    string s;
    while (true) {
       getInput(s);
        if (s == "quite") {
            cout << "Done\n";
            break;
        } else if (m->is_agent_present(s)) {
            Agent& a = *m->get_agent_ptr(s);
            if(!a.is_alive())
                throw Error("Agent is not Alive!");
            getInput(s);


        }else{
            throw Error("Unrecognized Command");
        }
    }
}

Controller::Controller() {
m = new Model();
v = new View();
}

Controller::~Controller() {
    delete m;
    delete v;
}
