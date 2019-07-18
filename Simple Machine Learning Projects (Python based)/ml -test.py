from sklearn import linear_model, datasets
import matplotlib.pyplot as plt
import numpy as np


# CODE THAT YOU DON'T NEED TO WORRY ABOUT! ######################
dataset = datasets.load_diabetes()
dataset_X = dataset.data[:, np.newaxis, 2]
input_variables_values_training_datasets = dataset_X[:-20]
input_variables_values_test_datasets = dataset_X[-20:]
target_variables_values_training_datasets = dataset.target[:-20]
#################################################################

x_train = input_variables_values_training_datasets
y_train = target_variables_values_training_datasets
x_test = input_variables_values_test_datasets


#Creat linear regression object
linear = linear_model.LinearRegression()

# Train the model using the training sets
linear.fit(x_train, y_train)

# Make predictions using the testing set
predicted = linear.predict(x_test)

# Give it a score!
score = linear.score(x_train, y_train)

print("Here is the predicted values for the test dataset: " ,
      predicted)

print("Here is the score: ", score)

plt.scatter(x_train, y_train,  color='black')
plt.plot(x_test, predicted, color='blue', linewidth=3)

plt.xticks(())
plt.yticks(())

plt.show()


