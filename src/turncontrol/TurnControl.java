package turncontrol;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;

/**
 *
 * @author nhuytan
 */
public class TurnControl {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        /*
            User input: 
               * Checkin (1) --> enter name --> generate ID, get datetime --> add to arraylist, update list employee(not finish)
               * Checkout (2) --> enter ID --> set status to false
         */
        Scanner input = new Scanner(System.in);
        //Employee[] employee = new Employee[20];
        ArrayList<Employee> employee = new ArrayList<Employee>(20);
        String employeeName, employeeID;
        int size;
        LocalDateTime checkIn, checkOut;
        TableBuilder tbCommandGuide = new TableBuilder();
        tbCommandGuide.addRow("Enter Command: ", "=========", "=========");
        tbCommandGuide.addRow("1-Check In", "2-Check Out", "e-Exit Program");
        tbCommandGuide.addRow("p-Print List", "3-Add Turn", "4-Remove Turn");
        String commandGuide = tbCommandGuide.toString();

        while (true) {
            System.out.println(commandGuide);
            String a = input.next();
            if (a.equals("1")) {
                System.out.println("==> Please input Employee Name:");
                employeeName = input.next();
                size = employee.size();
                employeeID = Integer.toString(size + 1);
                checkIn = LocalDateTime.now();
                employee.add(new Employee(employeeID, employeeName, checkIn));
                System.out.println("Add Employee " + employeeName + " Completed");

            } else if (a.equals("2")) {
                System.out.println("==> Please choose 'EmployeeID' below to check out...");
                printActiveEmployee(employee);
                employeeID = input.next();
                while (!checkID(employeeID, employee)) {
                    System.out.println("==> EmployeeID not found, enter again or 'e'to exit");
                    employeeID = input.next();
                }
                if (employeeID.equals("e")) {
                    System.out.println("==> Check out command not complete");
                } else {

                    int index = findEmployee(employeeID, employee);
                    employee.get(index).setActive(false);
                    System.out.println("==> Employee " + employee.get(index).getEmpName() + " Check Out Completed");
                }
            } else if (a.equals("3")) {
                System.out.println("==> Please choose EmployeeID below to add turn...");
                printActiveEmployee(employee);
                employeeID = input.next();
                while (!checkID(employeeID, employee)) {
                    System.out.println("==> EmployeeID not found, choose again or 'e'to exit");
                    employeeID = input.next();
                }
                if (employeeID.equals("e")) {
                    System.out.println("==> Check out command not complete");
                } else {
                    int index = findEmployee(employeeID, employee);
                    System.out.println("==> Enter amount:");
                    String amount = input.next();
                    System.out.println("==> Enter: '0'-Free Turn ; '1'-Count Turn");
                    String freeTurnFlag = input.next();
                    employee.get(index).Turn1.add(amount);
                    employee.get(index).Turn1.add(freeTurnFlag);
                    System.out.println("Turn list update for " + employee.get(index).getEmpName());
                    System.out.println(getStringTurn(employee.get(index)));
                }
            } // remove turn
            else if (a.equals("4")) {

                int employeeIndex, indexTurn;
                System.out.println("==> Please choose EmployeeID below to remove turn...");
                printActiveEmployee(employee);
                employeeID = input.next();
                while (!checkID(employeeID, employee)) {
                    System.out.println("==> EmployeeID not found, choose again or 'e'to exit");
                    employeeID = input.next();
                }
                if (employeeID.equals("e")) {
                    System.out.println("==> REMOVE COMMAND NOT COMPLETE");
                } else {
                    employeeIndex = findEmployee(employeeID, employee);
                    indexTurn = getIndexTurn(employee.get(employeeIndex));
                    removeTurn(employee.get(employeeIndex), indexTurn);

                    System.out.println("Turn list update for " + employee.get(employeeIndex).getEmpName());
                    System.out.println(getStringTurn(employee.get(employeeIndex)));
                }
            } else if (a.equals("e")) {
                print(employee);
                System.out.println("==> ===PROGRAM EXIT===");
                break;
            } else if (a.equals("p")) {
                print(employee);
            } else {
                System.out.println("==> WRONG COMMAND, TRY AGAIN <==!!");
                System.out.println(commandGuide);
            }
            
            System.out.println("=================================================================");
        }
    }

    public static int getIndexTurn(Employee e) {
        String index;
        System.out.println("Please choose the turn below to remove");
        TableBuilder tbTurnList = new TableBuilder();
        tbTurnList.addRow("Turn No.", "Amount", "Free or Count");
        for (int i = 0; i < (e.Turn1.size() / 2); i++) {
            String flag;
            if (e.Turn1.get(i * 2 + 1).equals("0")) {
                flag = "Free";
            } else {
                flag = "Count";
            }
            tbTurnList.addRow(Integer.toString(i + 1), e.Turn1.get(i*2), flag);
        }
        System.out.println(tbTurnList.toString());
        Scanner input = new Scanner(System.in);
        index = input.next();
        int numOfElement = e.Turn1.size() / 2;
        while (!checkIndex(index,numOfElement)) {
            System.out.println("==> Index not found, choose again or 'e'to exit");
            index = input.next();
        }

        if (index.equals("e")) {
            System.out.println("==> REMOVE TURN COMMAND NOT COMPLETE");
            return 0;
        } else {
            return Integer.parseInt(index);
        }
    }

    public static void removeTurn(Employee e, int index) {

        if (index > 0) {
            e.Turn1.remove((index - 1) * 2);
            e.Turn1.remove((index - 1) * 2);
        }

    }

    public static void updateTurn(ArrayList<Employee> employee, int index, String amount, String freeTurnFlag) {

    }

    public static boolean checkID(String id, ArrayList<Employee> employee) {
        for (int i = 0; i < employee.size(); i++) {
            if (id.equals("e") || id.equals("E")) {
                return true;
            } else if (employee.get(i).getEmployeeID().equals(id) && employee.get(i).isActive()) {
                return true;
            }
        }
        return false;
    }
       public static boolean checkIndex(String index,  int numOfElement) {
     
           if (index.equals("e") || index.equals("E"))
               return true;
           else if (Integer.parseInt(index) < 1 ||Integer.parseInt(index) > numOfElement )
               return false;

           return true;
    }
    
    

    public static int findEmployee(String id, ArrayList<Employee> employee) {
        for (int i = 0; i < employee.size(); i++) {
            if (id.equals("e") || id.equals("E")) {
                return -1;
            } else if (employee.get(i).getEmployeeID().equals(id)) {
                return i;
            }
        }
        return -1;
    }

    public static void print(ArrayList<Employee> employee) {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH:mm:ss");
        System.out.println("\nEmployee Table Details:");
        TableBuilder tb = new TableBuilder();
        tb.addRow("EmployeeID", "EmployeeName", "CheckInTime", "Total", "Status", "Position", "Turn_List");
        //System.out.println("EmployeeID\tEmployeeName\tCheckInTime\tTotal\tStatus\tPosition\n");
        for (int i = 0; i < employee.size(); i++) {
            String turnList = getStringTurn(employee.get(i));
            tb.addRow(employee.get(i).getEmployeeID(), employee.get(i).getEmpName(), dtf.format(employee.get(i).getCheckInTime()), Integer.toString(employee.get(i).getTotal()), Boolean.toString(employee.get(i).isActive()), Integer.toString(employee.get(i).position), turnList);
        }
        System.out.println(tb.toString());
    }

    //get the output string of Turn of specific employee
    public static String getStringTurn(Employee e) {
        String output = "";
        for (int i = 0; i < e.Turn1.size(); i += 2) {
            if (e.Turn1.get(i).compareTo("") == 1) {
                return output;
            } else {
                if (e.Turn1.get(i + 1).compareTo("1") == 0) {
                    output = output + e.Turn1.get(i) + ",";
                } else {
                    output = output + "(" + e.Turn1.get(i) + "),";
                }
            }
        }
        // if(output.substring(output.length()-1, output.length()-1).compareTo(",")==1)
        //      output = output.substring(0, output.length()-2);
        return output;
    }

    public static void printActiveEmployee(ArrayList<Employee> employee) {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH:mm:ss");
        System.out.println("\nActive Employee\n");
        TableBuilder tb = new TableBuilder();
        tb.addRow("EmployeeID", "EmployeeName");
        //System.out.println("EmployeeID\tEmployeeName\tCheckInTime\tTotal\tStatus\tPosition\n");
        for (int i = 0; i < employee.size(); i++) {
            if (employee.get(i).isActive()) {
                tb.addRow(employee.get(i).getEmployeeID(), employee.get(i).getEmpName());
            }
        }
        System.out.println(tb.toString());
    }
}
