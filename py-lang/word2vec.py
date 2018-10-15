from gensim.test.utils import common_texts, get_tmpfile
from gensim.models import Word2Vec
from gensim.test.utils import datapath
from gensim.models.word2vec import LineSentence
import time


# https://radimrehurek.com/gensim/models/word2vec.html
#
# sentences = []
#
# model = Word2Vec(min_count=1)
#
# model.build_vocab(sentences)  # prepare the model vocabulary
#
# model.train(sentences, total_examples=model.corpus_count, epochs=model.iter)  # train word vectors
# (1, 30)


# //////////
# path = get_tmpfile("word2vec.model")
#
#
#
# common_texts = LineSentence(datapath('/Users/timvai/train-data/10w.comment'))
# model = Word2Vec(common_texts, size=100, window=5, min_count=1, workers=4)
# model.save("word2vec.model")


model =  Word2Vec.load("word2vec.model")
print(model.wv["裙子"])
start_time = time.time()
mostLike = model.predict_output_word(["裙子"])
print(time.time() - start_time)
for result in mostLike :
    print(result)




#

#vThe training is streamed
# model = Word2Vec.load("word2vec.model")
# model.train([["hello", "world"]], total_examples=1, epochs=1)


# #加载google模型就不能再训练了，因为模型中没有 the hidden weights, vocabulary frequencies and the binary tree    not just the KeyedVectors.
# # so google model just the KeyedVectors.
# wv_from_text = KeyedVectors.load_word2vec_format(datapath('word2vec_pre_kv_c'), binary=False)  # C text format
# wv_from_bin = KeyedVectors.load_word2vec_format(datapath("euclidean_vectors.bin"), binary=True)  # C binary format
#
#
#
# #get keyedvectors
# word_vectors = model.wv




print("finish")