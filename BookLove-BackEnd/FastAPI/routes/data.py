from db import models
from db.database import SessionLocal, engine
from db import crud
from sqlalchemy.orm import Session
from fastapi import APIRouter, Depends, status
import json
import pandas
import os

dir = os.path.dirname(os.path.realpath(__file__)).replace('\\', '/') + '/'
models.Base.metadata.create_all(bind=engine)

router = APIRouter(prefix="/data")

# Dependency
def get_db():
    db = SessionLocal()
    try:
        yield db
    finally:
        db.close()

def store_categories_in_db(db):
    df = pandas.read_csv(dir + '../file/category_depth1.csv', encoding='cp949')
    json_data = df.to_json(orient='records')

    data = json.loads(json_data)

    for row in data:
        model = models.Category(**row)
        db.add(model)

    db.commit()

    return

@router.get('/books', status_code=status.HTTP_201_CREATED)
async def store_books_in_dir(db: Session = Depends(get_db)):
    db_book = crud.get_books(db)

    if db_book is None:
        return "fail"

    df = pandas.DataFrame([o.__dict__ for o in db_book])
    df.drop(['_sa_instance_state'], axis=1, inplace=True)

    df.to_csv(dir + '../file/db_books.csv', encoding='cp949', index=False)

    return "success"

@router.post('/books', status_code=status.HTTP_201_CREATED)
def store_books_in_db(db: Session = Depends(get_db)) -> None:
    store_categories_in_db(db)
    df = pandas.read_csv(dir + '../file/books.csv', encoding='cp949')
    df.drop_duplicates(subset=None, keep='first', inplace=False, ignore_index=False)

    json_data = df.to_json(orient='records')

    data = json.loads(json_data)

    for row in data:
        model = models.Book(**row)
        db.add(model)

    db.commit()

    return "success"