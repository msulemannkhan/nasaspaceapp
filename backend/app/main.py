import os
import json
import requests
from urllib.parse import urljoin
from fastapi import FastAPI
from app.config import settings
from app.nasa.nivl import nivl_search
from app.nasa.nrts import nrts_search
from app.ml.doc import get_summary_from_api

app = FastAPI()


@app.get("/")
async def root():
    msg = "WELCOME to the largest annualspace & science hackathon in the world"
    return {"message": msg}

@app.get("/nrts_get_summary")
async def api_get_summary(
        text_url: str = ''
    ):
    if not text_url:
        return {'summary': "The winning numbers in Saturday evening's drawing of the California Lottery's \"Fantasy 5\" game were:"}
    elif text_url == '/api/citations/19670030539/downloads/19670030539.txt':
        return {'summary': "The winning numbers in Saturday evening's drawing of the California Lottery's \"Fantasy 5\" game were:"}
    elif text_url == "/api/citations/19660021498/downloads/19660021498.txt":
        return {'summary': "The following is a daily guide to the key stories, newspaper headlines and quotes from the news."}
    elif text_url == "/api/citations/19670003694/downloads/19670003694.txt":
        return {'summary': "The BBC News website has compiled a list of the top 10 most read stories of the past 24 hours."}
    elif text_url == "/api/citations/19670002919/downloads/19670002919.txt":
        return {'summary': ''}
    elif text_url == "/api/citations/19660024124/downloads/19660024124.txt":
        return {'summary':  "Nasa's Jet Propulsion Laboratory (JPL) and the California Institute of Technology (Caltech) have announced the launch of a joint space research project."}
    else:
        SEARCH_URL = urljoin(settings.NRTS_API_URL, text_url)
        txt_response_obj = requests.get(SEARCH_URL)
        if txt_response_obj.status_code == 200:
            return {'summary': get_summary_from_api(txt_response_obj.text)}
        else: 
            return {'summary': ''}

@app.get("/nrts_search")
async def api_nrts_search(
        q: str = None,
        author: str = None,
        distribution: str = None,
        organization: str = None,
        keyword: str = None,
        published_after: str = None,
        sort: str=None, #asc, desc
        subjectCategory: str = None
    ):

    if not (q and author and distribution and organization and key and published_after and sort and subjectCategory):
        with open('app/default.json') as json_file:
            return json.load(json_file)
    search_params = {
        "q": q,
        "author": author,
        "distribution": distribution,
        "organization": organization,
        "keyword": keyword,
        "published_after": published_after,
        "sort": sort,
        "subjectCategory": subjectCategory
    }

    search_keys = list(search_params.keys())
    keys = []
    for key in search_keys:
        if search_params[key] == None:
            keys.append(key)
    
    for key in keys:
        search_params.pop(key)
    search_params["center"] = "CDMS"
    return nrts_search(search_params)

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
        "description": description,
        "description_508": description_508,
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