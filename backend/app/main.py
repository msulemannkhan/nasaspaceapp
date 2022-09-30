from fastapi import FastAPI
from app.config import settings
from app.nasa.nivl import nivl_search
from app.nasa.nrts import nrts_search


app = FastAPI()


@app.get("/")
async def root():
    msg = "WELCOME to the largest annualspace & science hackathon in the world"
    return {"message": msg}

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