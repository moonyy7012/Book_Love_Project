import os
import pandas
import csv
import joblib
from routes import data
from typing import List
from db import schemas
from db.database import SessionLocal, engine
from sqlalchemy.orm import Session
from fastapi import APIRouter, Depends
from sklearn.feature_extraction.text import TfidfVectorizer
from sklearn.metrics.pairwise import cosine_similarity

dir = os.path.dirname(os.path.realpath(__file__)).replace('\\', '/') + '/'

router = APIRouter(prefix="/recommend")

# Dependency
def get_db():
    db = SessionLocal()
    try:
        yield db
    finally:
        db.close()

@router.get("/book/{book_id}", response_model=List[schemas.BookBase])
async def get_book_recommend(book_id: int, db: Session = Depends(get_db)) -> None:
    if not os.path.isfile(dir + '../file/db_books.csv'):
        await data.store_books_in_dir(db)

    df = pandas.read_csv(dir + '../file/db_books.csv', encoding='cp949')

    if not os.path.isfile(dir + '../file/tfidf_matrix_des.pkl'):
        await save_tfidf_matrix()

    tfidf_matrix_des = joblib.load(dir + '../file/tfidf_matrix_des.pkl')
    tfidf_matrix_cate = joblib.load(dir + '../file/tfidf_matrix_cate.pkl')
    tfidf_matrix_title = joblib.load(dir + '../file/tfidf_matrix_title.pkl')

    idx = [df.index[df['book_id'] == book_id][0]]

    cosine_matrix_des = cosine_similarity(tfidf_matrix_des[idx], tfidf_matrix_des)
    cosine_matrix_cate = cosine_similarity(tfidf_matrix_cate[idx], tfidf_matrix_cate)
    cosine_matrix_title = cosine_similarity(tfidf_matrix_title[idx], tfidf_matrix_title)

    sim_scores_des = [(i, c) for i, c in enumerate(cosine_matrix_des[0])]
    sim_scores_cate = [(i, c) for i, c in enumerate(cosine_matrix_cate[0])]
    sim_scores_title = [(i, c) for i, c in enumerate(cosine_matrix_title[0])]

    sim_scores_all = [(i, sim_scores_des[i][1] * 0.9 + sim_scores_cate[i][1] * 0.05 + sim_scores_title[i][1] * 0.05)
                      for i in range(len(sim_scores_des))]
    sim_scores_all = sorted(sim_scores_all, key=lambda x: x[1], reverse=True)
    sim_scores_all = [(i, score) for i, score in sim_scores_all[1:11]]

    recommend_books = []

    for (key, score) in sim_scores_all:
        recommend_book = schemas.BookDescBase(book_id=int(df.loc[key]['book_id']),
                                              title=df.loc[key]['title'],
                                              cover=df.loc[key]['cover'],
                                              description=df.loc[key]['description'])
        recommend_books.append(recommend_book)

    return recommend_books

@router.get("/books/{user_id}", response_model=List[schemas.BookDescBase])
async def get_books_recommend(user_id: int, db: Session = Depends(get_db)) -> None:
    if not os.path.isfile(dir + '../file/db_books.csv'):
        await data.store_books_in_dir(db)

    df_click_log = await data.get_click_log_by_user_id(db, user_id)
    df_book = pandas.read_csv(dir + '../file/db_books.csv', encoding='cp949')

    df_click_log = df_click_log.sort_values(by='update_date', ascending=False)

    df = pandas.merge(df_click_log, df_book, on="book_id")

    recent_title = df['title'][0:10].str.cat(sep=' ')
    recent_desc = df['description'][0:10].str.cat(sep=' ')
    recent_cate = df['category_name'][0:10].str.cat(sep=' ')

    df_book = pandas.concat([df_book, df_click_log])
    df_book.drop_duplicates(['book_id'], keep=False, inplace=True)

    df_book = df_book.append({'title': recent_title, 'description': recent_desc, 'category_name': recent_cate},
                             ignore_index=True)

    idx = len(df_book.index) - 1

    return recommend_books(idx, df_book)

