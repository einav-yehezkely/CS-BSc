#include "Activation.h"

#include <cmath>

Matrix activation::relu(const Matrix& m) {
    Matrix result(m);
    for (int i = 0; i < result.get_rows()*result.get_cols(); ++i) {
        if (m[i] >= 0) {
            result[i] = m[i];
        }
        else {
            result[i] = 0;
        }
    }
    return result;
}

Matrix activation::softmax(const Matrix &m) {
    float sum = 0.0f;
    for (int i = 0; i < m.get_rows()*m.get_cols(); ++i) {
        sum += std::exp(m[i]);
    }
    Matrix result(m);
    for (int i = 0; i < result.get_rows()*result.get_cols(); ++i) {
        result[i] = std::exp(m[i]) / sum;
    }
    return result;
}

