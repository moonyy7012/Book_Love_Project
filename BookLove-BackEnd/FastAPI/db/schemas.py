from pydantic import BaseModel

class Book(BaseModel):
    book_id: int
    title: str
    category_id: int
    description: str
    author: str
    pub_date: str
    isbn: str
    price_sales: int
    price_standard: str
    cover: str
    link: str
    category_id: int
    category_name: str
    publisher: str
    sales_point: int
    customer_review_rank: int

    class Config:
        orm_mode = True

class Book(BaseModel):
    title: str
    category_id: int
    description: str
    author: str
    pub_date: str
    isbn: str
    price_sales: int
    price_standard: str
    cover: str
    link: str
    category_id: int
    category_name: str
    publisher: str
    sales_point: int
    customer_review_rank: int

    class Config:
        orm_mode = True

class Category(BaseModel):
    category_id: int
    name: str

    class Config:
        orm_mode = True