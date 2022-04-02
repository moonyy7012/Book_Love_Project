from sqlalchemy import create_engine
from sqlalchemy.ext.declarative import declarative_base
from sqlalchemy.orm import sessionmaker

SQLALCHEMY_DATABASE_URL = "mysql+pymysql://ssafy:ssafy@localhost:3306/bookdb"
# SQLALCHEMY_DATABASE_URL = "mysql+pymysql://ssafy:ssafy@j6d106.p.ssafy.io:3306/bookdb"

engine = create_engine(
    SQLALCHEMY_DATABASE_URL
)

SessionLocal = sessionmaker(autocommit=False, autoflush=False, bind=engine)

Base = declarative_base()



