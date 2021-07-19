#include "../include/Model.h"
#include "../include/Agent.h"
#include "../include/Structure.h"
#include "../include/Agent_factory.h"
#include "../include/Structure_factory.h"
bool Model::is_name_in_use(const std::string &name) const {
    bool ans = false;
    for(const auto& agent: agents){
        ans |= (agent->get_name() == name);
    }

    for(const auto& s: structs){
        ans |= (s->get_name() == name);
    }
    return ans;
}

bool Model::is_structure_present(const std::string &name) const {
    for(const auto& s: structs)
        if(s->get_name()==name)
            return true;
    return false;
}

void Model::add_structure(Structure *s) {
    structs.push_back(s);
}

Structure *Model::get_structure_ptr(const std::string &name) const {
    for (const auto& s: structs)
        if(s->get_name() == name)
            return s;
    return nullptr;
}

bool Model::is_agent_present(const std::string &name) const {
    for(const auto& a: agents)
        if(a->get_name() == name)
            return true;
    return false;
}

void Model::add_agent(Agent *a) {
    agents.push_back(a);
}

Agent *Model::get_agent_ptr(const std::string &name) const {
    for (const auto& a: agents)
        if(a->get_name() == name)
           return a;
    return nullptr;
}

void Model::describe() const {
    for (int i = 0; i < structs.size(); ++i) {
        structs[i]->describe();
    }

    for (int i = 0; i < agents.size(); ++i) {
        agents[i]->describe();
    }
}

void Model::update() {
    time++;
    for (int i = 0; i < structs.size(); ++i) {
        structs[i]->update();
    }

    for (int i = 0; i < agents.size(); ++i) {
        agents[i]->update();
    }
}

Model::~Model() {
    for (const auto& s: structs )
        delete s;
    for (const auto& a: agents )
        delete a;

}

Model::Model() {

    this->add_structure(create_structure("Rivendale", "Farm", Point(10., 10.)));
    this->add_structure(create_structure("Sunnybrook", "Farm", Point(0., 30.)));
    this->add_structure(create_structure("Shire", "Town_Hall", Point(20., 20.)));
    this->add_structure(create_structure("Paduca", "Town_Hall", Point(30., 30.)));

    this->add_agent(create_agent("Pippin", "Peasant", Point(5., 10.)));
    this->add_agent(create_agent("Merry", "Peasant", Point(0., 25.)));
    this->add_agent(create_agent("Zug", "Soldier", Point(20., 30.)));
    this->add_agent(create_agent("Bug", "Soldier", Point(15., 20.)));

}
