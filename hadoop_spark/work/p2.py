from pyspark import SparkConf, SparkContext

conf = SparkConf().setAppName("GrandchildGrandparent").setMaster("local")
sc = SparkContext(conf=conf)
input_file = sc.textFile("/input/p2_input_file.txt")

relationships = input_file.map(lambda line: line.split(","))
parent_child_pairs = relationships.map(lambda pair: (pair[0].strip(), pair[1].strip()))
res_pairs = []
for pair in parent_child_pairs.collect():
    parent, child = pair[0], pair[1]
    for pair1 in parent_child_pairs.collect():
        if pair1[0] == child:
            res_pairs.append((parent, pair1[1]))
sc.parallelize(res_pairs).saveAsTextFile("/output/grandchild_grandparent_groups")

# spark-submit p2.py