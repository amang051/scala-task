import scala.io.Source
import scala.util.{Try, Success, Failure}

case class Employee(sno: Int, name: String, city: String, salary: Long, department: String)

object CSVReader {
    def main(args: Array[String]) = {
        val fileName = "/Users/amangarg/Downloads/VS Code/Day5-Task/data.csv"

        val employeesTry = Try {
            val source = Source.fromFile(fileName)

            val data = for {
                line <- source.getLines().drop(1)
                columns = line.split(",").map(_.trim)
                if columns.length == 5
                sno = columns(0).toInt
                name = columns(1)
                city = columns(2)
                salary = columns(3).toLong
                department = columns(4)
            } yield Employee(sno, name, city, salary, department)

            val result = data.toList
            source.close()
            result
        }

        employeesTry match {
            case Success(result) =>
                // Filter employees whose salary is greater than 60,000
                val salaryGreaterThan60K = result.filter(employee => employee.salary > 60000)

                println("\nEmployees whose salary is greater than 60,000")
                salaryGreaterThan60K.foreach(println)

                // Filter employees whose department is Finance or Sales
                val financeSalesEmployee = result.filter(employee => (employee.department.equals("Finance") || employee.department.equals("Sales")))

                println("\nEmployees whose department is Finance or Sales")
                financeSalesEmployee.foreach(println)

            case Failure(exception) =>
                println(s"Error reading the file: ${exception.getMessage}")
        }

        employeesTry match {
            case Success(result) =>
                // Creating formatted reports using Map
                val report = result.map(employee => f"Name: ${employee.name} | City: ${employee.city} | Salary: ${employee.salary} | Department: ${employee.department}")
                println("\nFormatted Report:")
                report.foreach(println)

            case Failure(exception) =>
                println(s"Error reading the file: ${exception.getMessage}")
        }

        employeesTry match {
            case Success(result) =>
                // Performing reduce operations to find total salary, average salary and number of employees department wise
                val departmentStats = result.groupBy(_.department).map {
                    case (dept, empList) =>
                        val totalSalary = empList.map(_.salary).sum
                        val averageSalary: Float = totalSalary / empList.size
                        val numberOfEmployees = empList.size

                        (dept, totalSalary, averageSalary, numberOfEmployees)
                }

                println("\nDepartment-wise Salary Report:")
                departmentStats.foreach { 
                    case (dept, total, average, count) =>
                        println(f"Department: $dept, Total Salary: $total%.2f, Average Salary: $average%.2f, Number of Employees: $count")
                }

            case Failure(exception) =>
                println(s"Error reading the file: ${exception.getMessage}")
        }
    }
}

