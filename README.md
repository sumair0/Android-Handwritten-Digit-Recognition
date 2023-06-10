# Handwritten Digit Recognition Android App

This repository contains the code for an Android application that allows users to take pictures of handwritten digits and performs digit recognition using a deep learning model. The application is developed using Android Studio.

## Project Description

The goal of this project is to build an Android app that enables users to capture images of handwritten digits and recognize the digit present in the image. The application utilizes TensorFlow 2, a deep learning framework, and the Keras API for running the deep learning model.

The deep learning model implemented in this project is a Convolutional Neural Network (CNN) architecture with 10 convolutional layers (conv2d). The final output layer consists of 10 outputs, each representing the confidence of the corresponding digit output. The model was trained on a dataset of 42,000 images and tested on a dataset of 28,000 images, achieving a training accuracy of 99%.

## Repository Contents

- `android-app`: Contains the source code for the Android application developed using Android Studio.
- `server`: Contains the server-side code implemented using Flask framework in Python.
- `saved_model`: Contains the saved trained model weights.
- `uploads`: Contains the uploaded images stored in respective folders based on the predicted digit.

## Usage

1. Install the Android app on an Android device.
2. Launch the app and grant camera permission if requested.
3. Capture an image of a handwritten digit using the app.
4. The app will send the image to the server for digit recognition.
5. The server will resize the image to 28x28 pixels and use the trained model to predict the digit.
6. The predicted digit will be displayed on the server's terminal.
7. The image will be stored in the corresponding folder based on the predicted digit in the `uploads` directory.

## Dependencies

- Android Studio
- TensorFlow 2
- Keras
- Flask
- PIL
- matplotlib
- NumPy

## Installation and Setup

1. Clone this repository to your local machine.
2. Open the Android project in Android Studio and build the app.
3. Install the Flask and other required Python packages using pip: `pip install flask pillow matplotlib numpy tensorflow`.
4. Load the saved model weights from the `saved_model` directory.
5. Run the Flask server by executing `python server.py` in the `server` directory.

## Illustration

![Animated GIF](https://github.com/sumair0/Android-Handwritten-Digit-Recognition/blob/main/etc/demo.gif)

## Contributing

Pull requests are welcome. For major changes, please open an issue first to discuss what you would like to change.

## License

[MIT](LICENSE)


