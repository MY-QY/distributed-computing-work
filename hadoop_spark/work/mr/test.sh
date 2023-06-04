cd /opt/work

hadoop fs -ls /input
hadoop fs -cat /input/p1*
hadoop fs -cat /input/p2*

cd mr

hadoop fs -rm -r /output ; hadoop jar StuScore.jar xdu.jzh.StuScoreDriver /input/p1_input_file.txt /output
hadoop fs -cat /output/part-r-00000

hadoop fs -rm -r /output ; hadoop jar ClassScore.jar xdu.jzh.ClassScoreDriver /input/p1_input_file.txt /output
hadoop fs -cat /output/part-r-00000

hadoop fs -rm -r /output ; hadoop jar GrandFinder.jar xdu.jzh.GrandFinder /input/p2_input_file.txt /output
hadoop fs -cat /output/part-r-00000