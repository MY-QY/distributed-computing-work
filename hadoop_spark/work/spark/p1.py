from pyspark.sql import SparkSession

# 创建 SparkSession
spark = SparkSession.builder.appName("StudentScores").getOrCreate()
# 读取输入文件并创建 DataFrame
input_file = spark.read.text("/input/p1_input_file.txt")
df = input_file.selectExpr("split(value, ',') as data")
# 进行 map 操作, 将 DataFrame 数据映射为一个个的 RDD 列表
rdd = df.rdd.map(
    lambda row: (row.data[0], row.data[1], row.data[2], row.data[3], float(row.data[4]))
)
# 进行 map 操作, 过滤出必修课, 将 (班级, 学生) 作为 key, 按 key 进行分组, 然后计算每个学生必修课的平均成绩
student_avg_scores = (
    rdd.filter(lambda row: row[3] == "必修")
    .map(lambda row: ((row[0], row[1]), row[4]))
    .groupByKey()
    .mapValues(lambda scores: sum(scores) / len(scores))
)
# 进行 map 操作, 将 (班级,科目) 作为 key, 按 key 进行分组, 最后按科目统计每个班的平均成绩
class_subject_avg_scores = (
    rdd.map(lambda row: ((row[0], row[2]), row[4]))
    .groupByKey()
    .mapValues(lambda scores: sum(scores) / len(scores))
)
# 保存结果到文件
student_avg_scores.saveAsTextFile("/output/student_avg_scores")
class_subject_avg_scores.saveAsTextFile("/output/class_subject_avg_scores")

# spark-submit p1.py
