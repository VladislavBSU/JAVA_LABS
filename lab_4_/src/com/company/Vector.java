package com.company;

import java.util.Random;

public class Vector {
    private int N ;
    private int [] mas ;
    //конструктор
    Vector() {
        N = 0;
        mas = null;
    }
    //конструктор с параметрами
    public Vector( int n ) {
        assert( n > 0 );
        N=n;
        Init(  );
    }


    //коньюкция
    public void Conjunction(int i, int j)
    {
        int k;//число противоположное числу mas[i-1]
        assert(i>=0);
        assert(j>=0);
        if((mas[i-1]==0 && mas[j-1]==0)||( mas[i-1]==0 && mas[j-1]==1 )|| (mas[i-1]==1 && mas[j-1]==0))
        {
            k=0;
            System.out.println("коньюнкция mas["+i+"] и  mas["+j+"] = "+k);
        }

        if(mas[i-1]==1 && mas[j-1]==1)
        {
            k=1;
            System.out.println("коньюнкция mas["+i+"] и  mas["+j+"] = "+k);
        }
    }



    // дизъюнкция
    public void disjunction(int i, int j)
    {
        int k;//число противоположное числу mas[i-1]
        assert(i>=0);
        assert(j>=0);
        if((mas[i-1]==1 && mas[j-1]==1)||( mas[i-1]==0 && mas[j-1]==1 )|| (mas[i-1]==1 && mas[j-1]==0))
        {
            k=1;
            System.out.println("дизъюнкция mas["+i+"] и  mas["+j+"] = "+k);
        }

        if(mas[i-1]==0 && mas[j-1]==0)
        {
            k=0;
            System.out.println("дизъюнкция mas["+i+"] и  mas["+j+"] = "+k);
        }
    }
    public void counter( )
    {
        int nu=0,ed=0;
        for (int i=0;i<N;i++) {
            if(mas[i]==0){ nu+=1;}
            if(mas[i]==1){ed+=1;}
        }
        System.out.println("кол-во нулей = "+nu);
        System.out.println("кол-во единиц = "+ed);
    }


    //отрицание
    public void negation(int i)
    {
        int k;//число противоположное числу mas[i-1]
        assert(i>=0);

        if(mas[i-1]==1)
        {
            k=0;
            System.out.println("отрицание mas["+i+"] = "+k);
        }

        if(mas[i-1]==0)
        {
            k=1;
            System.out.println("отрицание mas["+i+"] = "+k);
        }
    }




    //печать вектора
    public void Print( ) {
        assert( N > 0 );
        for( int i = 0; i < N; i++ ) {

            System.out.print( " " + mas[i] );

        }
        System.out.println();
    }
    //инициализация вектора
    public void Init(  ) {
        assert( N > 0 );

        mas = new int [N];

        Random rnd = new Random() ;
        rnd.setSeed( System.currentTimeMillis() );
        for( int i = 0; i < N; i++ ) {
            int temp = rnd.nextInt();
            if(temp<0)
                temp*=(-1);
            mas[i] = temp % 2;
        }

    }
}
