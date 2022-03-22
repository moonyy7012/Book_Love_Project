import SaveBook
import pandas as pd
from sklearn.feature_extraction.text import CountVectorizer
from sklearn.metrics.pairwise import cosine_similarity



df = pd.read_csv('./test.csv', encoding='cp949')
data= df
# print(data['categoryName'][0])
data['categoryName'] = data['categoryName'].apply(lambda x:x.replace(">"," "))
# print(data['categoryName'][0])

counterVector = CountVectorizer(ngram_range=(1, 3))
# print(len(result))
cVectorCategory = counterVector.fit_transform(data['categoryName'])
# cVectorCategory.shape
# cVectorCategory

similarityCategory = cosine_similarity(cVectorCategory, cVectorCategory).argsort()[:, ::-1]
# print(similarity_genre)
#similarityCategory.shape


def recommend_books(df, bookTitle, top=10):
    target_book = df[df['title'] == bookTitle].index.values
    sim_index = similarityCategory[target_book, :top].reshape(-1)
    sim_index = sim_index[sim_index != target_book]
    result = df.iloc[sim_index].sort_values('salesPoint', ascending=False)[:10]
    return result


recoList = recommend_books(data, "어서 오세요, 휴남동 서점입니다")
with pd.option_context('display.max_rows', None, 'display.max_columns', None):
    print(recoList)
