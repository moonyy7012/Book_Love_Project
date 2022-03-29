from sqlalchemy import Column, ForeignKey, Integer, String, BIGINT

from db.database import Base

class Book(Base):
    __tablename__ = "book"

    book_id = Column(Integer, primary_key=True, index=True)
    title = Column(String(1000))
    description = Column(String(1000))
    author = Column(String(1000))
    pub_date = Column(String(1000))
    isbn = Column(String(200), unique=True)
    price_sales = Column(Integer)
    price_standard = Column(Integer)
    cover = Column(String(2000))
    link = Column(String(2000))
    category_id = Column(BIGINT, ForeignKey("category.category_id"))
    category_name = Column(String(2000))
    publisher = Column(String(1000))
    sales_point = Column(Integer)
    customer_review_rank = Column(Integer)

    # children = relationship("Child", backref="parent")

class Category(Base):
    __tablename__ = "category"

    category_id = Column(Integer, primary_key=True)
    name = Column(String(100))


