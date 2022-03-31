import uvicorn
import pymysql
from fastapi import FastAPI
from routes import data, recommend

app = FastAPI()
app.include_router(data.router)
app.include_router(recommend.router)

if __name__ == "__main__":
    uvicorn.run("main:app", host="localhost", port=8000, reload=True)