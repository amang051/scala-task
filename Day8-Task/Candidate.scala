import java.sql.{Connection, DriverManager, ResultSet, Statement, PreparedStatement}

case class Candidate(sno: Int, name: String, city: String)

implicit def tupleToCandidate(t: (Int, String, String)) : Candidate = Candidate(t._1, t._2, t._3)

val candidateData: Array[(Int, String, String)] = Array(
      (1, "Alice", "New York"),
      (2, "Bob", "Los Angeles"),
      (3, "Charlie", "Chicago"),
      (4, "Diana", "Houston"),
      (5, "Eve", "Phoenix"),
      (6, "Frank", "Philadelphia"),
      (7, "Grace", "San Antonio"),
      (8, "Hank", "San Diego"),
      (9, "Ivy", "Dallas"),
      (10, "Jack", "San Jose"),
      (11, "Kathy", "Austin"),
      (12, "Leo", "Jacksonville"),
      (13, "Mona", "Fort Worth"),
      (14, "Nina", "Columbus"),
      (15, "Oscar", "Charlotte"),
      (16, "Paul", "San Francisco"),
      (17, "Quinn", "Indianapolis"),
      (18, "Rita", "Seattle"),
      (19, "Steve", "Denver"),
      (20, "Tina", "Washington"),
      (21, "Uma", "Boston"),
      (22, "Vince", "El Paso"),
      (23, "Wendy", "Detroit"),
      (24, "Xander", "Nashville"),
      (25, "Yara", "Portland"),
      (26, "Zane", "Oklahoma City"),
      (27, "Aiden", "Las Vegas"),
      (28, "Bella", "Louisville"),
      (29, "Caleb", "Baltimore"),
      (30, "Daisy", "Milwaukee"),
      (31, "Ethan", "Albuquerque"),
      (32, "Fiona", "Tucson"),
      (33, "George", "Fresno"),
      (34, "Hazel", "Mesa"),
      (35, "Ian", "Sacramento"),
      (36, "Jill", "Atlanta"),
      (37, "Kyle", "Kansas City"),
      (38, "Luna", "Colorado Springs"),
      (39, "Mason", "Miami"),
      (40, "Nora", "Raleigh"),
      (41, "Owen", "Omaha"),
      (42, "Piper", "Long Beach"),
      (43, "Quincy", "Virginia Beach"),
      (44, "Ruby", "Oakland"),
      (45, "Sam", "Minneapolis"),
      (46, "Tara", "Tulsa"),
      (47, "Ursula", "Arlington"),
      (48, "Victor", "New Orleans"),
      (49, "Wade", "Wichita"),
      (50, "Xena", "Cleveland")
    )

object DatabaseUtils {
    def main(args: Array[String]): Unit = {
        Class.forName("com.mysql.cj.jdbc.Driver")

        val url = "jdbc:mysql://scaladb.mysql.database.azure.com:3306/aman"
        val username = "mysqladmin"
        val password = "Password@12345"
        val connection: Connection = DriverManager.getConnection(url, username, password)

        try {
            val statement: Statement = connection.createStatement()

            val createTableSQL =
                """
                CREATE TABLE IF NOT EXISTS candidates (
                sno INT AUTO_INCREMENT PRIMARY KEY,
                name VARCHAR(100),
                city VARCHAR(100)
                )
                """

            statement.execute(createTableSQL)
            println("Table created successfully.")

            candidateData.foreach { case (sno, name, city) =>
                insertMethod((sno, name, city), connection)
            }
            
            println("Data inserted successfully.")

            val query = "SELECT * FROM candidates"
            val resultSet: ResultSet = statement.executeQuery(query)

            println("Candidates:")
            while (resultSet.next()) {
                val sno = resultSet.getInt("sno")
                val name = resultSet.getString("name")
                val city = resultSet.getString("city")
                println(s"Sno: $sno, Name: $name, City: $city")
            }
        } catch {
            case e: Exception => e.printStackTrace()
        } finally {
            connection.close()
        }
    }

    def insertMethod(candidate: Candidate, connection: Connection): Unit = {
        val sql = "INSERT INTO candidates (sno, name, city) VALUES (?, ?, ?)"
        val preparedStatement: PreparedStatement = connection.prepareStatement(sql)
        preparedStatement.setInt(1, candidate.sno)
        preparedStatement.setString(2, candidate.name)
        preparedStatement.setString(3, candidate.city)
    
        preparedStatement.executeUpdate()
    }
}