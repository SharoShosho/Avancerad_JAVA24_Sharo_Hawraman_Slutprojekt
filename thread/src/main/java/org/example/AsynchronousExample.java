package org.example;

public class AsynchronousExample {
    public static void main(String[] args) {
        System.out.println("Startar asynkrona uppgiften");
        Thread task1 = new Thread(() -> performTask("Uppgigt1 ", 2000));
        Thread task2 = new Thread(() -> performTask("Uppgigt2 ", 3000));
        Thread task3 = new Thread(() -> performTask("Uppgigt3 ", 4000));

        task1.start();
        task2.start();
        task3.start();

    }

    private static void performTask(String taskname, int duration){
        try{
            System.out.println(taskname + " påbörjad...   ");
            Thread.sleep(duration);
            System.out.println(taskname + " avslutad...");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}

