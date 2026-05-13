import pandas as pd
import matplotlib.pyplot as plt
from sklearn.model_selection import train_test_split

from polynomial_fitting import PolynomialFitting


def load_data(filename: str) -> pd.DataFrame:
    """
    Load city daily temperature dataset and preprocess data.
    Parameters
    ----------
    filename: str
        Path to city temperature dataset

    Returns
    -------
    Design matrix and response vector (Temp)
    """
    # Load csv and parse Date column as datetime
    df = pd.read_csv(filename, parse_dates=["Date"])

    # Remove invalid temperatures.
    # In this dataset, invalid/missing temperatures often appear as -99.
    df = df[df["Temp"] > -10 & (df["Temp"] < 50)]

    # Remove rows with missing important values
    df = df.dropna(subset=["Date", "Temp", "Country"])

    # Add day of year column
    df["DayOfYear"] = df["Date"].dt.dayofyear

    return df


if __name__ == "__main__":
    # Question 2 - Load and preprocessing of city temperature dataset
    df = load_data("code/Regression/city_temperature.csv")

    # Question 3 - Exploring data for specific country
    df_israel = df[df["Country"] == "Israel"]
    # Add Year and Month columns
    df_israel["Year"] = df_israel["Date"].dt.year
    df_israel["Month"] = df_israel["Date"].dt.month

    plt.figure(figsize=(10, 6))

    for year in sorted(df_israel["Year"].unique()):
        year_df = df_israel[df_israel["Year"] == year]

        plt.scatter(
            year_df["DayOfYear"], year_df["Temp"], s=10, alpha=0.6, label=str(year)
        )

    plt.xlabel("Day of Year")
    plt.ylabel("Temperature")
    plt.title("Daily Temperature in Israel as Function of Day of Year")
    plt.legend(title="Year")
    plt.grid(True)
    plt.tight_layout()
    plt.savefig("israel_temperature_by_dayofyear.png")
    plt.show()

    monthly_std = df_israel.groupby("Month")["Temp"].std()

    plt.figure(figsize=(10, 6))

    plt.bar(monthly_std.index, monthly_std.values)

    plt.xlabel("Month")
    plt.ylabel("Standard Deviation of Temperature")
    plt.title("Temperature Standard Deviation per Month in Israel")
    plt.xticks(range(1, 13))
    plt.grid(axis="y")
    plt.tight_layout()
    plt.savefig("israel_temperature_std_by_month.png")
    plt.show()

    # Question 4 - Fitting model for different values of `k`
    # Use only Israel samples
    X = df_israel["DayOfYear"].to_numpy()
    y = df_israel["Temp"].to_numpy()

    # Split into train and test
    X_train, X_test, y_train, y_test = train_test_split(
        X, y, test_size=0.25, random_state=42
    )

    degrees = range(1, 11)
    losses = []

    for k in degrees:
        # Create polynomial model
        model = PolynomialFitting(k)

        # Fit model
        model.fit(X_train, y_train)

        # Evaluate on test set
        loss = model.loss(X_test, y_test)

        # Round to 2 decimal places
        loss = round(loss, 2)

        losses.append(loss)

        print(f"k={k}: test loss={loss}")

    # Bar plot
    plt.figure(figsize=(10, 6))

    plt.bar(degrees, losses)

    plt.xlabel("Polynomial Degree (k)")
    plt.ylabel("Test Loss")
    plt.title("Polynomial Fitting Test Loss by Degree")
    plt.xticks(degrees)
    plt.grid(axis="y")

    plt.tight_layout()
    plt.savefig("polynomial_test_loss.png")
    plt.show()
