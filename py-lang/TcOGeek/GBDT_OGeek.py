#!usr/bin/env python
# -*- coding: utf-8 -*-

import sys
import os
import time
from sklearn import metrics
import numpy as np
import cPickle as pickle


# GBDT(Gradient Boosting Decision Tree) Classifier
def gradient_boosting_classifier(train_x, train_y):
    from sklearn.ensemble import GradientBoostingClassifier
    model = GradientBoostingClassifier(n_estimators=200)
    model.fit(train_x, train_y)
    return model


def read_data(data_file):
    import gzip
    f = gzip.open(data_file, "rb")
    train, val, test = pickle.load(f)
    f.close()
    train_x = train[0]
    train_y = train[1]
    test_x = test[0]
    test_y = test[1]
    return train_x, train_y, test_x, test_y


if __name__ == '__main__':
    data_file = "mnist.pkl.gz"
    model_save_file = None
    model_save = {}

    train_x, train_y, test_x, test_y = read_data(data_file)
    num_train, num_feat = train_x.shape
    num_test, num_feat = test_x.shape
    is_binary_class = (len(np.unique(train_y)) == 2)

    start_time = time.time()
    model = gradient_boosting_classifier(train_x, train_y)
    # print    'training took %fs!' % (time.time() - start_time)
    predict = model.predict(test_x)

    if is_binary_class:
        precision = metrics.precision_score(test_y, predict)
        recall = metrics.recall_score(test_y, predict)
        print('precision: %.2f%%, recall: %.2f%%' % (100 * precision, 100 * recall))
    accuracy = metrics.accuracy_score(test_y, predict)
    print('accuracy: %.2f%%' % (100 * accuracy))

    pickle.dump(model_save, open(model_save_file, 'wb'))
