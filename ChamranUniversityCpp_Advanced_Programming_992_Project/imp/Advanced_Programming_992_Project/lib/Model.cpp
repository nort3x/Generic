#include "../include/Model.h"
#include "../include/Agent.h"
#include "../include/Structure.h"
bool Model::is_name_in_use(const std::string &name) const {
    bool ans = false;
    for(const auto& agent: agents){
        ans |= (agent.get_name() == name);
    }

    for(const auto& s: structs){
        ans |= (s.get_name() == name);
    }
    return ans;
}