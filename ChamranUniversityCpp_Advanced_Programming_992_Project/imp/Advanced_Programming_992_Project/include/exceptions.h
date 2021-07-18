//
// Created by root on 7/18/21.
//

#ifndef ADVANCEOOPCPP_EXCEPTIONS_H
#define ADVANCEOOPCPP_EXCEPTIONS_H
#include <stdexcept>
class IllegalType: public std::invalid_argument{
public:
    IllegalType(const std::string& reason):std::invalid_argument(reason){

    }
};
#endif //ADVANCEOOPCPP_EXCEPTIONS_H
