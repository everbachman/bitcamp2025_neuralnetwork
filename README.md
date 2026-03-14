# Neural Network from Scratch (Java)

I wrote this project in April 2025 for the UMD Bitcamp hackathon. I did not previously have any experience with machine learing or neural networks, this project was intended to implement weight initialization, forward propagation, and backpropagation entirely manually, without any use of AI tools for assistence in coding and without any external libraries.

Additionally, I built a simple CLI to demonstrate the functionality of the neural network by way of a two bit binary negation function.

## Potential Future Work 

Originally this project was going to be coneected to a Convolutional Neural Netrwork (CNN) but given the time limit of the hackathon this was not innitially possable and may be completed at a later date. I used trial and error to cofigure the current test dataset size for the example neural network. Larger networks and more complex datasets could be congured depending on potential intended use. 

If created the CNN would allow the networks to take in and interpret compressed images as binary data.

## Project Structure

* **`Network001.java`**: The network class, contains the logic for the neural layers, weight matrices, and the training loop.
* **`Main.java`**: A short script that trains the network to solve a bit-flipping logic problem and provides a CLI for testing/demonstration

## Key Features

* **Customizable Topology:** The neural networks can be configured to any sizes of inputs, outputs, and hidden layers
* **Manual Backpropagation:** Implements the chain rule from scratch to calculate gradients for weights and biases without external autodiff libraries
* **Xavier Initialization:** Implements weight initialization to prevent gradients from vanishing or exploding
* **Mini-Batch Gradient Descent:** Supports processing of training data in batches to improve convergence stability 

## Network Architecture

### Forward Pass

The network uses the Sigmoid activation function for non-linearity:


>$\sigma(x) = \frac{1}{1 + e^{-x}}$


`run()`: processes inputs through nested loops representing the weighted sums and activation triggers for each layer

### Weight Initialization

To ensure the variance of the outputs of each layer is equal to the variance of its inputs, the project utilizes Xavier initialization:

 >Limit = $\sqrt{\frac{6}{n_{in} + n_{out}}}&

### Backpropagation & Optimization

`fillGradient()`: the error is propagated backward through the network

* **Loss Gradient:** Calculated as the derivative of the error with respect to each weight.
* **Activation Derivative:** Uses the property of the Sigmoid derivative: $\sigma'(x) = \sigma(x) \cdot (1 - \sigma(x))$.
* **Weight Updates:** Weights are updated using a defined learning rate ($\eta$) to minimize the cost function.


## Performance Demonstration

The current demonstration trains a small network on a bit-flipping mapping ( `[0, 1] --> [1, 0]`). After training, the model achieves near-perfect classification on binary inputs, demonstrating the successful implementation of the custom backpropagation algorithm.


