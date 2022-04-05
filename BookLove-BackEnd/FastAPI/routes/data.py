from db import models
from db.database import SessionLocal, engine
from db import crud
from sqlalchemy.orm import Session
from fastapi import APIRouter, Depends, status
import parse_api
import json
import pandas
import os

dir = os.path.dirname(os.path.realpath(__file__)).replace('\\', '/') + '/'
models.Base.metadata.create_all(bind=engine)


async def store_categories_in_db(db):
    db_cate = crud.get_categories(db)

    if not len(db_cate) == 0:
        return

    df = pandas.read_csv(dir + '../file/category_depth1.csv', encoding='cp949')
    json_data = df.to_json(orient='records')

    data = json.loads(json_data)

    for row in data:
        model = models.Category(**row)
        db.add(model)

    db.commit()

    return

async def store_books_in_dir(db):
    db_book = crud.get_books(db)

    if len(db_book) == 0:
        await store_books_in_db(db)
        db_book = crud.get_books(db)

    df = pandas.DataFrame([o.__dict__ for o in db_book])
    df.drop(['_sa_instance_state'], axis=1, inplace=True)

    df.to_csv(dir + '../file/db_books.csv', encoding='cp949', index=False)

    return

async def store_books_in_db(db):
    await store_categories_in_db(db)

    if not os.path.isfile(dir + '../file/books.csv'):
        await parse_api.save_book()
        parse_api.process_book()

    df = pandas.read_csv(dir + '../file/books.csv', encoding='cp949')
    df.drop_duplicates(subset=None, keep='first', inplace=False, ignore_index=False)

    json_data = df.to_json(orient='records')

    data = json.loads(json_data)

    for row in data:
        model = models.Book(**row)
        db.add(model)

    db.commit()

    return

async def get_click_log_by_user_id(db, user_id):
    db_click_log = crud.get_click_log_by_user_id(db, user_id)

    if len(db_click_log) == 0:
        return

    df = pandas.DataFrame([o.__dict__ for o in db_click_log])
    df.drop(['_sa_instance_state'], axis=1, inplace=True)

    return df