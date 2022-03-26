import csv
# ttbpmw71301115001
import requests
import json
import pandas as pd

def saveBook():
    maxResult = 50
    queryType = ["ItemNewAll", "ItemNewSpecial", "Bestseller"]
    key = "ttbpmw71301115001"
    dataList = ['title', 'link', 'categoryId', 'priceSales', 'isbn', 'author', 'categoryName', 'salesPoint']
    dataLen = len(dataList)
    pageNum = 20 #가져올 페이지
    bookResult= []
    #1페이지부터 원하는 페이지까지 가져오기
    for page in range(1,pageNum+1):
        url = f"http://www.aladin.co.kr/ttb/api/ItemList.aspx?ttbkey=ttbpmw71301115001&QueryType={queryType[2]}&MaxResults={maxResult}&start={page}&SearchTarget=Book&output=js&Version=20131101"
        response = requests.get(url)
        responseJson = json.loads(response.text) #response를 json으로 변환
        itemList = responseJson
        #print(url)
        #책리스트 초기화
        bookList = [[0] * dataLen for _ in range(maxResult)]
        for i in range(maxResult):
            for j in range(dataLen):
                bookList[i][j] = itemList['item'][i][dataList[j]]

        if page ==1 :
            bookResult+=bookList
        else :
            bookResult+=bookList
    df = pd.DataFrame(bookResult,
                      columns=['title', 'link', 'categoryId', 'priceSales', 'isbn', 'author', 'categoryName', 'salesPoint'])
    df.to_csv('test.csv', index=False, encoding='cp949')
    return bookResult

saveBook()

result = saveBook()
# print(result)