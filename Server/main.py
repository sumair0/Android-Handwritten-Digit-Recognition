import os

from flask import Flask
from flask import request
from werkzeug.utils import secure_filename

from PIL import Image
import matplotlib.pyplot as plt
import numpy as np

import tensorflow as tf

from imagePrepare import imageprepare

app = Flask(__name__)

model = tf.keras.models.load_model("saved_model")

def checkNCreate(path):
    if not os.path.exists(path):
        os.mkdir(path)

def intensify(image, threshold):

    image = image.copy()

    for i in range(len(image)):
        for j in range(len(image[0])):
            if image[i][j] > threshold:
                image[i][j] = 255.0
            else:
                image[i][j] = 0.0

    return image


# @app.route("/upload", methods=["POST"])
# def upload():
#     file = request.files['uploadFile']
#     filename = secure_filename(file.filename) + ".jpg"
#     category = request.form['category']
#     checkNCreate('uploads')
#     save_path = os.path.join('uploads', category)
#     checkNCreate(save_path)
#     file.save(os.path.join(save_path, filename))
#
#     return '', 204

@app.route("/upload", methods=["POST"])
def upload():
    file = request.files['uploadFile']
    filename = secure_filename(file.filename) + ".jpg"
    checkNCreate('uploads')

    img = Image.open(file)
    greyscale = img.convert("L")
    greyscale_resized = greyscale.resize((28, 28), Image.Resampling.LANCZOS)
    arr = np.asarray(greyscale_resized)
    arr = arr / 255.0

    arr = arr[np.newaxis,:,:,np.newaxis]

    results = model.predict(arr)
    digit = np.argmax(results[0])
    print(f'The digit is {digit}')

    save_path = os.path.join('uploads', str(digit))
    checkNCreate(save_path)

    file.seek(0)

    file.save(os.path.join(save_path, filename))


    return '', 204


@app.route("/")
def test():
    return "<h1>Server Live!</h1>"


if __name__ == "__main__":
    app.run("0.0.0.0", 5000)
