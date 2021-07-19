#include "../include/Agent_factory.h"
#include "../include/Geometry.h"
#include <map>
#include <functional>
#include "../include/Utility.h"
#include "../include/Peasant.h"
#include "../include/Soldier.h"

Agent *create_agent(const std::string &name, const std::string &type, Point location) {
    // lazy and static , like a boss
    static std::map<std::string, std::function<Agent *(const std::string&,Point)>> creators = {
            {"Soldier",
                    [](const std::string &name, Point location) -> Agent * {
                        return new Soldier(name,location);
                    }
            },
            {"Peasant",
                    [](const std::string &name, Point location) -> Agent * {
                        return new Peasant(name,location);
                    }
            }
            // add more later (?)
    };

    if(!creators.contains(type))
        throw Error("no creator found for requested type");

    return creators.at(type)(name,location);
}