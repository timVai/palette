CTR预估是对每次广告的点击情况做出预测，预测用户是点击还是不点击。


CTR预估和很多因素相关，比如历史点击率、广告位置、时间、用户等  用户的人口学特征、广告自身特征、广告展示特征  用户所属职业、广告展示的IP地址

DNN 在CTR的局限：图像的局部与其周围存在着紧密的联系；语音和文字的前后存在强相关性。但是CTR预估的数据如前面介绍，是非常离散的，特征前后之间的关系很多是我们排列的结果，并非本身是相互联系的

CNN

RNN

配合图像

Embeding

NN Embeding

FM
FM(Factorization Machine)主要目标是：解决数据稀疏的情况下，特征怎样组合的问题


特征================
广告创意特征：

图片，标题文字，价格，销量
推广商品所属类目，包含属性
创意组，推广计划，广告主
Query信息：

包含的Terms
Query分析：类目，属性
Query扩展：同义词，相似query
环境特征：

用户，时间
如年龄，性别，婚姻状况，职业，兴趣等
用户历史CTR， user组合特征(User-Ad, User-Query)
名义特征

时间，创意ID等
• 点击反馈特征
计算历史上包含该特征的(query, ad)的点击率
E.g., ad所属广告计划的历史点击率
组合特征

query与ad标题匹配的term个数
特征================


CTR=clicks/impressions
clicke-throuth rate
Ratio of user who click on an ad to the number of total user who view the ad

一般方案=====================
LR：

结果表示点击率
用LR做base_line
模型简单，可解释性高（工业运作中，遇到问题可以找到是哪个特征对应的权重出现问题）
大多公司还在用LR或用LR和其他模型混合
平时用pandas但吃内存
工业界用LIBLINEAR（libsvm），省内存

组合特征非常有用。FFM适合用于组合特征，用矩阵分解减小开销。
one-hot编码数据会非常稀疏，组合特征更会暴增
稀疏性会带来什么问题：

内存压力大
组合特征出现1的次数少，样本不足，结果不准
L1正则项能使大量的无效特征权重为0，起到特征选择作用

模型：LR--FM，FFM--GDBT--RF--DNN

作者：小透明苞谷
链接：https://www.jianshu.com/p/635dc4ffae32
來源：简书
简书著作权归作者所有，任何形式的转载都请联系作者获得授权并注明出处。
一般方案=====================



###########################
input,[x1_p1,x2_p2,x3_p3.....xn_pn],title,type,label

{x}中数据可以和title按相关性 交叉  再用x对应概率做其权重

type 是title的属性  但不知道怎么用

数据观察：
title不是以input开头的点击，type为经验、百科、知道等


