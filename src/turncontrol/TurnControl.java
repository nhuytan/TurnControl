package turncontrol;

import java.time.LocalDateTime;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Scanner;
import javafx.util.converter.LocalDateTimeStringConverter;

/**
 *
 * @author nhuytan add collaborator lanhsunam987
 * @version : 1.1
 */
public class TurnControl {

    public static final int STEP_TURN = 15;

    public static void main(String[] args) {

        String password = "nhuytan";
        LocalDateTime aDateTime = LocalDateTime.of(2018, Month.OCTOBER, 31, 19, 30, 40);
        
        LocalDateTime now = LocalDateTime.now();

        if(now.isAfter(aDateTime))
        {
        Scanner input = new Scanner(System.in);
        //Employee[] employee = new Employee[20];
        ArrayList<Employee> employee = new ArrayList<>();
        ArrayList<ArrayList<Employee>> arrOfArrEmployee = new ArrayList<>();
        String employeeName, employeeID;
        int size;
        LocalDateTime checkIn, checkOut;
        TableBuilder tbCommandGuide = new TableBuilder();
        tbCommandGuide.addRow("Enter Command: ", "=========", "=========");
        tbCommandGuide.addRow("1-Check In", "2-Check Out", "e-Exit Program");
        tbCommandGuide.addRow("3-Add Turn", "4-Remove Turn", "5-Revert Working Status");
        tbCommandGuide.addRow("6-Update Turn", "p-print");
        String commandGuide = tbCommandGuide.toString();

        while (true) {
            System.out.println(commandGuide);
            String a = input.next();
            if (a.equals("1")) {
                command1(employee, input);
                updatePosition(employee, arrOfArrEmployee);
                //print(arrOfArrEmployee);
            } else if (a.equals("2")) {
                command2(employee, input);
                updatePosition(employee, arrOfArrEmployee);
            } else if (a.equals("3")) {
                command3(employee, input);
                updatePosition(employee, arrOfArrEmployee);
            } else if (a.equals("4")) {
                command4(employee, input);
                updatePosition(employee, arrOfArrEmployee);
            } else if (a.equals("5")) {
                command5(employee, input);
                updatePosition(employee, arrOfArrEmployee);
            } else if (a.equals("6")) {
                command6(employee, input);
                updatePosition(employee, arrOfArrEmployee);
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
                    //updatePosition(employee, arrOfArrEmployee);
                    print(arrOfArrEmployee);
                }
            } else {
                System.out.println("==> WRONG COMMAND, TRY AGAIN <==!!");
                System.out.println(commandGuide);
            }
            System.out.println("=================================================================");
        }
        }
        else
            System.out.println("exit");
    }

// HELP FUNCTION: show the index of Turn need to be edited, it differs compare with index show in console
/*
array 01 23 45 67
index show  index_in_array
User choose Function return
1           0
2           2
3           4
4           6
 */
public static int getIndexTurn(Employee e) {
        String index;
        System.out.println("Please choose the turn below to \"remove/edit\"");
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
            System.out.println("==> Index not found, choose again or 'E'to exit");
            index = input.next();
        }
        if (index.toUpperCase().equals("E")) {
            System.out.println("==> REMOVE TURN COMMAND NOT COMPLETE");
            return -1;
        } else {
            return (Integer.parseInt(index) - 1) * 2;
        }
    }

    public static void removeTurn(Employee e, int index) {
        e.turnList.remove(index);
        e.turnList.remove(index);
    }

// HELP FUNCTION: .......
    public static boolean checkID(String id, ArrayList<Employee> employee) {
        if (employee.isEmpty()) {
            return true;
        } else {
            for (int i = 0; i < employee.size(); i++) {
                if (id.toUpperCase().equals("E")) {
                    return true;
                } else if (employee.get(i).getEmployeeID().equals(id) && employee.get(i).isActive()) {
                    return true;
                }
            }
        }
        return false;
    }

