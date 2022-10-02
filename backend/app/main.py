import os
import json
import requests
from urllib.parse import urljoin
from fastapi import FastAPI
from fastapi.staticfiles import StaticFiles
from app.config import settings
from app.nasa.nivl import nivl_search
from app.nasa.nrts import nrts_search
from app.ml.doc import get_summary_from_api
from app.ml.img import apply_image_filter, get_filter_1_status
from app.ml.img import images_style
from app.filters import get_filter_details


app = FastAPI()
app.mount("/images", StaticFiles(directory='data/images'), name="images")

@app.get("/")
async def root():
    msg = "WELCOME to the largest annualspace & science hackathon in the world"
    return {"message": msg}

@app.get('/get_filters')
async def api_get_img_filters():
    filters_dic = get_filter_details()
    filters_lst = []
    for key in filters_dic:
        filters_lst.append(filters_dic[key])
    return filters_lst

@app.get("/apply_img_filter")
async def api_apply_img_filter_1(filter_name: str = 'oil_paint', image_url: str =''):
    img_filters = get_filter_details()
    default_image = "https://cdni.iconscout.com/illustration/premium/thumb/sorry-item-not-found-3328225-2809510.png"
    if filter_name in img_filters and image_url:
        try:
            img_after_filter = img_filters[filter_name]["display_image"]
            img_after_filter = images_style(style=filter_name, url=image_url)
            return {"image_url": img_after_filter}
        except Exception as e:
            print(e)
            return {"image_url": default_image}
    else:
        # return image not found (default image)
        return {"image_url": default_image}
        
@app.get("/nivl_search")
async def api_nivl_search(
        q: str = None,
        center: str = None,
        description: str = None,
        description_508: str = None,
        keywords: str = None,
        location: str = None,
        media_type: str = 'image',
        nasa_id: str = None,
        page: int = None,
        photographer: str = None,
        secondary_creator: str = None,
        title: str = None,
        year_start: str = None,
        year_end: str = None
    ):
    search_params = {
        "q": q,
        "center": center,
        "keywords": keywords,
        "location": location,
        "media_type": media_type,
        "nasa_id": nasa_id,
        "page": page,
        "photographer": photographer,
        "secondary_creator": secondary_creator,
        "title": title,
        "year_start": year_start,
        "year_end": year_end
    }
    search_keys = list(search_params.keys())
    keys = []
    for key in search_keys:
        if search_params[key] == None:
            keys.append(key)
    
    for key in keys:
        search_params.pop(key)

    return nivl_search(search_params)
