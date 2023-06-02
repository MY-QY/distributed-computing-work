from pyspark import SparkConf, SparkContext

conf = SparkConf().setAppName("GrandchildGrandparent").setMaster("local")
sc = SparkContext(conf=conf)
input_file = sc.textFile("/input/p2_input_file.txt")

relationships = input_file.map(lambda line: line.split(","))
parent_child_pairs = relationships.map(lambda pair: (pair[0].strip(), pair[1].strip()))
mid_pairs = parent_child_pairs.flatMap(
    lambda pair: [(pair[1], pair[0], 1), (pair[0], pair[1], 0)]
)
grouped_pairs = mid_pairs.groupBy(lambda pair: pair[0])
# res_pairs = grouped_pairs.flatMap(lambda group: [(pair1[1], pair2[1]) for pair1 in group[1] for pair2 in group[1] if pair1[2] == 1 and pair2[2] == 0])
res_pairs = grouped_pairs.flatMap(
    lambda group: [
        (pair2[1], pair1[1])
        for pair1 in group[1]
        for pair2 in group[1]
        if pair1[2] == 1 and pair2[2] == 0
    ]
)
res_pairs.saveAsTextFile("/output/grandchild_grandparent_groups")

# spark-submit p2.py
