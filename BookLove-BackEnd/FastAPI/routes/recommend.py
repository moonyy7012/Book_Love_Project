import os
import pandas
import csv
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

@router.get("/{book_id}", response_model=List[schemas.BookBase])
async def get_book_recommend(book_id: int, db: Session = Depends(get_db)) -> None:
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

    title2idx = {}
    for i, c in enumerate(corpus_title):
        title2idx[i] = c

    idx2title = {}
    for i, c in title2idx.items():
        idx2title[c] = i

    idx = [df.index[df['book_id'] == book_id][0]]

    cosine_matrix_des = cosine_similarity(tfidf_matrix_des[idx], tfidf_matrix_des)
    cosine_matrix_cate = cosine_similarity(tfidf_matrix_cate[idx], tfidf_matrix_cate)
    cosine_matrix_title = cosine_similarity(tfidf_matrix_title[idx], tfidf_matrix_title)

    sim_scores_des = [(i, c) for i, c in enumerate(cosine_matrix_des[0])]
    sim_scores_cate = [(i, c) for i, c in enumerate(cosine_matrix_cate[0])]
    sim_scores_title = [(i, c) for i, c in enumerate(cosine_matrix_title[0])]

    sim_scores_all = [(i, sim_scores_des[i][1] * 0.75 + sim_scores_cate[i][1] * 0.2 + sim_scores_title[i][1] * 0.05)
                      for i in range(len(sim_scores_des))]
    sim_scores_all = sorted(sim_scores_all, key=lambda x: x[1], reverse=True)
    sim_scores_all = [(i, score) for i, score in sim_scores_all[1:11]]

    recommend_books = []

    for (key, score) in sim_scores_all:
        recommend_book = schemas.BookBase(book_id=int(df.loc[key]['book_id']),
                                          title=df.loc[key]['title'],
                                          cover=df.loc[key]['cover'])
        recommend_books.append(recommend_book)

    return recommend_books
