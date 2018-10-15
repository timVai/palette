import fasttext


def train_ft():
    print("start train...")
    classifier = fasttext.supervised('/Users/timvai/train-data/tc_ogeek/oppo_round1_train_20180929.format.txt','model-v0')
    print("train finish")

    print("start test")
    result = classifier.test('/Users/timvai/train-data/tc_ogeek/oppo_round1_vali_20180929.format.txt')
    print('P@1:', result.precision)
    print('R@1:', result.recall)
    f1 = (2*result.precision*result.recall)/(result.precision+result.recall)//0.8409488672/1.29688
    print('Number of examples:', result.nexamples)


def check():
    classifier = fasttext.load_model("model-v0.bin")
    # classifier = fasttext.load_model("model-v0.bin",label_prefix='__label__')
    label = classifier.predict(["__label__1 , 学位 学位 英语 0032 学位证 0045 学位 网 0108 学位 服 0021 学位 认证 0022 学位证书 0057 学位证 有 什么 用 0026 学位 等级 0029 学位 是 什么 0037 学位 和 学历 0019 学位 证书编号 怎么 查 经验"])

    print(label[0])

def test():
    classifier = fasttext.load_model("model-v0.bin")
    result = open("/Users/timvai/train-data/tc_ogeek/model-v0.pred",'w')
    with open("/Users/timvai/train-data/tc_ogeek/oppo_round1_test_A_20180929.format.txt") as f:
        for pre_line in f:
            list=[]
            list.append(pre_line)
            r = classifier.predict(list)[0][0].split("__")[2]
            r = r+"\n"
            print(r)
            result.write(r)
    result.close()

if __name__ == '__main__':
    # train_ft()
    # check()
    test()

    print("all finish ")