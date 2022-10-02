from app.config import settings
import pandas as pd
import requests
import json
import cv2
import urllib
import io
import numpy as np
import boto3
import io
import uuid


def url_to_array(img_url= 'https://live.staticflickr.com/4072/4545191995_acae7e27ca_b.jpg'):
    with urllib.request.urlopen(img_url) as req:
        buffer_img = req.read()
        img_binary = io.BytesIO(bytearray(buffer_img))
        arr = np.asarray(bytearray(buffer_img), dtype=np.uint8)
        return cv2.imdecode(arr, -1) 


def images_style(url , style):
    #read an image from url
    img = url_to_array(url)

    new_image_name = str(uuid.uuid4()) + ".png"
    image_path=settings.IMAGE_DATA_DIR+"/"+new_image_name
    return_image_path = settings.SERVER_URL + "/images/" + new_image_name
    # settings.DATA_DIR
    if(style == 'oil_paint'):
        #converted to oil painting and save image
        oil_painitng = cv2.xphoto.oilPainting(img, 7, 1)
        cv2.imwrite(image_path, oil_painitng)

        return return_image_path
    if(style == 'stylized'):
        #converted to oil painting and save image
        stylized_img = cv2.stylization(img, sigma_s=60, sigma_r=0.6)
        cv2.imwrite(image_path, stylized_img)
        return return_image_path
    if(style == 'pencil_sketch'):
        #convert to pencil sketch and save image
        dst_gray, dst_color  = cv2.pencilSketch(img, sigma_s=60, sigma_r=0.07, shade_factor=0.05)
        cv2.imwrite(image_path, dst_gray)
        return return_image_path


def apply_image_filter(data):
    response = requests.post(settings.IMAGE_FILTER_1_URL, json=data)
    return json.loads(response.text)

def get_filter_1_status(data):
    response = requests.post(settings.IMAGE_FILTER_1_STATUS_URL, json=data)
    return json.loads(response.text)



