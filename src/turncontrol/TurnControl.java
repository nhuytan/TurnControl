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
        tbCommandGuide.addRow("5-Revert Working Status", "----", "----");
        String commandGuide = tbCommandGuide.toString();

        while (true) {
            System.out.println(commandGuide);
            String a = input.next();
            if (a.equals("1")) {
                command1(employee, input);
            } else if (a.equals("2")) {
                command2(employee, input);
            } else if (a.equals("3")) {
                command3(employee, input);
            } else if (a.equals("4")) {
                command4(employee, input);
            } else if (a.equals("5")) {
                command5(employee, input);
            } else if (a.toUpperCase().equals("E")) {
                //print(employee);
                System.out.println("==> ===PROGRAM EXIT===");
                break;
            } else if (a.toUpperCase().contains("P")) {
                if (a.toUpperCase().contains("ACTIVE")) {
                    if (a.toUpperCase().contains("INACTIVE")) {
                        print(employee, false);
                    } else {
                        print(employee, true);
                    }
                } else {
                    print(employee);
                }
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
        for (int i = 0; i < (e.turnList.size() / 2); i++) {
            String flag;
            if (e.turnList.get(i * 2 + 1).equals("0")) {
                flag = "Free";
            } else {
                flag = "Count";
            }
            tbTurnList.addRow(Integer.toString(i + 1), e.turnList.get(i * 2), flag);
        }
        System.out.println(tbTurnList.toString());
        Scanner input = new Scanner(System.in);
        index = input.next();
        int numOfElement = e.turnList.size() / 2;
        while (!checkIndex(index, numOfElement)) {
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
            e.turnList.remove((index - 1) * 2);
            e.turnList.remove((index - 1) * 2);
        }
    }

    public static void updateTurn(ArrayList<Employee> employee, int index, String amount, String freeTurnFlag) {

    }

    public static boolean checkID(String id, ArrayList<Employee> employee) {
        if (employee.size() == 0) {
            return true;
        } else {
            for (int i = 0; i < employee.size(); i++) {
                if (id.equals("e") || id.equals("E")) {
                    return true;
                } else if (employee.get(i).getEmployeeID().equals(id) && employee.get(i).isActive()) {
                    return true;
                }
            }
        }
        return false;
    }

    public static boolean checkIndex(String index, int numOfElement) {

        if (index.equals("e") || index.equals("E")) {
            return true;
        } else if (Integer.parseInt(index) < 1 || Integer.parseInt(index) > numOfElement) {
            return false;
        }
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

// COMMAND CHECK-IN
    public static void command1(ArrayList<Employee> employee, Scanner input) {
        System.out.println("==> Please input Employee Name:");
        String employeeName = input.next();
        int size = employee.size();
        String employeeID = Integer.toString(size + 1);
        LocalDateTime checkIn = LocalDateTime.now();
        employee.add(new Employee(employeeID, employeeName, checkIn));
        System.out.println("==> Employee \"" + employeeName + "\" Check-In Completed");
    }

// COMMAND CHECK-OUT
    public static void command2(ArrayList<Employee> employee, Scanner input) {
        if (employee.size() > 0) {
            System.out.println("==> Please choose 'EmployeeID' below to check out...");
            printActiveEmployee(employee);
            String employeeID = input.next();
            while (!checkID(employeeID, employee)) {
                System.out.println("==> EmployeeID not found, enter again or 'e'to exit");
                employeeID = input.next();
            }
            if (employeeID.equals("e")) {
                System.out.println("==> Check out command not complete");
            } else {
                int index = findEmployee(employeeID, employee);
                employee.get(index).setActive(false);
                System.out.println("==> Employee \"" + employee.get(index).getEmpName()
                        + "\" Check-Out Completed");
            }
        } else {
            System.out.println("==> DON'T HAVE ANY EMPLOYEE TO CHECK OUT");
        }
    }

// COMMAND ADD TURN
    public static void command3(ArrayList<Employee> employee, Scanner input) {
        System.out.println("==> Please choose EmployeeID below to add turn...");
        printActiveEmployee(employee);
        String employeeID = input.next();
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
            employee.get(index).turnList.add(amount);
            employee.get(index).turnList.add(freeTurnFlag);
            System.out.println("Turn list update for " + employee.get(index).getEmpName());
            System.out.println(getStringTurn(employee.get(index)));
            UpdateTotal(employee.get(index));
            System.out.println("==> Add " + amount + "$ to \"" + employee.get(index).getEmpName() + "\" Successful");
        }
    }

// COMMAND REMOVE TURN
    public static void command4(ArrayList<Employee> employee, Scanner input) {
        int employeeIndex, indexTurn;
        System.out.println("==> Please choose EmployeeID below to remove turn...");
        printActiveEmployee(employee);
        String employeeID = input.next();
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
            UpdateTotal(employee.get(employeeIndex));
            System.out.println("Turn list update for " + employee.get(employeeIndex).getEmpName());
            System.out.println(getStringTurn(employee.get(employeeIndex)));
        }
    }

