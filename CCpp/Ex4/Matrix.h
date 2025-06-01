// Matrix.h
#ifndef MATRIX_H
#define MATRIX_H
#include <stdexcept>


// You don't have to use the struct. Can help you with MlpNetwork.h
struct matrix_dims {
    int rows, cols;
};


// Insert Matrix class here...
class Matrix{
private:
    int rows_, cols_;
    float *elems_;
public:
    // constructors and destructor
    Matrix(int rows, int cols): rows_(rows), cols_(cols), elems_(nullptr) {
        if (rows < 1 || cols < 1) {
            throw std::invalid_argument("Invalid matrix dimensions");
        }
        elems_ = new float[rows * cols];
        for (int i = 0; i < rows * cols; i++) {
            elems_[i] = 0.0f;
        }
    }
    Matrix(): Matrix(1, 1) {}
    Matrix(const Matrix &m): rows_(m.rows_), cols_(m.cols_), elems_(new float[m.rows_ * m.cols_]) {
        for (int i = 0; i < rows_ * cols_; ++i) {
            elems_[i] = m.elems_[i];
        }
    }
    ~Matrix() {
        delete[] elems_;
    }
    // getters
    int get_rows() const;
    int get_cols() const;
    // functions
    Matrix& transpose();

};



#endif //MATRIX_H