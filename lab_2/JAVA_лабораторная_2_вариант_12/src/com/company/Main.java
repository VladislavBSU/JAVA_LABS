package com.company;

import java.util.Scanner;

public class Main{
    //разряд сотен --------------------------------
    public static void Hundreds(char c){
        switch (c){
            case '1':
                System.out.print("сто ");
                break;
            case '2':
                System.out.print("двесьте ");
                break;
            case '3':
                System.out.print("триста ");
                break;
            case '4':
                System.out.print("четыреста ");
                break;
            case '5':
                System.out.print("пятьсот ");
                break;
            case '6':
                System.out.print("шестьсот ");
                break;
            case '7':
                System.out.print("семьсот ");
                break;
            case '8':
                System.out.print("восемьсот ");
                break;
            case '9':
                System.out.print("девятьсот ");
                break;
        }
    }
    //----------------------------------------

    // разряд десятков-------------------------
    public static void Desatki(String s){
        switch (s.charAt(0)){
            case '0':
                Edinichki(s.charAt(1));
                break;
            case '1':
                switch (s.charAt(1)){
                    case '0':
                        System.out.println("десять ");
                        break;
                    case '1':
                        System.out.println("одинадцать ");
                        break;
                    case '2':
                        System.out.println("двенадцать ");
                        break;
                    case '3':
                        System.out.println("тринадцать ");
                        break;
                    case '4':
                        System.out.println("четырнадцать ");
                        break;
                    case '5':
                        System.out.println("пятнадцать ");
                        break;
                    case '6':
                        System.out.println("шестнадцать ");
                        break;
                    case '7':
                        System.out.println("семнадцать ");
                        break;
                    case '8':
                        System.out.println("восемнадцать ");
                        break;
                    case '9':
                        System.out.println("девятнадцать ");
                        break;
                }
                break;
            case '2':
                System.out.print("двадцать ");
                Edinichki(s.charAt(1));
                break;
            case '3':
                System.out.print("тридцать ");
                Edinichki(s.charAt(1));
                break;
            case '4':
                System.out.print("сорок ");
                Edinichki(s.charAt(1));
                break;
            case '5':
                System.out.print("пятьдесят ");
                Edinichki(s.charAt(1));
                break;
            case '6':
                System.out.print("шестьдесят ");
                Edinichki(s.charAt(1));
                break;
            case '7':
                System.out.print("семьдесят ");
                Edinichki(s.charAt(1));
                break;
            case '8':
                System.out.print("восемьдесят ");
                Edinichki(s.charAt(1));
                break;
            case '9':
                System.out.print("девяносто ");
                Edinichki(s.charAt(1));
                break;
        }
    }
    //-----------------------------------------------------


    //разряд единиц---------------------------------------
    public static void Edinichki(char c){
        switch (c){
            case '1':
                System.out.println("один ");
                break;
            case '2':
                System.out.println("два ");
                break;
            case '3':
                System.out.println("три ");
                break;
            case '4':
                System.out.println("четыре ");
                break;
            case '5':
                System.out.println("пять ");
                break;
            case '6':
                System.out.println("шесть ");
                break;
            case '7':
                System.out.println("семь ");
                break;
            case '8':
                System.out.println("восемь ");
                break;
            case '9':
                System.out.println("девять ");
                break;
            case '0':
                break;
        }
    }
    //---------------------------------------------------


    public static void Number(String s){
        switch (s.length()){
            case 4:
                System.out.println("тысяча");
                break;
            case 3:
                Hundreds(s.charAt(0));
                Desatki(s.substring(1, 3));
                break;
            case 2:
                Desatki(s.substring(0, 2));
                break;
            case 1:
                Edinichki(s.charAt(0));
                break;
        }
    }

    public static void main(String[] args){

        Scanner input = new Scanner(System.in);

        System.out.print("Введите натуральное число в диапазоне number<=1000 ");
        while(input.hasNextLine())
        {
            String s=input.nextLine();
            if (  (    (Integer.parseInt(s))  >1000) ||((Integer.parseInt(s)<0))) {
                System.out.print("Вы ввели число не в диапазоне number<=1000");
                System.exit(0);
            }
            Number(s);
            System.out.println();
            System.out.print("Введите натуральное число в диапазоне number<=1000 ");

        }
        System.exit(0);
    }

}

