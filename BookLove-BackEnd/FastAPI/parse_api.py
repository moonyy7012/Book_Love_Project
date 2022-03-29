import asyncio
# ttbpmw71301115001
import requests
import json
import pandas as pd
import os

dir = os.path.dirname(os.path.realpath(__file__)).replace('\\', '/') + '/'

async def save_book():
    max_result = 50
    query_type = ["Bestseller", "ItemNewSpecial"]
    key = "ttbsmy042991037001"
    #"ttbpmw71301115001"
    data_list = ['title', 'description', 'link', 'pubDate', 'priceStandard', 'cover', 'publisher',
                 'priceSales', 'isbn', 'author', 'categoryName', 'salesPoint', 'customerReviewRank']
    data_len = len(data_list)
    page_num = 20 #가져올 페이지
    book_result= []
    category_list = (pd.read_csv(dir + 'file/category_all.csv', encoding='cp949')['CID'].values.tolist())


    for query_type in query_type:
        if query_type == query_type[1]:
            category_list = [0]
            max_result = 20
            page_num = 1

        for cid in category_list:
            # 1페이지부터 원하는 페이지까지 가져오기
            for page in range(1, page_num + 1):
                url = f"http://www.aladin.co.kr/ttb/api/ItemList.aspx?ttbkey={key}&QueryType={query_type}" \
                      f"&MaxResults={max_result}&start={page}&SearchTarget=Book&output=js&Version=20131101&categoryId={cid}"
                response = requests.get(url)
                try:
                    response_json = json.loads(response.text)  # response를 json으로 변환
                except (json.JSONDecodeError, json.JSONDecodeError):
                    continue

                item_list = response_json
                # print(len(item_list['item']))

                book_list = [[0] * data_len for _ in range(len(item_list['item']))]

                for i in range(len(item_list['item'])):
                    for j in range(data_len):
                        book_list[i][j] = item_list['item'][i][data_list[j]]

                book_result += book_list

                if len(item_list['item']) < 50 :
                    break


        df = pd.DataFrame(book_result,
                          columns=['title', 'description', 'link', 'pub_date', 'price_standard', 'cover', 'publisher',
                                   'price_sales', 'isbn', 'author', 'category_name', 'sales_point', 'customer_review_rank'])

        df.to_csv(dir + 'file/books.csv', index=False, encoding='cp949')

    return book_result

def process_book():
    # 데이터 가공
    map_dictionary = {'소설/시/희곡': 1, '역사': 74, '경제경영': 170, '자기계발': 336, '컴퓨터/모바일': 351, '예술/대중문화': 517,
                      '인문학': 656, '사회과학': 798, '과학': 987, '어린이': 1108, '청소년': 1137, '여행': 1196,
                      '가정/요리/뷰티': 1230, '종교/역학': 1237, '외국어': 1322, '수험서/자격증': 1383, '좋은부모': 2030, '고전': 2105,
                      '만화': 2551, '대학교재/전문서적': 8257, '유아': 13789, '전집/중고전집': 17195, '초등학교참고서': 50246,
                      '에세이': 55889, '건강/취미/레저': 55890, '중학교참고서': 76000, '고등학교참고서': 76001, '장르소설': 112011}

    df = pd.read_csv(dir + 'file/books.csv', encoding='cp949');
    df['category_id'] = df['category_name'].str.split(">", expand=True)[1].map(map_dictionary)
    df = df.drop_duplicates(['isbn'])
    df = df.dropna(axis=0, how='any')
    df.to_csv(dir + 'file/books.csv', index=False, encoding='cp949')

# asyncio.run(save_book())
# process_book()



