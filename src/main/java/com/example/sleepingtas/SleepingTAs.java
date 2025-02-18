
/*
    ========== This Is a Demo Without GUI ==========
*/


//package com.example.sleepingtas;
//
//import java.util.List;
//import java.util.Random;
//import java.util.Scanner;
//import java.util.concurrent.ExecutorService;
//import java.util.concurrent.Executors;
//import java.util.logging.Level;
//import java.util.logging.Logger;
//
//import static java.lang.System.exit;
//import static java.util.concurrent.TimeUnit.SECONDS;
//
//public class SleepingTAs {
//
//    public static void main(String[] args) {
//        Scanner in = new Scanner(System.in);
//        System.out.println(" === Welcome To Sleeping TAs Program === \n\n");
//        System.out.print("Enter Number Of TAs: ");
//        int numOfTAs = in.nextInt();
//        System.out.print("Enter Number Of Waiting Chairs: ");
//        int numOfWaitingChairs = in.nextInt();
//        System.out.print("Enter Number Of Students: ");
//        int numOfStudents = in.nextInt();
//
//        Random r = new Random();
//        TAsRoom room = new TAsRoom(numOfWaitingChairs, numOfTAs, numOfStudents);
//        ExecutorService exec = Executors.newFixedThreadPool(12);
//
//
//        System.out.println("\n\n === TAs Room Is Opened With (" + numOfTAs + ") TAs === \n\n");
//
//        for(int i = 1; i <= numOfTAs; i++){
//            TA ta = new TA(room, i);
//            Thread thTA = new Thread(ta);
//            exec.execute(thTA);
//        }
//
//
//        for(int i = 1; i <= numOfStudents; i++){
//            Student student = new Student(room, i);
//            Thread thStudent = new Thread(student);
//            exec.execute(thStudent);
//
//            double val = r.nextGaussian() * 2000 + 2000;
//            int Delay = Math.abs((int) Math.round(val));
//            try {
//                Thread.sleep(Delay);
//            } catch (InterruptedException ex) {
//                Logger.getLogger(SleepingTAs.class.getName()).log(Level.SEVERE, null, ex);
//            }
//
//        }
//
//
//        List<Student> backLater = room.Backlater();
//        for(int i =0; i<backLater.size(); i++){
//            Student student = backLater.get(i);
//            Thread thStudent = new Thread(student);
//            exec.execute(thStudent);
//
//            double val = r.nextGaussian() * 2000 + 2000;
//            int Delay = Math.abs((int) Math.round(val));
//            try {
//                Thread.sleep(Delay); // Simulate Enter Room Process
//            } catch (InterruptedException ex) {
//                Logger.getLogger(SleepingTAs.class.getName()).log(Level.SEVERE, null, ex);
//            }
//        }
//
//
//        try {
//            exec.awaitTermination(12, SECONDS);
//        } catch (InterruptedException ex) {
//            Logger.getLogger(SleepingTAs.class.getName()).log(Level.SEVERE, null, ex);
//        }
//        exec.shutdown();
//
//        System.out.println("\n\n === TAs Room Is Closed === \n");
//
//
//        room.printSummary();
//        exit(0);
//
//    }
//
//}
