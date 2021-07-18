cmake_minimum_required(VERSION 3.17)
project(AdvanceOOPCPP)


set(CMAKE_CXX_STANDARD 20)

add_executable(AdvanceOOPCPP

        include/Geometry.h lib/Geometry.cpp
        include/Moving_object.h lib/Moving_object.cpp
        include/Agent_factory.h lib/Agent_factory.cpp
        include/exceptions.h
        include/Sim_object.h
        include/Agent.h
        include/Soldier.h lib/Soldier.cpp
        include/Model.h lib/Model.cpp
        mainTest.cpp )