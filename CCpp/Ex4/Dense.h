// Dense.h
#ifndef DENSE_H
#define DENSE_H

#include "Activation.h"

// Insert Dense class here...
class Dense {
private:
    Matrix weights_;
    Matrix bias_;
    ActivationFunc activation_func_;
public:
    // constructor
    Dense(const Matrix &weights, const Matrix &bias, ActivationFunc activation_func_):
        weights_(weights), bias_(bias), activation_func_(activation_func_) {}
    // getters
    Matrix get_weights() const;
    Matrix get_bias() const;
    ActivationFunc get_activation() const;
    // operator
    Matrix operator()(const Matrix &input) const;

};









#endif //DENSE_H