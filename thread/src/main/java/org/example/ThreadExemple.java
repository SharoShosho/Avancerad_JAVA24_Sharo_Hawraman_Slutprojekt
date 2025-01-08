package org.example;

public class ThreadExemple {
    public static void main(String[] args) {
        MyThread thread1 = new MyThread("Tråd 1");
        MyThread thread2 = new MyThread("Tråd 2");

        thread1.run();
        thread2.start();
    }
}


class MyThread extends Thread{
    private String name;
    public MyThread (String name){
        this.name = name;
    }

    @Override
    public void run(){
        for (int i = 0; i <= 5; i++){
            System.out.println(name + " Kör " + i);
            try {
                Thread.sleep(1000);
            }catch (InterruptedException e){
                throw new RuntimeException(e);
            }
            System.out.println("är klar");
        }

    }
}
