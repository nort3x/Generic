#include <map>
#include <functional>
#include "../include/Structure_factory.h"
#include "../include/Geometry.h"
#include "../include/Utility.h"
#include "../include/Farm.h"
#include "../include/Town_Hall.h"

Structure * create_structure(const std::string& name, const std::string& type, Point location){
    static std::map<std::string, std::function<Structure *(const std::string&,Point)>> creators = {
            {"Farm",
                    [](const std::string &name, Point location) -> Structure * {
                        return new Farm(name,location);
                    }
            },
            {"Town_Hall",
                    [](const std::string &name, Point location) -> Structure * {
                        return new Town_Hall(name,location);
                    }
            }
            // add more later (?)
    };

    if(!creators.contains(type))
        throw Error("no creator found for requested type");

    return creators.at(type)(name,location);
}