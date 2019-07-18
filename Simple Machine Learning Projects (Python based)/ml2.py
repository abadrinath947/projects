import time
import numpy as np
import scipy as sp
import matplotlib.pyplot as plt
from sklearn import cluster
from skimage.transform import resize
from sklearn.feature_extraction import image
from sklearn.cluster import spectral_clustering

# load the raccoon face as a numpy array
try:  # SciPy >= 0.16 have face in misc
    from scipy.misc import face
    face = face(gray=True)
except ImportError:
    face = sp.face(gray=True)

print("Done loading the raccoon!")

def show_raccoon():
   n_clusters = 5
   np.random.seed(0)

   X = face.reshape((-1, 1))  # We need an (n_sample, n_feature) array
   k_means = cluster.KMeans(n_clusters=n_clusters, n_init=4)
   k_means.fit(X)
   values = k_means.cluster_centers_.squeeze()
   labels = k_means.labels_

   vmin = face.min()
   vmax = face.max()
   # original face
   plt.figure(1, figsize=(3, 2.2))
   plt.imshow(face, cmap=plt.cm.gray, vmin=vmin, vmax=256)

def make_raccoon_smaller():
   n_clusters = 5
   np.random.seed(0)

   X = face.reshape((-1, 1))  # We need an (n_sample, n_feature) array
   k_means = cluster.KMeans(n_clusters=n_clusters, n_init=4)
   k_means.fit(X)
   values = k_means.cluster_centers_.squeeze()
   labels = k_means.labels_

   vmin = face.min()
   vmax = face.max()
   # create an array from labels and values
   face_compressed = np.choose(labels, values)
   face_compressed.shape = face.shape

   # compressed face
   plt.figure(2, figsize=(3, 2.2))
   plt.imshow(face_compressed, cmap=plt.cm.gray, vmin=vmin, vmax=vmax)

def plot_raccoon_face():
   n_clusters = 5
   np.random.seed(0)

   X = face.reshape((-1, 1))  # We need an (n_sample, n_feature) array
   k_means = cluster.KMeans(n_clusters=n_clusters, n_init=4)
   k_means.fit(X)
   values = k_means.cluster_centers_.squeeze()
   labels = k_means.labels_

   vmin = face.min()
   vmax = face.max()
   
   # equal bins face
   regular_values = np.linspace(0, 256, n_clusters + 1)
   regular_labels = np.searchsorted(regular_values, face) - 1
   regular_values = .5 * (regular_values[1:] + regular_values[:-1])  # mean
   regular_face = np.choose(regular_labels.ravel(), regular_values, mode="clip")
   regular_face.shape = face.shape
   plt.figure(3, figsize=(3, 2.2))
   plt.imshow(regular_face, cmap=plt.cm.gray, vmin=vmin, vmax=vmax)

   # histogram
   plt.figure(4, figsize=(3, 2.2))
   plt.clf()
   plt.axes([.01, .01, .98, .98])
   plt.hist(X, bins=256, color='.5', edgecolor='.5')
   plt.yticks(())
   plt.xticks(regular_values)
   values = np.sort(values)
   for center_1, center_2 in zip(values[:-1], values[1:]):
       plt.axvline(.5 * (center_1 + center_2), color='b')

   for center_1, center_2 in zip(regular_values[:-1], regular_values[1:]):
       plt.axvline(.5 * (center_1 + center_2), color='b', linestyle='--')

def show_image_recognition(type_of_recog):
   face_resized = resize(face, (100, 100))
   # Convert the image into a graph with the value of the gradient on the
   # edges.
   graph = image.img_to_graph(face_resized)

   # Take a decreasing function of the gradient: an exponential
   # The smaller beta is, the more independent the segmentation is of the
   # actual image. For beta=1, the segmentation is close to a voronoi
   beta = 5
   eps = 1e-6
   graph.data = np.exp(-beta * graph.data / graph.data.std()) + eps

   # Apply spectral clustering (this step goes much faster if you have pyamg
   # installed)
   N_REGIONS = 25
   for assign_labels in ('kmeans', 'discretize'):
      if (assign_labels == type_of_recog):
          t0 = time.time()
          labels = spectral_clustering(graph, n_clusters=N_REGIONS,
                                       assign_labels=assign_labels, random_state=1)
          t1 = time.time()
          labels = labels.reshape(face_resized.shape)

          plt.figure(figsize=(5, 5))
          plt.imshow(face_resized, cmap=plt.cm.gray)
          for l in range(N_REGIONS):
              plt.contour(labels == l, contours=1,
                          colors=[plt.cm.nipy_spectral(l / float(N_REGIONS))])
          plt.xticks(())
          plt.yticks(())
          title = 'Spectral clustering: %s, %.2fs' % (assign_labels, (t1 - t0))
          print(title)
          plt.title(title)

