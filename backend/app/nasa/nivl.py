import json
import requests
from urllib.parse import urljoin
from app.config import settings


def nivl_search(search_params: dict):
    SEARCH_URL = urljoin(
            settings.NASA_IMAGE_AND_VIDEO_LIBRARY_API_URL,
            '/search'
        )
    response = requests.get(
            SEARCH_URL,
            params=search_params
        )
    search_results = json.loads(response.text)
    limited_results = search_results["collection"]["items"][:10]
    search_results["collection"]["items"] = limited_results
    return search_results