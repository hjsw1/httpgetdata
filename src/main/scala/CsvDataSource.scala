import org.apache.spark.sql.{DataFrame, SparkSession}

/**
  * Created by sw on 2018/3/12
  */
object CsvDataSource {
  def main(args: Array[String]): Unit = {
    val spark = SparkSession.builder().appName("CsvDataSource")
      .master("local[*]")
      .getOrCreate()

    //指定以后读取csv类型的数据
    val csv: DataFrame = spark.read.csv("D:\\date.csv")
    //过滤掉csv的第一行
    val header = csv.first()

    val trainData = csv.filter(_ != header)

    trainData.printSchema()


    val pdf: DataFrame = trainData.toDF("timestamp", "open", "high","low","close","volume")
    pdf.show()

    spark.stop()
  }

}
