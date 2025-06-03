#include "Dense.h"

Matrix Dense::get_weights() const {
    return weights_;
}
Matrix Dense::get_bias() const {
    return bias_;
}
ActivationFunc Dense::get_activation() const {
    return activation_func_;
}

Matrix Dense::operator()(const Matrix &input) const {
    ActivationFunc activation_func = activation_func_;
    return activation_func(weights_.dot(input) + bias_);
}
