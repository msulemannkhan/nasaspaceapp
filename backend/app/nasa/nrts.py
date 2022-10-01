import requests
import json
from urllib.parse import urljoin
from app.config import settings
from app.ml.doc import get_summary_from_api

def nrts_search(request_params: dict) -> dict:
    
    SEARCH_URL = urljoin(settings.NRTS_API_URL, "/api/citations/search")
    response = requests.get(SEARCH_URL, params=request_params)
    response_obj = json.loads(response.text)
    del response_obj["stats"]
    del response_obj["aggregations"]
    new_response = []
    i = 0
    while i < len(response_obj["results"]):
        # try:
            item = response_obj["results"][i]
            if len(item["downloads"]) > 0:
                print(item["downloads"])
                if item["downloads"][0]["links"].get("fulltext", None):
                    del item["_meta"]
                    del item["copyright"]
                    del item["exportControl"]
                    del item["otherReportNumbers"]
                    new_response.append(item)
                    if len(new_response) >= 5:
                        break
            i += 1
        # except:
        #     print("here")
        #     break
    return_obj = {}
    try:
        if len(new_response) >= 5: 
            return_obj = {"results": new_response}
        elif len(new_response) > 0:
            return_obj = {"results": new_response.extend(response_obj["results"][:5 - len(new_response)])}
        else:
            return_obj = {"results": response_obj["results"][:5]}
    except:
        return_obj =  {"results": response_obj["results"][:5]}
    
    return return_obj