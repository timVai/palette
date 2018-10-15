import jieba
import re


def cutWordAndFormat4Fasttext():
    # format_file = open("/Users/timvai/train-data/tc_ogeek/oppo_round1_train_20180929.format.txt",'w')
    format_file = open("/Users/timvai/train-data/tc_ogeek/oppo_round1_vali_20180929.format.txt",'w')

    # with open("/Users/timvai/train-data/tc_ogeek/10.txt") as f:
    # with open("/Users/timvai/train-data/tc_ogeek/oppo_round1_train_20180929.txt") as f:
    with open("/Users/timvai/train-data/tc_ogeek/oppo_round1_vali_20180929.txt") as f:
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
    format_file = open("/Users/timvai/train-data/tc_ogeek/oppo_round1_test_A_20180929.format.txt",'w')

    with open("/Users/timvai/train-data/tc_ogeek/oppo_round1_test_A_20180929.txt") as f:
        for line in f:
            line = re.sub("[\s+\.\!\/_,$%^*(+\"\')]+|[+——()?【】“”！，{:}。？、~@#￥%……&*（）]+", "",line)
            jieba_result = jieba.cut(line,False,True)
            line = " ".join(jieba_result)
            line = "__label__  , " + line + "\n"
            print(line)
            format_file.write(line)

    format_file.close()


def testJieba():
    jieba_result = jieba.cut("今天我们去钓鱼吧", False, True)
    print(jieba_result)
    print(" ".join(jieba_result))

if __name__ == '__main__':
    # cutWordAndFormat4Fasttext()
    # testJieba();
    cut_test_word_and_format4Fasttext()