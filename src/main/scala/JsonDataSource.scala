import com.alibaba.fastjson.JSON
import org.apache.spark.sql.{DataFrame, SparkSession}

/**
  * Created by sw on 2018/3/12
  */
object JsonDataSource {
  def main(args: Array[String]): Unit = {

    val spark = SparkSession.builder().appName("JdbcDataSource")
      .master("local[*]")
      .getOrCreate()

    import spark.implicits._

    //指定以后读取json类型的数据(有表头)
    val jsons: DataFrame = spark.read.json("D:\\dat.json")

    val str = jsons.toString()
    //val filtered: DataFrame = jsons.select("timestamp", "open", "high", "low", "close", "volume")

    //filtered.printSchema()

    //filtered.show()
    jsons.toDF().show()

    spark.stop()


  }
}
