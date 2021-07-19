cmake_minimum_required(VERSION 3.17)
project(AdvanceOOPCPP)


set(CMAKE_CXX_STANDARD 20)

add_executable(AdvanceOOPCPP

        include/Geometry.h lib/Geometry.cpp
        include/Moving_object.h lib/Moving_object.cpp
        include/Agent_factory.h lib/Agent_factory.cpp
        include/Sim_object.h
        include/Agent.h
        include/Soldier.h lib/Soldier.cpp
        include/Model.h lib/Model.cpp
        include/Structure.h
        include/Structure_factory.h lib/Structure_factory.cpp
                                    lib/Controller.cpp
                                    lib/View.cpp
        include/Farm.h lib/Farm.cpp
        include/Town_Hall.h lib/Town_Hall.cpp
        mainTest.cpp lib/Peasant.cpp include/Peasant.h)