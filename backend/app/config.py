import os
from dotenv import load_dotenv
from pydantic import BaseSettings


class Settings(BaseSettings):
    ENV: str = 'prod'  #prod or dev
    NASA_IMAGE_AND_VIDEO_LIBRARY_API_URL: str = ''
    NRTS_API_URL: str = ''

    class Config:
        # All settings can be replaced with the contents in the '.env' file
        env_file: str = ".env"
        env_file_encoding = "utf-8"


load_dotenv()
settings = Settings()