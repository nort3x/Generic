#include <iostream>
#define LOG(s) std::cout<<s<<'\n';
#include "include/Controller.h"
int main(){
    using namespace std;


    cout.setf(ios::fixed, ios::floatfield);
    cout.precision(2);

    try {
        // create a Model object

        // create the Controller object and go
        Controller controller;
        controller.run();
    }
    catch(std::exception& error) {
        cout << "Other exception caught: " << error.what() << endl;
    }
    catch(...) {
        cout << "Unknown exception caught!" << endl;
    }

}