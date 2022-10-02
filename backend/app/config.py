import os
from dotenv import load_dotenv
from pydantic import BaseSettings


class Settings(BaseSettings):
    ENV: str = 'prod'  #prod or dev
    NASA_IMAGE_AND_VIDEO_LIBRARY_API_URL: str = ''
    NRTS_API_URL: str = ''
    IMAGE_DATA_DIR: str = ''
    SERVER_URL: str = ''

    ML_MODEL_KEY_1: str = ''
    ML_MODEL_KEY_2: str = ''
    ML_MODEL_KEY_3: str = ''
    ML_MODEL_KEY_4: str = ''
    ML_MODEL_KEY_5: str = ''
    ML_MODEL_KEY_6: str = ''
    ML_MODEL_KEY_7: str = ''
    ML_MODEL_KEY_8: str = ''

    SUMMARY_MODEL_SERVER_URL: str = ''

    IMAGE_FILTER_1_URL: str = ''
    IMAGE_FILTER_1_STATUS_URL: str = ''

    class Config:
        # All settings can be replaced with the contents in the '.env' file
        env_file: str = ".env"
        env_file_encoding = "utf-8"


load_dotenv()
settings = Settings()