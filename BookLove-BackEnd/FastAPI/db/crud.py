from sqlalchemy.orm import Session

import db.models as models


def get_books(db: Session):
    return db.query(models.Book).filter().all()

def get_categories(db: Session):
    return db.query(models.Category).filter().all()
