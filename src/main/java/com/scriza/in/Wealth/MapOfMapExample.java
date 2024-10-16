package com.scriza.in.Wealth;

import java.util.HashMap;
import java.util.Map;
import java.util.*;
//create class MapOfMapExample to create a MapOfMap Example  
public class MapOfMapExample {
    // main() method start  
    public static void main(String[] args) {
        // declare variables  
        int size1 = 0;
        int size2 = 0;
        // create a Map for BCA students that will store students Id and Name  
        Map<Object,String> bcaStudents = new HashMap<Object, String>();
        // create a Map for MCA students that will store students Id and Name
        Map<Object,String> mcaStudents = new HashMap<Object, String>();
        // create Scanner class object to take input from user
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter total number of BCA students.");
        size1 = Integer.parseInt(sc.nextLine());
        System.out.println("Enter total number of MCA students.");
        size2 = Integer.parseInt(sc.nextLine());
        // fill bcaStudents Map by taking input from user          
        for(int i = 1; i <= size1; i++){
            int id = 100;
            String name = "";
            System.out.println("Enter name of " + i + "st student of BCA:");
            name = sc.nextLine();
            System.out.println("Student Id = " + (id+i) + "    Student Name = "+name);
            id = id + i;
            bcaStudents.put(Integer.valueOf(id), name);
        }
        // fill mcaStudents Map by taking input from user          
        for(int i = 1; i <= size2; i++){
            int id = 100;
            String name = "";
            System.out.println("Enter name of " + i + "st student of MCA:");
            name = sc.nextLine();
            System.out.println("Student Id = " + (id+i) + "   Student Name = "+name);
            id = id + i;
            mcaStudents.put(Integer.valueOf(id), name);
        }
        // create a Map to store data of BCA and MCA students   
        Map<String, Object> students = new HashMap<String, Object>();
        students.put("BCA", bcaStudents);
        students.put("MCA", mcaStudents);
        // print students Map  
        System.out.println("Map of Map:   " + students);
        // print elements of students Map
        for (int i = 0; i < students.size(); i++){
            ArrayList<Object> data = new ArrayList<Object>(students.keySet());
            Object obj = data.get(i);
            System.out.println("Course: " + obj + " Students: " + students.get(obj));
        }
        // close Scanner class object  
        sc.close();
    }
} 