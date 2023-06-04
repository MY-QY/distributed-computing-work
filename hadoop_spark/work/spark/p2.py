from pyspark import SparkConf, SparkContext

# 加载数据为 RDD
conf = SparkConf().setAppName("GrandchildGrandparent").setMaster("local")
sc = SparkContext(conf=conf)
input_file = sc.textFile("/input/p2_input_file.txt")
# 第一次 map 操作，将每一行的两个人的关系拆分为两个元素的 RDD 列表
relationships = input_file.map(lambda line: line.split(","))
# 第二次 map 操作, 去除字符两侧空白
parent_child_pairs = relationships.map(lambda pair: (pair[0].strip(), pair[1].strip()))
# 第三次 map 操作, 将每一行的两个人的关系拆分为两个元素的 RDD 列表, 并将每个元素的关系类型标记为 0 或 1, 0 代表父子关系, 1 代表子父关系
mid_pairs = parent_child_pairs.flatMap(
    lambda pair: [(pair[1], pair[0], 1), (pair[0], pair[1], 0)]
)
# 将每个人的关系按照人名分组
grouped_pairs = mid_pairs.groupBy(lambda pair: pair[0])
# 进行 reduce, 在每一组中, 选出关系为子父关系的人和关系为父子关系的人, 两两组合, 得到孙子和祖父的关系
res_pairs = grouped_pairs.flatMap(
    lambda group: [
        (pair2[1], pair1[1])
        for pair1 in group[1]
        for pair2 in group[1]
        if pair1[2] == 1 and pair2[2] == 0
    ]
)
# 祖父 - 孙子
# res_pairs = grouped_pairs.flatMap(lambda group: [(pair1[1], pair2[1]) for pair1 in group[1] for pair2 in group[1] if pair1[2] == 1 and pair2[2] == 0])
# 保存结果
res_pairs.saveAsTextFile("/output/grandchild_grandparent_groups")

# spark-submit p2.py
