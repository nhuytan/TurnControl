/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package turncontrol;

import java.util.ArrayList;
import java.util.Collections;

/**
 *
 * @author nhuytan
 */
// Java program for implementation of Bubble Sort 
public class BubbleSort {

    public void bubbleSortTime(ArrayList<Employee> employee) {
        int n = employee.size();
        for (int i = 0; i < n - 1; i++) {
            for (int j = 0; j < n - i - 1; j++) {
                if (employee.get(i).getCheckInTime().isAfter(employee.get(i + 1).getCheckInTime())) {
                    Collections.swap(employee, i, i + 1);
                }
            }
        }
    }

    public void bubbleSortTotal(ArrayList<Employee> employee) {
        int n = employee.size();
        for (int i = 0; i < n - 1; i++) {
            for (int j = 0; j < n - i - 1; j++) {
                if (employee.get(i).getTotal() > employee.get(i + 1).getTotal()) {
                    Collections.swap(employee, i, i + 1);
                }
            }
        }
    }

    public void bubbleSortTotalTurn(ArrayList<Employee> employee) {
        int n = employee.size();
        for (int i = 0; i < n - 1; i++) {
            for (int j = 0; j < n - i - 1; j++) {
                if (employee.get(i).getTotalTurn()> employee.get(i + 1).getTotalTurn()) {
                    Collections.swap(employee, i, i + 1);
                }
            }
        }
    }

    /* Prints the array */
    public void printArray(int arr[]) {
        int n = arr.length;
        for (int i = 0; i < n; ++i) {
            System.out.print(arr[i] + " ");
        }
        System.out.println();
    }
}
/* This code is contributed by Rajat Mishra */
