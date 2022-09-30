import requests
import json
from urllib.parse import urljoin
from app.config import settings

def nrts_search(request_params: dict) -> dict:
    SEARCH_URL = urljoin(settings.NRTS_API_URL, "/api/citations/search")
    response = requests.get(SEARCH_URL, params=request_params)
    return json.loads(response.text)