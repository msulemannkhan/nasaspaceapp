# Important Links

- [NASA Technical Reports Server (NTRS) API](https://ntrs.nasa.gov/api/openapi/#/default/SearchController_postSearch)
- [NASA Technical Reports Server](https://ntrs.nasa.gov/search)
- [NASA STI Repository OpenAPI ](https://sti.nasa.gov/docs/STI_Open_API_Documentation_20210426.pdf)


# Developer Instructions:

1. activate conda environment
    
    `conda activate nasaapp`

2. Run uvicorn

    `uvicorn app.main:app --reload`

3. Run Docker
    
    `docker-compose --env-file=./.env up -d`

    `sudo docker stop $(sudo docker ps -a -q)`

    `sudo docker rm $(sudo docker ps -a -q)`

    `sudo docker rmi $(sudo docker images -a -q)`