// HELP FUNCTION: .......
    public static boolean checkIndex(String index, int numOfElement) {

        if (index.toUpperCase().equals("E")) {
            return true;
        } else if (Integer.parseInt(index) < 1 || Integer.parseInt(index) > numOfElement) {
            return false;
        }
        return true;
    }

// HELP FUNCTION: .......
    public static int findEmployee(String id, ArrayList<Employee> employee) {
        for (int i = 0; i < employee.size(); i++) {
            if (id.toUpperCase().equals("E")) {
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
        //updatePosition(employee, arrOfArrEmployee);

    }

// COMMAND CHECK-OUT
    public static void command2(ArrayList<Employee> employee, Scanner input) {
        if (employee.size() > 0) {
            System.out.println("==> Please choose 'EmployeeID' below to check out...");
            printActiveEmployee(employee);
            String employeeID = input.next();
            while (!checkID(employeeID, employee)) {
                System.out.println("==> EmployeeID not found, enter again or 'E'to exit");
                employeeID = input.next();
            }
            if (employeeID.toUpperCase().equals("E")) {
                System.out.println("==> Check out command not complete");
            } else {
                int index = findEmployee(employeeID, employee);
                employee.get(index).setActive(false);
                System.out.println("==> Employee \"" + employee.get(index).getEmpName()
                        + "\" Check-Out Completed");
            }
        } else {
            System.out.println("==> DON'T HAVE ANY EMPLOYEE SIGN IN");
        }

        //updatePosition(employee);
    }

// COMMAND ADD TURN
    public static void command3(ArrayList<Employee> employee, Scanner input) {

        if (employee.size() > 0) {
            System.out.println("==> Please choose EmployeeID below to add turn...");
            printActiveEmployee(employee);
            String employeeID = input.next();
            while (!checkID(employeeID, employee)) {
                System.out.println("==> EmployeeID not found, choose again or 'E'to exit");
                employeeID = input.next();
            }
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
        } else {
            //printActiveEmployee(employee);
            System.out.println("==> DON'T HAVE ANY EMPLOYEE SIGN IN");
        }
    }

// COMMAND REMOVE TURN
    public static void command4(ArrayList<Employee> employee, Scanner input) {
        if (employee.size() > 0) {
            int employeeIndex, indexTurn;
            System.out.println("==> Please choose EmployeeID below to remove turn...");
            printActiveEmployee(employee);
            String employeeID = input.next();
            while (!checkID(employeeID, employee)) {
                System.out.println("==> EmployeeID not found, choose again or 'E'to exit");
                employeeID = input.next();
            }
            if (employeeID.toUpperCase().equals("E")) {
                System.out.println("==> REMOVE COMMAND NOT COMPLETE");
            } else {
                employeeIndex = findEmployee(employeeID, employee);
                indexTurn = getIndexTurn(employee.get(employeeIndex));
                removeTurn(employee.get(employeeIndex), indexTurn);
                UpdateTotal(employee.get(employeeIndex));
                System.out.println("Turn list update for " + employee.get(employeeIndex).getEmpName());
                System.out.println(getStringTurn(employee.get(employeeIndex)));
            }
        } else {
            //printActiveEmployee(employee);
            System.out.println("==> DON'T HAVE ANY EMPLOYEE SIGN IN");
        }
    }

// COMMAND REVERT WORKING STATUS
    public static void command5(ArrayList<Employee> employee, Scanner input) {
        if (employee.size() > 0) {
            int employeeIndex;
            System.out.println("==> Please choose EmployeeID below to change working status...");
            printActiveEmployee(employee);
            String employeeID = input.next();
            while (!checkID(employeeID, employee)) {
                System.out.println("==> EmployeeID not found, choose again or 'E'to exit");
                employeeID = input.next();
            }
            if (employeeID.toUpperCase().equals("E")) {
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

// COMMAND EDIT TURN
    public static void command6(ArrayList<Employee> employee, Scanner input) {

        if (employee.size() > 0) {
            System.out.println("==> Please choose EmployeeID below to EDIT turn...");
            printActiveEmployee(employee);
            String employeeID = input.next();
            while (!checkID(employeeID, employee)) {
                System.out.println("==> EmployeeID not found, choose again or 'E'to exit");
                employeeID = input.next();
            }
            if (employeeID.toUpperCase().equals("E")) {
                System.out.println("==> Command not complete");
            } else {
                int index = findEmployee(employeeID, employee);
                int indexTurn = getIndexTurn(employee.get(index));
                System.out.println("==> Enter newamount:");
                String amount = input.next();
                System.out.println("==> Enter: '0'-Free Turn ; '1'-Count Turn");
                String freeTurnFlag = input.next();

                employee.get(index).turnList.set(index, amount);
                employee.get(index).turnList.set((index + 1), freeTurnFlag);

                System.out.println("Turn list update for " + employee.get(index).getEmpName());
                System.out.println(getStringTurn(employee.get(index)));
                UpdateTotal(employee.get(index));
                System.out.println("==> Update " + amount + "$ to \"" + employee.get(index).getEmpName() + "\" Successful");
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

// HELP FUNCTION - GET INDEX BY VALUE 
    public static int getIndexByValue(ArrayList<Employee> e, int value) {
        for (int i = 0; i < e.size(); i++) {
            if (e.get(i).getPosition() == value) {
                return i;
            }
        }
        return 0;
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

    public static void printAddr(ArrayList<Employee> e) {
        for (int i = 0; i < e.size(); i++) {
            System.out.println("Employee: " + e.get(i).getEmpName() + "  Index Group: " + e.get(i).getIndexGroup()
                    + "  POSITION " + (i + 1) + " :" + System.identityHashCode(e.get(i)));
        }
    }

// DISPLAY FUNCTION - PRINT EMPLOYEE LIST WITH FULL INFORMATION 
    public static void print(ArrayList<ArrayList<Employee>> employee) {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH:mm:ss");
        System.out.println("\nEmployee Table Details:");
        TableBuilder tb = new TableBuilder();
        tb.addRow("EmployeeID", "EmployeeName", "CheckInTime", "Total", "Total_Turn", "Is_Working", "Status", "Position", "Turn_List", "Index_Group");
        for (int j = 0; j < employee.size(); j++) {
            for (int i = 0; i < employee.get(j).size(); i++) {
                int index = i;
                String turnList = getStringTurn(employee.get(j).get(index));
                tb.addRow(employee.get(j).get(index).getEmployeeID(), employee.get(j).get(index).getEmpName(),
                        dtf.format(employee.get(j).get(index).getCheckInTime()),
                        Integer.toString(employee.get(j).get(index).getTotal()),
                        Integer.toString(employee.get(j).get(index).getTotalTurn()),
                        Boolean.toString(employee.get(j).get(index).isIsWorking()),
                        Boolean.toString(employee.get(j).get(index).isActive()),
                        Integer.toString(employee.get(j).get(index).position), turnList,
                        Integer.toString(employee.get(j).get(index).getIndexGroup()));
            }
        }
        System.out.println(tb.toString());
    }

// @overload DISPLAY FUNCTION - PRINT EMPLOYEE LIST WITH FULL INFORMATION on condition STATUS
    public static void print(ArrayList<Employee> employee, boolean status) {

        if (status) {
            ArrayList<Employee> tmpActive = new ArrayList<>();
            for (int i = 0; i < employee.size(); i++) {
                if (employee.get(i).isActive()) {
                    tmpActive.add(employee.get(i));
                }
            }
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH:mm:ss");
            System.out.println("\nEmployee Table Details:");
            TableBuilder tb = new TableBuilder();
            tb.addRow("EmployeeID", "EmployeeName", "CheckInTime", "Total", "Total_Turn", "Is_Working", "Status", "Position", "Turn_List");
            for (int i = 0; i < tmpActive.size(); i++) {
                int index = getIndexByValue(tmpActive, i + 1);
                String turnList = getStringTurn(tmpActive.get(index));
                tb.addRow(tmpActive.get(index).getEmployeeID(),
                        tmpActive.get(index).getEmpName(),
                        dtf.format(tmpActive.get(index).getCheckInTime()),
                        Integer.toString(tmpActive.get(index).getTotal()),
                        Integer.toString(tmpActive.get(index).getTotalTurn()),
                        Boolean.toString(tmpActive.get(index).isIsWorking()),
                        Boolean.toString(tmpActive.get(index).isActive()),
                        Integer.toString(tmpActive.get(index).position),
                        turnList);
            }
            System.out.println(tb.toString());
        } else {
            ArrayList<Employee> tmpInActive = new ArrayList<>();
            for (int i = 0; i < employee.size(); i++) {
                if (!employee.get(i).isActive()) {
                    tmpInActive.add(employee.get(i));
                }
            }
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH:mm:ss");
            System.out.println("\nEmployee Table Details:");
            TableBuilder tb = new TableBuilder();
            tb.addRow("EmployeeID", "EmployeeName", "CheckInTime", "Total", "Total_Turn", "Is_Working", "Status", "Position", "Turn_List");
            for (int i = 0; i < tmpInActive.size(); i++) {
                int index = getIndexByValue(tmpInActive, employee.size() - tmpInActive.size() + 1);
                String turnList = getStringTurn(tmpInActive.get(index));
                tb.addRow(tmpInActive.get(index).getEmployeeID(),
                        tmpInActive.get(index).getEmpName(),
                        dtf.format(tmpInActive.get(index).getCheckInTime()),
                        Integer.toString(tmpInActive.get(index).getTotal()),
                        Integer.toString(tmpInActive.get(index).getTotalTurn()),
                        Boolean.toString(tmpInActive.get(index).isIsWorking()),
                        Boolean.toString(tmpInActive.get(index).isActive()),
                        Integer.toString(tmpInActive.get(index).position),
                        turnList);
            }
            System.out.println(tb.toString());
        }
    }

// HELP FUNCTION - UPDATE POSITION BASE ON: TOTALTURN, CHECK-IN TIME
/*
    Pseudo Code:
    1. Count number of member (active+inactive)
    2. Grouping active, unactive (set position base on check-in time)
    3. In group active, group by employee busy or free (base on isWorking value)
    4. Group busy: label base on TotalTurn
    5. The remain group:
        a. sort by total
        b. index sub-group by step turn value
        c. sort by check_in time in each index sub group
     */
    public static void updatePosition(ArrayList<Employee> employee,
            ArrayList<ArrayList<Employee>> arrOfArrEmployee) {
// total 10, active 6 , inactive 4
// Get active, inactive number
        int numberOfEmployee = employee.size();
        int numberActive = 0;
        int numberInActive = 0;
        int numberBusyWorker = 0;
        int numberFreeWorker = 0;
        BubbleSort b = new BubbleSort();
        for (int i = 0; i < numberOfEmployee; i++) {
            if (employee.get(i).isActive()) {
                numberActive++;
                if (employee.get(i).isIsWorking()) {
                    numberBusyWorker++;
                }
            } else {
                numberInActive++;
            }
        }
        numberFreeWorker = numberActive - numberBusyWorker;

// Process Free Worker 
        if (numberFreeWorker > 0) {
            ArrayList<Employee> tmpFreeWorker = new ArrayList<>(numberFreeWorker);
            for (int i = 0; i < employee.size(); i++) {
                if (employee.get(i).isActive() && !employee.get(i).isIsWorking()) {
                    tmpFreeWorker.add(employee.get(i));
                }
            }
            //System.out.println("\nBEFORE SORT:");
            //printAddr(tmpFreeWorker);
            b.bubbleSortTotalTurn(tmpFreeWorker);

//index group by Step_Turn
            int tmpIndexGroup = 1;
            if (tmpFreeWorker.size() > 0) {
                tmpFreeWorker.get(0).setIndexGroup(tmpIndexGroup);
                //System.out.println("Employee: " + tmpFreeWorker.get(0).getEmpName() + " total_Turn: " + tmpFreeWorker.get(0).getTotalTurn());
                if (tmpFreeWorker.size() > 1) {
                    for (int i = 1; i < tmpFreeWorker.size(); i++) {
                        if ((tmpFreeWorker.get(i).getTotalTurn() - tmpFreeWorker.get(i - 1).getTotalTurn()) >= 15) {
                            tmpIndexGroup++;
                            tmpFreeWorker.get(i).setIndexGroup(tmpIndexGroup);
                        } else {
                            tmpFreeWorker.get(i).setIndexGroup(tmpIndexGroup);
                        }
                        //System.out.println("Employee: " + tmpFreeWorker.get(i).getEmpName() + " total_Turn: " + tmpFreeWorker.get(i).getTotalTurn());
                    }
                }
            }
            //System.out.println("\nAFTER SORT:");
            //printAddr(tmpFreeWorker);

            /*=====================================================================================*/
            //arrOfArrEmployee = new ArrayList<ArrayList<Employee>>(tmpIndexGroup);
            arrOfArrEmployee.clear();
            if (tmpFreeWorker.size() > 0) {
                ArrayList<Employee> tmp = new ArrayList<Employee>();
                tmp.add(tmpFreeWorker.get(0));
                for (int i = 1; i < tmpFreeWorker.size(); i++) {
                    if (tmpFreeWorker.get(i).getIndexGroup() != tmpFreeWorker.get(i - 1).getIndexGroup()) {
                        arrOfArrEmployee.add(tmp);
                        System.out.println("Added group: " + arrOfArrEmployee.size() + " && with " + tmp.size() + " elements");
                        tmp = new ArrayList<Employee>();
                        tmp.add(tmpFreeWorker.get(i));
                    } else {
                        tmp.add(tmpFreeWorker.get(i));
                    }
                }
                arrOfArrEmployee.add(tmp);
                System.out.println("Added last group: " + arrOfArrEmployee.size() + " && with " + tmp.size() + " elements");
            }

            int position = 1;
            for (int i = 0; i < arrOfArrEmployee.size(); i++) {
                b.bubbleSortTime(arrOfArrEmployee.get(i));
                for (int j = 0; j < arrOfArrEmployee.get(i).size(); j++) {
                    arrOfArrEmployee.get(i).get(j).setPosition(position);
                    position++;
                }
            }

// Create tmp array of busy worker and sort by total , index position
// Process Busy worker array
            if (numberBusyWorker > 0) {
                ArrayList<Employee> tmpBusyWorker = new ArrayList<>(numberBusyWorker);
                for (int i = 0; i < employee.size(); i++) {
                    if (employee.get(i).isActive() && employee.get(i).isIsWorking()) {
                        tmpBusyWorker.add(employee.get(i));
                    }
                }
                b.bubbleSortTotal(tmpBusyWorker);
                //set Position
                for (int i = 0; i < tmpBusyWorker.size(); i++) {
                    tmpBusyWorker.get(i).setPosition(numberActive - numberBusyWorker + i + 1);
                }
                arrOfArrEmployee.add(tmpBusyWorker);
            }

//Create tmp array of inactive and sort inactive & index  position 
//Process Inactive worker array
            if (numberInActive > 0) {
                ArrayList<Employee> tmpInActive = new ArrayList<>(numberInActive);
                for (int i = 0; i < employee.size(); i++) {
                    if (employee.get(i).isActive() == false) {
                        tmpInActive.add(employee.get(i));
                    }
                }
                b.bubbleSortTime(tmpInActive);
                for (int i = 0; i < tmpInActive.size(); i++) {
                    tmpInActive.get(i).setPosition(i + 1 + numberActive);
                }
                arrOfArrEmployee.add(tmpInActive);
            }
        }

        //print(arrOfArrEmployee);
    }
}
