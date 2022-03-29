from sqlalchemy.orm import Session

import db.models as models


def get_books(db: Session):
    return db.query(models.Book).filter().all()

def get_categories(db: Session):
    return db.query(models.Category).filter().all()

def get_book_info_by_book_id(db : Session, book_id : int):
    return db.query(models.Book).filter(models.Book.book_id==book_id).first()