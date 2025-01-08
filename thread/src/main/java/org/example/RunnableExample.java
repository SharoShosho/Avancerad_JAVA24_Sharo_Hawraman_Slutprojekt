package org.example;

public class RunnableExample {
    public static void main(String[] args) {
        Thread thread1=new Thread(new Task("Tråd 1"));
        Thread thread2=new Thread(new Task("Tråd 2"));
        Task thread3 = new Task("Tråd 3");

        thread1.start();
        thread2.start();

    }
}

class Task implements Runnable{

    private String name;
    public Task(String name){
        this.name=name;
    }

    @Override
    public void run() {
        for (int i = 0; i <= 10; i++){
            System.out.println(name + " kör " + i);
            try {
                Thread.sleep(1000);
            }catch (InterruptedException e){
                throw new RuntimeException(e);
            }
            System.out.println("är klar");
        }
    }
}