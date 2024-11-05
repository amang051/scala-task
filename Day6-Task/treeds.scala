import scala.io.StdIn
import scala.collection.mutable.ListBuffer
import scala.util.control.Breaks._

case class Employee(sno: Int, name: String, city: String)

case class TreeNode(parentName: String, departmentName: String, children: ListBuffer[TreeNode] = ListBuffer.empty[TreeNode], employees: ListBuffer[Employee] = ListBuffer.empty[Employee])

object Main {
    val root = TreeNode("", "Organization")

    def main(args: Array[String]): Unit = {

        println("Tree application started")
        println("\nType 'print' to print the organization tree")
        println("\nTo add a new record, type in required format :: <parent>, <current>, (<sno>, <name>, <city>)")
        println("\nType 'exit' to close the application")
        println()

        var input = StdIn.readLine

        while(!input.equals("exit")) {
            if(input.equals("print")) {
                println()
                printTree(root)
                println()
            } else {
                val pattern = """([a-zA-Z]+), ([a-zA-Z]+), \((\d+), ([a-zA-Z]+), ([a-zA-Z ]+)\)""".r

                input match {
                    case pattern(parent, current, sno, name, city) =>
                        val parentNode = findDepartment(parent, root)
                        if(parentNode.isDefined) {
                            val deptNode = findDepartment(current, root)
                            val employee = new Employee(sno.toInt, name, city)
                            if(deptNode.isDefined) {
                                breakable {
                                    if(!deptNode.get.parentName.equals(parentNode.get.departmentName)) {
                                        println("\nParent name mismatch")
                                        break()
                                    }
                                }
                                deptNode.get.employees += employee
                            } else {
                                val childNode = new TreeNode(parent, current)
                                childNode.employees += employee
                                parentNode.get.children += childNode
                            }
                        } else {
                            println("\nParent is not defined in the Tree")
                        }
                    case _ => 
                        println("\nInvalid input!")
                }
            }
            println("\nType 'print' to print the organization tree")
            println("\nTo add a new record, type in required format :: <parent>, <current>, (<sno>, <name>, <city>)")
            println("\nType 'exit' to close the application")
            println()

            input = StdIn.readLine
        }

        println("\nTree application closed")
    }

    def findDepartment(department: String, root: TreeNode): Option[TreeNode] = {
        if(root == null) return None

        if(root.departmentName.equals(department)) return Some(root)

        root.children.foldLeft(Option.empty[TreeNode]) { (acc, child) =>
            acc.orElse(findDepartment(department, child))
        }
    }

    def printTree(node: TreeNode, indent: String = "", isLast: Boolean = true): Unit = {
        println(s"${indent}${node.departmentName}")

        if (node.employees.nonEmpty) {
            node.employees.foreach { employee =>
                println(s"${indent}    ├── (${employee.sno}, ${employee.name}, ${employee.city})")
            }
        }

        node.children.foreach { child =>
            printTree(child, indent + "    ├── ")
        }
    }
}