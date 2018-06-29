package main;

import main.java.NalogServiceWorker;
import java.util.Scanner;


public class TestTask {
    
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        System.out.println("Введите ИНН ЮЛ/ФЛ:");
        String inn = in.nextLine(); //5753063376
        System.out.println(NalogServiceWorker.getDescriptionState(inn));
}
    
}
