from app.config import settings
from app.ml.utils import get_ml_model_key
import pandas as pd
import requests
import json


def get_summary_from_api(doc_text: str) -> str:
  # API_KEY = "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"
    API_KEY = get_ml_model_key()
    if API_KEY:
        headers = {"Authorization": f"Bearer {API_KEY}"}
        response = requests.post(
            settings.SUMMARY_MODEL_SERVER_URL, 
            {"inputs": doc_text}, 
            headers=headers
            )
        if response.status_code == 200:
            response = json.loads(response.text)
            doc_summary = response[0]["summary_text"]
            return doc_summary