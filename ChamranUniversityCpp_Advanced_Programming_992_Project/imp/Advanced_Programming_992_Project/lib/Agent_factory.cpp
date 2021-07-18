#include "../include/Agent_factory.h"
#include "../include/Geometry.h"
#include <map>
#include <functional>
#include "../include/exceptions.h"
Agent *create_agent(const std::string &name, const std::string &type, Point location) {
    // lazy and static , like a boss
    static std::map<std::string, std::function<Agent *(const std::string&,Point)>> creators = {
            {"soldier",
                    [](const std::string &name, Point location) -> Agent * {
                        //todo
                    }
            },
            {"farmer",
                    [](const std::string &name, Point location) -> Agent * {
                        //todo
                    }
            }
            // add more later (?)
    };

    if(!creators.contains(type))
        throw IllegalType("no creator found for requested type");

    return creators.at(type)(name,location);
}