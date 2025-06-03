#include "Dense.h"

Matrix Dense::get_weights() const {
    return weights_;
}
Matrix Dense::get_bias() const {
    return bias_;
}
ActivationFunc Dense::get_activation_func() const {
    return ActivationFunction_;
}

Matrix Dense::operator()(const Matrix &input) const {
    ActivationFunc activation_func = ActivationFunction_;
    return activation_func(weights_.dot(input) + bias_);
}
