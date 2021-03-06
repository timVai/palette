import jieba
import re

from util.LD import ld_distance

base_dir="/Users/timvai/train-data/tc_ogeek/"
# base_dir="/Users/fanfengshi/data/Tc_OGeek/"

def cutWordAndFormat4Fasttext():
    # format_file = open("/Users/timvai/train-data/tc_ogeek/oppo_round1_train_20180929.format.txt",'w')
    format_file = open(base_dir + "oppo_round1_vali_20180929.format.txt",'w')

    # with open("/Users/timvai/train-data/tc_ogeek/10.txt") as f:
    # with open("/Users/timvai/train-data/tc_ogeek/oppo_round1_train_20180929.txt") as f:
    with open(base_dir + "oppo_round1_vali_20180929.txt") as f:
        for line in f:
            label = line.split("\t")[-1:][0]
            label = str(label).replace("\n","")
            line = "\t".join(line.split("\t")[:-1])
            line = re.sub("[\s+\.\!\/_,$%^*(+\"\')]+|[+——()?【】“”！，{:}。？、~@#￥%……&*（）]+", "",line)
            jieba_result = jieba.cut(line,False,True)
            line = " ".join(jieba_result)
            # print(label)
            # print(line)
            formatted = "__label__" + label + " , " + line + "\n"
            print(formatted)
            format_file.write(formatted)

    format_file.close()

def cut_test_word_and_format4Fasttext():
    format_file = open(base_dir + "oppo_round1_test_A_20180929.format.txt",'w')

    with open(base_dir + "oppo_round1_test_A_20180929.txt") as f:
        for line in f:
            line = re.sub("[\s+\.\!\/_,$%^*(+\"\')]+|[+——()?【】“”！，{:}。？、~@#￥%……&*（）]+", "",line)
            jieba_result = jieba.cut(line,False,True)
            line = " ".join(jieba_result)
            line = "__label__  , " + line + "\n"
            print(line)
            format_file.write(line)

    format_file.close()


def lookup_class_data(class_num):
    max_search_list = 0
    with open(base_dir + "oppo_round1_train_20180929.txt.class"+class_num, 'w') as result_file:

        with open(base_dir + "oppo_round1_train_20180929.txt") as f:
            for line in f:
                result_class = line.split('\t')[4]
                search_list_len = len(line.split('\t')[1].split(','))
                if search_list_len > max_search_list:
                    max_search_list = search_list_len
                print(result_class)
                if result_class == class_num + '\n':
                    print(line)
                    # result_file.write(line)

    print(max_search_list)

def test_jieba():
    jieba_result = jieba.cut("今天我们去钓鱼吧", False, True)
    print(jieba_result)
    print(" ".join(jieba_result))


def pre_look_feature():

    max_search_list_len = 16
    with open(base_dir + "oppo_round1_train_20180929.txt") as train_data:
        for line in train_data:
            feature_map = ""

            cols = line.split('\t')
            user_input = cols[0]
            search_list = cols[1].replace("{", "").replace("}", "").split(",")
            title = cols[2]
            title_type = cols[3]
            label = cols[4]

            search_feature = []
            index = 0
            for search_word in search_list:
                try:
                    pair_word = search_word.split(':')
                    word = pair_word[0]
                    p = pair_word[1]

                    ld = ld_distance(word,title) / len(title)
                    feature_sim = ld * float(p.replace("\"", ""))
                    search_feature.append(word+" : "+p+" : "+str(ld)+" : f"+str(feature_sim))
                    index = index + 1
                except IndexError:
                    feature_map = ""

            #补齐特征wei
            while index < max_search_list_len:
                search_feature.append()
                index = index + 1

            print(user_input + "#" + ",".join(search_feature) + "#" + title + "#" + title_type + "#" +label)
            feature_map = " ".join(search_feature) + "#" + title + "#" + title_type + "#" +label



if __name__ == '__main__':
    # cutWordAndFormat4Fasttext()
    # test_jieba();
    # cut_test_word_and_format4Fasttext()
    # lookup_class_data('1')
    lookup_class_data('0')
    # pre_look_feature()
    print('finish')
