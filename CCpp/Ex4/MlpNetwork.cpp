#include "MlpNetwork.h"
#include "Activation.h"

digit MlpNetwork::operator()(const Matrix &input) const {
    Matrix output = input;
    for (int i = 0; i < MLP_SIZE; i++) {
        output = layers_[i](output);
    }
    digit result;
    result.value = output.argmax();
    result.probability = output[result.value];
    return result;

}