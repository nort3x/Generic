/* Controller
This class is responsible for controlling the Model and View according to interactions
with the user.
*/



class Model;
class View;

class Controller {
public:
    Controller();

    ~Controller();  // delete any View objects that still exist

    // run the program by acccepting user commands
    void run();

private:
    Model* m;
    View* v;
};