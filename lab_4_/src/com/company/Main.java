package com.company;
//в решении для удобства(наглядности) вместо i используется i-1
public class Main {

    public static void main(String[] args) {
	// write your code here
        int n=10;
        Vector v= new Vector();
        System.out.println("________конструктор без параметров:_________");
        v.Init( );
        System.out.print("печать: ");
        v.Print( );
        Vector v1=new Vector(n);
        System.out.println("________конструкор с параметрами:___________");
        System.out.println();
        v1.Init( );
        System.out.print("печать: ");
        v1.Print( );
        System.out.println();
        System.out.println("____кол-во элементов  булева вектора");
        v1.counter( );
        System.out.println();
        System.out.println("____виды операций :");
        System.out.println("1.конъюнкция");
        System.out.println("2.отрицание");
        System.out.println("3.дизъюнкция");
        System.out.println();

        System.out.println("__итог операций :");
         v1.Conjunction(3, 4);
         v1.negation(6);
         v1.disjunction(3, 4);





    }
}
