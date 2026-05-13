import itertools
import os
import numpy as np
import matplotlib.pyplot as plt
import seaborn as sns
from sklearn.svm import SVC
from sklearn.tree import DecisionTreeClassifier
from sklearn.neighbors import KNeighborsClassifier
from sklearn.datasets import make_moons
from sklearn.datasets import make_circles
from sklearn.model_selection import train_test_split
from sklearn.metrics import accuracy_score


### HELPER FUNCTIONS ###
# Add here any helper functions which you think will be useful
def make_two_gaussians(n_samples=200, random_state=42):
    np.random.seed(random_state)

    mean1 = [-1, -1]
    mean2 = [1, 1]
    cov = [[0.5, 0.2], [0.2, 0.5]]

    X1 = np.random.multivariate_normal(mean1, cov, n_samples // 2)
    X2 = np.random.multivariate_normal(mean2, cov, n_samples // 2)

    X = np.vstack([X1, X2])
    y = np.array([0] * (n_samples // 2) + [1] * (n_samples // 2))

    return X, y


def plot_decision_boundary(
    model, X_train, X_test, y_train, y_test, dataset_name, model_name, output_dir
):
    X_all = np.vstack([X_train, X_test])
    y_all = np.hstack([y_train, y_test])

    x_min, x_max = X_all[:, 0].min() - 0.5, X_all[:, 0].max() + 0.5
    y_min, y_max = X_all[:, 1].min() - 0.5, X_all[:, 1].max() + 0.5

    xx, yy = np.meshgrid(np.linspace(x_min, x_max, 300), np.linspace(y_min, y_max, 300))

    grid = np.c_[xx.ravel(), yy.ravel()]
    Z = model.predict(grid).reshape(xx.shape)

    y_pred = model.predict(X_test)
    acc = accuracy_score(y_test, y_pred)

    plt.figure(figsize=(7, 6))

    plt.contourf(xx, yy, Z, alpha=0.25)

    plt.scatter(X_all[:, 0], X_all[:, 1], c=y_all, edgecolors="k", s=35)

    plt.title(f"{dataset_name} - {model_name}, Accuracy = {acc:.2f}")
    plt.xlabel("x1")
    plt.ylabel("x2")
    plt.tight_layout()

    filename = f"{dataset_name}_{model_name}.png".replace(" ", "_")
    plt.savefig(os.path.join(output_dir, filename))
    plt.close()


### Exercise Solution ###


if __name__ == "__main__":
    output_dir = "classification_plots"
    os.makedirs(output_dir, exist_ok=True)
    # Generate 3 different datasets - Moons, Circles and Two-Gaussians (80% train, 20% test)
    X_moons, y_moons = make_moons(n_samples=200, noise=0.2, random_state=42)
    X_circles, y_circles = make_circles(n_samples=200, noise=0.1, random_state=42)
    X_gauss, y_gauss = make_two_gaussians(n_samples=200, random_state=42)

    datasets = {
        "Moons": (X_moons, y_moons),
        "Circles": (X_circles, y_circles),
        "Two_Gaussians": (X_gauss, y_gauss),
    }

    # For each dataset, train each one of the following 3 models: SVM with
    # lambda = 5, Decision Tree with depth = 7, KNN with k = 5
    classifiers = {
        "SVM_lambda_5": SVC(C=1 / 5, kernel="rbf"),
        "Decision_Tree_depth_7": DecisionTreeClassifier(max_depth=7, random_state=42),
        "KNN_k_5": KNeighborsClassifier(n_neighbors=5),
    }

    # Generate decision boundary plots
    for dataset_name, (X, y) in datasets.items():
        X_train, X_test, y_train, y_test = train_test_split(
            X, y, train_size=0.8, random_state=42
        )

        for model_name, model in classifiers.items():
            model.fit(X_train, y_train)

            plot_decision_boundary(
                model,
                X_train,
                X_test,
                y_train,
                y_test,
                dataset_name,
                model_name,
                output_dir,
            )