// COMMAND REVERT WORKING STATUS
    public static void command5(ArrayList<Employee> employee, Scanner input) {
        if (employee.size() > 0) {
            int employeeIndex, indexTurn;
            System.out.println("==> Please choose EmployeeID below to change working status...");
            printActiveEmployee(employee);
            String employeeID = input.next();
            while (!checkID(employeeID, employee)) {
                System.out.println("==> EmployeeID not found, choose again or 'e'to exit");
                employeeID = input.next();
            }
            if (employeeID.equals("e")) {
                System.out.println("==> REMOVE COMMAND NOT COMPLETE");
            } else {
                employeeIndex = findEmployee(employeeID, employee);
                if (employee.get(employeeIndex).isIsWorking()) {
                    setWorking(employee.get(employeeIndex), false);
                } else {
                    setWorking(employee.get(employeeIndex), true);
                }
                System.out.println("Working status of employee \"" + employee.get(employeeIndex).getEmpName() + "\" change from \"" + !employee.get(employeeIndex).isIsWorking() + "\" to \"" + employee.get(employeeIndex).isIsWorking() + "\"");
            }
        } else {
            System.out.println("==> DON'T HAVE ANY EMPLOYEE SIGN IN");
        }
    }

// HELP FUNCTION: Update Total for single Employee
    public static void UpdateTotal(Employee e) {
        e.setTotal(0);
        e.setTotalTurn(0);
        if (!e.turnList.isEmpty()) {
            for (int i = 0; i < e.turnList.size(); i += 2) {
                e.setTotal(e.getTotal() + Integer.parseInt(e.turnList.get(i)));
                if (e.turnList.get(i + 1).compareTo("0") == 1) {
                    e.setTotalTurn(e.getTotalTurn() + Integer.parseInt(e.turnList.get(i)));
                }
            }
        }
    }

// HELP FUNCTION: SET WORKING STATUS FOR SPECIFIC EMPLOYEE
    public static void setWorking(Employee e, boolean status) {
        e.setIsWorking(status);
    }

// HELP FUNCTION: GET TURN LIST OF SPECIFIC EMPLOYEE
    public static String getStringTurn(Employee e) {
        String output = "";
        for (int i = 0; i < e.turnList.size(); i += 2) {
            if (e.turnList.get(i).compareTo("") == 1) {
                return output;
            } else {
                if (e.turnList.get(i + 1).compareTo("1") == 0) {
                    output = output + e.turnList.get(i) + ",";
                } else {
                    output = output + "(" + e.turnList.get(i) + "),";
                }
            }
        }
        return output;
    }

// DISPLAY FUNCTION - PRINT ACTIVE EMPLOYEE LIST
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
        // command
    }

// DISPLAY FUNCTION - PRINT EMPLOYEE LIST WITH FULL INFORMATION 
    public static void print(ArrayList<Employee> employee) {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH:mm:ss");
        System.out.println("\nEmployee Table Details:");
        TableBuilder tb = new TableBuilder();
        tb.addRow("EmployeeID", "EmployeeName", "CheckInTime", "Total", "Total_Turn", "Is_Working", "Status", "Position", "Turn_List");
        //System.out.println("EmployeeID\tEmployeeName\tCheckInTime\tTotal\tStatus\tPosition\n");
        for (int i = 0; i < employee.size(); i++) {
            String turnList = getStringTurn(employee.get(i));
            tb.addRow(employee.get(i).getEmployeeID(), employee.get(i).getEmpName(), dtf.format(employee.get(i).getCheckInTime()), Integer.toString(employee.get(i).getTotal()), Integer.toString(employee.get(i).getTotalTurn()), Boolean.toString(employee.get(i).isIsWorking()), Boolean.toString(employee.get(i).isActive()), Integer.toString(employee.get(i).position), turnList);
        }
        System.out.println(tb.toString());
    }

    // @overload DISPLAY FUNCTION - PRINT EMPLOYEE LIST WITH FULL INFORMATION on condition STATUS
    public static void print(ArrayList<Employee> employee, boolean status) {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH:mm:ss");
        System.out.println("\nEmployee Table Details:");
        TableBuilder tb = new TableBuilder();
        tb.addRow("EmployeeID", "EmployeeName", "CheckInTime", "Total", "Total_Turn", "Is_Working", "Status", "Position", "Turn_List");
        //System.out.println("EmployeeID\tEmployeeName\tCheckInTime\tTotal\tStatus\tPosition\n");
        for (int i = 0; i < employee.size(); i++) {
            if (employee.get(i).isActive() == status) {
                String turnList = getStringTurn(employee.get(i));
                tb.addRow(employee.get(i).getEmployeeID(), employee.get(i).getEmpName(), dtf.format(employee.get(i).getCheckInTime()), Integer.toString(employee.get(i).getTotal()), Integer.toString(employee.get(i).getTotalTurn()), Boolean.toString(employee.get(i).isIsWorking()), Boolean.toString(employee.get(i).isActive()), Integer.toString(employee.get(i).position), turnList);
            }
        }
        System.out.println(tb.toString());
    }
}
