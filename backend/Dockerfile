FROM tiangolo/uvicorn-gunicorn-fastapi:python3.8-slim

# set work directory
WORKDIR /app

# RUN apt-get update && apt-get upgrade -y && apt-get install -y git

# set env variables
ENV PYTHONDONTWRITEBYTECODE 1
ENV PYTHONUNBUFFERED 1


# install dependencies
COPY requirements.txt .
RUN pip install -r requirements.txt


# copy project
COPY . .
