#include "Matrix.h"

int Matrix::get_rows() const {
    return rows_;
}

int Matrix::get_cols() const {
    return cols_;
}

Matrix& Matrix::transpose() {
    auto *temp = new float[cols_ * rows_];
    for (int i = 0; i < rows_; ++i) {
        for (int j = 0; j < cols_; ++j) {
            temp[j * rows_ + i] = elems_[i * cols_ + j];
        }
    }

    std::swap(rows_, cols_); // Swap the dimensions
    delete[] elems_; // Delete the old elements
    elems_ = temp; // Assign the new elements

    return *this;
}