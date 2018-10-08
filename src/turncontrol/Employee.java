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
 * @author nhuytan
 * edit 1
 */
public class Employee {

    public String EmployeeID;
    public String EmpName;
    LocalDateTime  CheckInTime;
    public ArrayList<String> turnList;
    int totalTurn, Total;
    int position;
    boolean active;
    boolean isWorking;

    public boolean isIsWorking() {
        return isWorking;
    }

    public void setIsWorking(boolean isWorking) {
        this.isWorking = isWorking;
    }

    
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

    /**
     *
     * @param CheckInTime
     */
    public void setCheckInTime(LocalDateTime  CheckInTime) {
        this.CheckInTime = CheckInTime;
    }

    public Employee(String EmployeeID, String EmpName, LocalDateTime  CheckInTime) {
        this.EmployeeID = EmployeeID;
        this.EmpName = EmpName;
        this.CheckInTime = CheckInTime;
        this.Total=0;
        this.totalTurn=0;
        this.turnList = new ArrayList<>();
        this.active=true;
        this.isWorking = false;
        this.position= Integer.parseInt(EmployeeID);
        
    }

    public int getTotalTurn() {
        return totalTurn;
    }

    public void setTotalTurn(int TotalTurn) {
        this.totalTurn = TotalTurn;
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