def recommend_books(idx, df):
    corpus_des = df['description'].values.astype('U')
    corpus_cate = df['category_name'].values.astype('U')
    corpus_title = df['title'].values.astype('U')

    tfidfv = TfidfVectorizer(stop_words='english')
    tfidf_matrix_des = tfidfv.fit_transform(corpus_des)
    tfidf_matrix_cate = tfidfv.fit_transform(corpus_cate)
    tfidf_matrix_title = tfidfv.fit_transform(corpus_title)

    cosine_matrix_des = cosine_similarity(tfidf_matrix_des[idx], tfidf_matrix_des)
    cosine_matrix_cate = cosine_similarity(tfidf_matrix_cate[idx], tfidf_matrix_cate)
    cosine_matrix_title = cosine_similarity(tfidf_matrix_title[idx], tfidf_matrix_title)

    sim_scores_des = [(i, c) for i, c in enumerate(cosine_matrix_des[0])]
    sim_scores_cate = [(i, c) for i, c in enumerate(cosine_matrix_cate[0])]
    sim_scores_title = [(i, c) for i, c in enumerate(cosine_matrix_title[0])]

    sim_scores_all = [(i, sim_scores_des[i][1] * 0.9 + sim_scores_cate[i][1] * 0.05 + sim_scores_title[i][1] * 0.05)
                      for i in range(len(sim_scores_des))]
    sim_scores_all = sorted(sim_scores_all, key=lambda x: x[1], reverse=True)
    sim_scores_all = [(i, score) for i, score in sim_scores_all[1:11]]

    recommend_books = []

    for (key, score) in sim_scores_all:
        recommend_book = schemas.BookDescBase(book_id=int(df.loc[key]['book_id']),
                                              title=df.loc[key]['title'],
                                              cover=df.loc[key]['cover'],
                                              description=df.loc[key]['description'])
        recommend_books.append(recommend_book)

    return recommend_books

# def recommend_books(idx, df):
#     corpus_des = df['description'].values.astype('U')
#     corpus_cate = df['category_name'].values.astype('U')
#     corpus_title = df['title'].values.astype('U')
#
#     tfidfv = TfidfVectorizer(stop_words='english')
#     tfidf_matrix_des = tfidfv.fit_transform(corpus_des)
#     tfidf_matrix_cate = tfidfv.fit_transform(corpus_cate)
#     tfidf_matrix_title = tfidfv.fit_transform(corpus_title)
#
#     cosine_matrix_des = cosine_similarity(tfidf_matrix_des[idx], tfidf_matrix_des)
#     cosine_matrix_cate = cosine_similarity(tfidf_matrix_cate[idx], tfidf_matrix_cate)
#     cosine_matrix_title = cosine_similarity(tfidf_matrix_title[idx], tfidf_matrix_title)
#
#     sim_scores_des = [(i, c) for i, c in enumerate(cosine_matrix_des[0])]
#     sim_scores_cate = [(i, c) for i, c in enumerate(cosine_matrix_cate[0])]
#     sim_scores_title = [(i, c) for i, c in enumerate(cosine_matrix_title[0])]
#
#     sim_scores_all = [(i, sim_scores_des[i][1] * 0.75 + sim_scores_cate[i][1] * 0.2 + sim_scores_title[i][1] * 0.05)
#                       for i in range(len(sim_scores_des))]
#     sim_scores_all = sorted(sim_scores_all, key=lambda x: x[1], reverse=True)
#     sim_scores_all = [(i, score) for i, score in sim_scores_all[1:11]]
#
#     recommend_books = []
#
#     for (key, score) in sim_scores_all:
#         recommend_book = schemas.BookDescBase(book_id=int(df.loc[key]['book_id']),
#                                           title=df.loc[key]['title'],
#                                           cover=df.loc[key]['cover'],
#                                           description=df.loc[key]['description'])
#         recommend_books.append(recommend_book)
#
#     return recommend_books

async def save_tfidf_matrix():
    if not os.path.isfile(dir + '../file/db_books.csv'):
        await data.store_books_in_dir(db)

    df = pandas.read_csv(dir + '../file/db_books.csv', encoding='cp949')

    corpus_des = df['description'].values.astype('U')
    corpus_cate = df['category_name'].values.astype('U')
    corpus_title = df['title'].values.astype('U')

    tfidfv = TfidfVectorizer(stop_words='english')
    tfidf_matrix_des = tfidfv.fit_transform(corpus_des)
    tfidf_matrix_cate = tfidfv.fit_transform(corpus_cate)
    tfidf_matrix_title = tfidfv.fit_transform(corpus_title)

    joblib.dump(tfidf_matrix_des, dir + '../file/tfidf_matrix_des.pkl')
    joblib.dump(tfidf_matrix_cate, dir + '../file/tfidf_matrix_cate.pkl')
    joblib.dump(tfidf_matrix_title, dir + '../file/tfidf_matrix_title.pkl')
