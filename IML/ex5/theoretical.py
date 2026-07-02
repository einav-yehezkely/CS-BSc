import numpy as np
import matplotlib.pyplot as plt

# Original vectors
x1 = np.array([1, 2])
x2 = np.array([-1, 1])
x3 = np.array([1, 1])

X = np.array([x1, x2, x3])

# Mean
x_bar = X.mean(axis=0)

# Principal eigenvector (normalized)
v = np.array([0.95709203, 0.28978415])

# Reconstruction
X_hat = []

for x in X:
    z = x - x_bar
    projection = v * np.dot(v, z)
    x_hat = projection + x_bar
    X_hat.append(x_hat)

X_hat = np.array(X_hat)

# Plot
plt.figure(figsize=(7,7))

# Original vectors
colors = ['r', 'g', 'b']
labels = ['x1', 'x2', 'x3']

for i in range(3):
    plt.quiver(0, 0,
               X[i,0], X[i,1],
               angles='xy',
               scale_units='xy',
               scale=1,
               color=colors[i],
               label=labels[i])

# Reconstructed vectors
# Reconstructed vectors
for i in range(3):
    plt.quiver(
        0, 0,
        X_hat[i,0], X_hat[i,1],
        angles='xy',
        scale_units='xy',
        scale=1,
        color=colors[i],
        alpha=0.45,
        label='Reconstructed version' if i == 0 else ""
    )

# Shifted span(v)+mean
t = np.linspace(-2, 2, 100)
line = x_bar + np.outer(t, v)
plt.plot(line[:,0], line[:,1], 'k', linewidth=2,
         label='span(v)+mean')

# Reconstruction error (dashed)
for i in range(3):
    plt.plot([X[i,0], X_hat[i,0]],
             [X[i,1], X_hat[i,1]],
             '--',
             color=colors[i])

# Mean point
plt.scatter(x_bar[0], x_bar[1], c='black', s=70, label='mean')

plt.xlim(-2,2)
plt.ylim(-2,2)
plt.xticks(np.arange(-2,3,1))
plt.yticks(np.arange(-2,3,1))
plt.grid(True)
plt.gca().set_aspect('equal')
plt.legend()
plt.title("PCA Reconstruction")
plt.show()