from sqlalchemy.orm import Session

import db.models as models

def get_books(db: Session):
    return db.query(models.Book).filter().all()

def get_categories(db: Session):
    return db.query(models.Category).filter().all()

def get_click_log_by_user_id(db: Session, user_id: int):
    return db.query(models.ClickLog).filter(models.ClickLog.user_id == user_id).all()