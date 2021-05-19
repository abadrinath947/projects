import sklearn
from sentence_transformers import SentenceTransformer
import pandas as pd
import tensorflow as tf
import numpy as np
import keras
import sklearn
from sklearn.linear_model import SGDClassifier
from keras.models import Sequential
from keras.layers import Dense, BatchNormalization, LSTM, Flatten
from keras.optimizers import Adam
import re

gpus = tf.config.experimental.list_physical_devices('GPU')
for gpu in gpus:
    tf.config.experimental.set_memory_growth(gpu, True)


BATCH_SIZE = 64
NUM_CLASSES = 5
EPOCHS = 30

def handcoded_features(t, split):
  # look for word "stars"
  search = re.search('(\d)\s+stars?', t)
  f1 = 0 if not search else int(search.group(1))
  # capitalization, exclamation
  f2 = sum(1 for c in t if c.isupper()) / len(t) if len(t) > 0 else 0
  f3 = t.count('!')
  # question marks
  f4 = t.count('?')
  # length
  f5 = len(t) / 1000
  return [f1, f2, f3, f4, f5]

def data_generator(loc):
    df = pd.read_json(loc, lines = True).iloc[:500000]
    model1 = SentenceTransformer('paraphrase-distilroberta-base-v1')
    model2 = SentenceTransformer('bert-base-nli-mean-tokens')
    while True:
      for i in range(len(df['text']) // BATCH_SIZE - 1):
          texts = list(df['text'].values[i * BATCH_SIZE: (i + 1) * BATCH_SIZE])
          processed_texts = []
          for t in texts:
            if len(t) < 200:
                split = [t[:len(t) // 2], t[:int(len(t) * 0.75)], t]
            else:
                split = [t[:len(t) // 5], t[len(t) // 5:4 * len(t) // 5], t[-len(t) // 5:]]
            processed_text = np.append(model1.encode(split, show_progress_bar = False).ravel(), model2.encode(split, show_progress_bar = False).ravel())
            processed_text = np.append(processed_text, handcoded_features(t, split))
            processed_texts.append(processed_text)
            #processed_texts.append(model2.encode(split).ravel())
          encoded_texts = np.array(processed_texts)
          #encoded_texts = model2.encode(texts)
          ratings = df['stars'].values[i * BATCH_SIZE: (i + 1) * BATCH_SIZE].astype(int) - 1
          ratings = keras.utils.to_categorical(ratings, NUM_CLASSES)
          yield encoded_texts.astype('float32'), ratings.astype('float32')
        
def data_generator_tm(loc):
    df = pd.read_json(loc, lines = True)
    model1 = SentenceTransformer('bert-large-nli-max-tokens')
    #model2 = SentenceTransformer('bert-base-nli-mean-tokens')
    while True:
      for i in range(len(df['text']) // BATCH_SIZE - 1):
          texts = list(df['text'].values[i * BATCH_SIZE: (i + 1) * BATCH_SIZE])
          ratings_df = df['stars'].values[i * BATCH_SIZE: (i + 1) * BATCH_SIZE].astype(int) - 1
          processed_texts, ratings = [], []
          for i, t in enumerate(texts):
            t = text_mod(t, ratings_df[i])
            if t is None:
                continue
            if len(t) < 200:
                split = [t[:len(t) // 2], t]
            elif len(t) < 400:
                split = [t[:len(t) // 3], t]
            else:
                split = [t[:len(t) // 5], t]
            processed_text = model1.encode(split, show_progress_bar = False) #, model2.encode(split, show_progress_bar = False).ravel())
            #print(processed_text.shape)
            processed_text = np.append(processed_text, [handcoded_features(split[0], split), handcoded_features(split[1], split)], axis = 1)
            processed_texts.append(processed_text)
            ratings.append(ratings_df[i])
            #processed_texts.append(model2.encode(split).ravel())
          encoded_texts = np.array(processed_texts)
          #encoded_texts = model2.encode(texts)
          ratings = keras.utils.to_categorical(ratings, NUM_CLASSES)
          yield encoded_texts.astype('float32'), np.array(ratings).astype('float32')

def text_mod(t, y):
    y += 1
    to = t
    p1, p2 = np.random.uniform(size = (2,))
    if p1 <= 0.7 and y in [1, 5]:
        return None
    elif p2 >= 0.5:
        return to
    p = np.random.uniform()
    if p <= 0.3:
        t = t[:int(len(t) * (p + 0.1))]
    elif p >= 0.6:
        t = t[int(len(t) * (p - 0.1)):]
    else:
        p1 = np.random.uniform(high = 0.3)
        p2 = np.random.uniform(low = 0.7)
        t = t[int(len(t) * p1): int(len(t) * p2)]
    if len(t) < 10:
        return to
    return t

model = Sequential()
model.add(LSTM(1024, input_shape = (2, 1029)))
#model.add(BatchNormalization())
model.add(Flatten())
model.add(Dense(512, activation = 'relu'))
#model.add(BatchNormalization())
model.add(Dense(256, activation = 'relu'))
#model.add(BatchNormalization())
model.add(Dense(128, activation = 'relu'))
#model.add(BatchNormalization())
model.add(Dense(5, activation = 'softmax'))
model.summary()

model.compile('Adam', 'categorical_crossentropy', metrics = ['accuracy'])
model.fit(data_generator_tm('dataset.jsonl'), 
          steps_per_epoch = 50000 / BATCH_SIZE, epochs = EPOCHS,)
model.save('model10.h5')
          #validation_data = test_data_generator('yelp_review_training_dataset.jsonl'))
