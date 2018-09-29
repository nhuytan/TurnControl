/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package turncontrol;

import java.time.LocalDateTime;
import java.util.ArrayList;

/**
 *
 * @author nhuyt
 */
public class Employee {

    public String EmployeeID;
    public String EmpName;
    LocalDateTime  CheckInTime;
    public String[] Turn;
    public ArrayList<String> Turn1;
    int TotalTurn, Total;
    int position;
    boolean active;

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
  

    public String getEmployeeID() {
        return EmployeeID;
    }

    public void setEmployeeID(String EmployeeID) {
        this.EmployeeID = EmployeeID;
    }

    public String getEmpName() {
        return EmpName;
    }

    public void setEmpName(String EmpName) {
        this.EmpName = EmpName;
    }

    public LocalDateTime  getCheckInTime() {
        return CheckInTime;
    }

    public void setCheckInTime(LocalDateTime  CheckInTime) {
        this.CheckInTime = CheckInTime;
    }

    public Employee(String EmployeeID, String EmpName, LocalDateTime  CheckInTime) {
        this.EmployeeID = EmployeeID;
        this.EmpName = EmpName;
        this.CheckInTime = CheckInTime;
        this.Total=0;
        this.TotalTurn=0;
        this.Turn = new String[30];
        this.Turn1 = new ArrayList<>();
        this.active=true;
        this.position= Integer.parseInt(EmployeeID);
        
    }

    public String[] getTurn() {
        return Turn;
    }

    public void setTurn(String[] Turn) {
        this.Turn = Turn;
    }

    public int getTotalTurn() {
        return TotalTurn;
    }

    public void setTotalTurn(int TotalTurn) {
        this.TotalTurn = TotalTurn;
    }

    public int getTotal() {
        return Total;
    }

    public void setTotal(int Total) {
        this.Total = Total;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }
    

}
