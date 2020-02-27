import org.apache.spark.sql.functions.{col, to_date, to_timestamp}

//read data from the csv
val frontData = spark.read
  .option("header", "true")
  .option("inferSchema", "true")
  .csv("/path/front.csv")

//add date, month, year as new column
val df = frontData
.withColumn("date_col", to_date(col("timestamp"))) //daily
.withColumn("month", month(col("date_col"))) //monthly 
.withColumn("year", year(col("date_col")))

//display the schema of the dataframe
df.printSchema

//this step is not needed but helpful to query the view using sql
df.createOrReplaceTempView("accounts")

//adding new column for quarterly aggregation
val Q1 = Seq(1,2,3)
val Q2 = Seq(4,5,6)
val Q3 = Seq(7,8,9)
val Q4 = Seq(10,11,12)

//adding a quarter column and using isInCollection() to categorize month into a quarter
val dfWithQuarter = df.withColumn("quarter", when(col("month").isInCollection(Q1), 1)
.when(col("month").isInCollection(Q2), 2)
.when(col("month").isInCollection(Q3), 3)
// .when(col("month").isInCollection(Q4), 4)
.otherwise(4)
)

//daily aggregation
val dailyDf = df.groupBy("date_col", "customer_id").agg(sum("change_in_mrr").alias("mrr")).orderBy(asc("date_col"))

//show is used to display first 50 rows in the dataframe
dailyDf.show

//monthly aggregation
val monthlyDf = df.groupBy("month", "year", "customer_id").agg(sum("change_in_mrr").alias("mrr")).orderBy(asc("year"), asc("month"))

//if we want to filter records:
// for e.g do not display records if MRR is 0
monthlyDf.filter("mrr != 0").show

//quartely aggregation
dfWithQuarter.groupBy("year","quarter", "customer_id").agg(sum("change_in_mrr").alias("mrr")).orderBy(asc("year"), asc("quarter")).show(300)

// write to mysql db
// store the daily aggregation in mysql 
// one can do similar snippet for writing the other form of aggregation
dailyDf
.write
.format("jdbc")      
.option("url", "jdbc:mysql://docker_db_1:3306/db")
  .option("driver", "com.mysql.jdbc.Driver")
  .option("dbtable", "revenues")
  .option("user", "user")
  .option("password", "root")
  .save()
