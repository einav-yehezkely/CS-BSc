#include "Matrix.h"
#include <iostream>
#include <cmath>

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

Matrix& Matrix::vectorize() {
    rows_ = cols_*rows_;
    cols_ = 1;
    return *this;
}

void Matrix::plain_print() {
    for (int i = 0; i < rows_; ++i) {
        for (int j = 0; j < cols_; ++j) {
            std::cout << elems_[i * cols_ + j] << " ";
        }
        std::cout << std::endl;
    }
}

Matrix& Matrix::dot(const Matrix& m) const {
    if (rows_ != m.rows_ || cols_ != m.cols_) {
        throw std::invalid_argument("Invalid matrix dimensions");
    }
    Matrix result(rows_, cols_);
    for (int i = 0; i < rows_*cols_; ++i) {
        result[i] = elems_[i] * m[i];
    }
    return result;
}

float Matrix::norm() const {
    float sum = 0.0f;
    for (int i = 0; i < rows_*cols_; ++i) {
        sum += elems_[i] * elems_[i];
    }
    return std::sqrt(sum);
}

Matrix Matrix::rref() const {
    Matrix result(*this);
    int cur_col = 0;
    for (int row = 0; row < result.rows_; ++row) {
        if (cur_col >= result.cols_) {
            break;
        }

        // Find the first non-zero element in the current row
        int i = row;
        while (result(i, cur_col) == 0.0f) {
            ++i;
            if (i >= result.rows_) {
                i = row;
                cur_col++;
                if (cur_col >= result.cols_) {
                    return result;
                }
            }

        }

        // Swap the current row with the first non-zero element
        for (int j = 0; j < result.cols_; ++j) {
            std::swap(result(i,j),result(row,j));
        }

        // Normalize the current row
        float pivot = result(row, cur_col);
        for (int j = 0; j < result.cols_; ++j) {
            result(row, j) /= pivot;
        }

        // Eliminate the current column in all other rows
        for (int i = 0; i < result.rows_; ++i) {
            if (i!=row) {
                float value = result(i, cur_col);
                for (int j = 0; j < result.cols_; ++j) {
                    result(i, j) -= value * result(row, j);
                }
            }
        }
        cur_col++;
    }
    return result;
}

int Matrix::argmax() const {
    int max_idx = 0;
    float max_value = elems_[0];
    for (int i = 1; i < rows_*cols_; ++i) {
        if (elems_[i] > max_value) {
            max_value = elems_[i];
            max_idx = i;
        }
    }
    return max_idx;
}

float Matrix::sum() const {
    float sum = 0.0f;
    for (int i = 0; i < rows_*cols_; ++i) {
        sum += elems_[i];
    }
    return sum;
}

Matrix& Matrix::operator+=(const Matrix& m) {
    if (rows_ != m.rows_ || cols_ != m.cols_) {
        throw std::invalid_argument("Invalid matrix dimensions");
    }
    for (int i = 0; i < rows_*cols_; ++i) {
        elems_[i] += m[i];
    }
    return *this;
}

Matrix Matrix::operator+(const Matrix& m) const {
    if (rows_ != m.rows_ || cols_ != m.cols_) {
        throw std::invalid_argument("Invalid matrix dimensions");
    }
    Matrix result(*this);
    result += m;
    return result;
}

Matrix& Matrix::operator=(const Matrix& m) {
    if (this != &m) {
        delete[] elems_;
        rows_ = m.rows_;
        cols_ = m.cols_;
        elems_ = new float[rows_ * cols_];
        for (int i = 0; i < rows_*cols_; ++i) {
            elems_[i] = m[i];
        }
    }
    return *this;
}

Matrix Matrix::operator*(const Matrix &m) const {
    if (cols_ != m.rows_) {
        throw std::invalid_argument("Invalid matrix dimensions");
    }
    Matrix result(rows_, m.cols_);
    for (int i = 0; i < rows_; ++i) {
        for (int j = 0; j < m.cols_; ++j) {
            float sum = 0.0f;
            for (int k = 0; k<cols_; ++k) {
                sum += (*this)(i,k) * m(k,j);
            }
            result(i,j) = sum;
        }
    }
    return result;
}

Matrix Matrix::operator*(float c) const {
    Matrix result(*this);
    for (int i = 0; i < rows_*cols_; ++i) {
        result[i] *= c;
    }
    return result;
}

Matrix operator*(float scalar, const Matrix& m) {
    return m * scalar;
}

float& Matrix::operator()(int row, int col) {
    if (row < 0 || row >= rows_ || col < 0 || col >= cols_) {
        throw std::out_of_range("Invalid matrix index");
    }
    return elems_[row * cols_ + col];
}

const float& Matrix::operator()(int row, int col) const {
    if (row < 0 || row >= rows_ || col < 0 || col >= cols_) {
        throw std::out_of_range("Invalid matrix index");
    }
    return elems_[row * cols_ + col];
}

float& Matrix::operator[](int index) {
    if (index < 0 || index >= rows_ * cols_) {
        throw std::out_of_range("Matrix index out of bounds");
    }
    return elems_[index];
}

const float& Matrix::operator[](int index) const {
    if (index < 0 || index >= rows_ * cols_) {
        throw std::out_of_range("Matrix index out of bounds");
    }
    return elems_[index];
}

std::ostream& operator<<(std::ostream& os, const Matrix& m) {
    for (int i = 0; i < m.rows_; ++i) {
        for (int j = 0; j < m.cols_; ++j) {
            if (m(i,j) > 0.0f) {
                os << "**";
            }
            else {
                os << "  ";
            }
        }
        os << std::endl;
    }
    return os;
}

std::istream& operator>>(std::istream& is, Matrix& m) {
    for (int i = 0; i < m.rows_ * m.cols_; ++i) {
        if (!(is >> m.elems_[i])) {
            throw std::runtime_error("Input stream ended too early");
        }
    }
    return is;
}
