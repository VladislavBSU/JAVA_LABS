package classes;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.StringTokenizer;

public class User implements Serializable  {
    //Fields
    private int sectionNumber;
    private int colMinets;
    private String nameSport;
    private String nameUser;
    private String nameTreiner;
    private Date startDate;
    private double time;
    private int priceFORminute;



    //  Methods
    public User() {
    }

    public User(int sectionNumber, int colMinets, String nameSport, String nameUser, Date startDate, double time, int priceFORminute) {
        this.sectionNumber = sectionNumber;
        this.colMinets = colMinets;
        this.nameSport = nameSport;
        this.nameUser = nameUser;
        this.startDate = startDate;
        this.time = time;
        this.priceFORminute = priceFORminute;
        this.nameTreiner = nameTreiner;
    }
    public User(String s) throws ParseException
    {
        StringTokenizer str=new StringTokenizer(s," ,");
        if(str.hasMoreTokens())
        {
            this.sectionNumber=Integer.parseInt(str.nextToken());//код секции
            this.colMinets=Integer.parseInt(str.nextToken());//кол-во минут
            this.nameSport=str.nextToken();//вид занятия
            this.nameUser=str.nextToken();//ФИО
            SimpleDateFormat sdf=new SimpleDateFormat("dd.MM.yyyy");
            this.startDate=sdf.parse(str.nextToken());//дата
            this.time=Integer.parseInt(str.nextToken());//время
            this.priceFORminute=Integer.parseInt(str.nextToken());//тариф за минуту
            this.nameTreiner=str.nextToken();//ФИО тренера
        }
    }
    public int getsectionNumber() {
        return sectionNumber;
    }

    public void setsectionNumber(int sectionNumber) {
        this.sectionNumber = sectionNumber;
    }

    public int getcolMinets() {
        return colMinets;
    }

    public void setcolMinets(int colMinets) {
        this.colMinets = colMinets;
    }

    public String getnameSport() {
        return nameSport;
    }

    public void setnameSport(String nameSport) {
        this.nameSport = nameSport;
    }

    public String getnameUser() {
        return nameUser;
    }

    public void setnameUser(String nameUser) {
        this.nameUser = nameUser;
    }

    public String getnameTreiner() {
        return nameTreiner;
    }

    public void setnameTreiner(String nameTreiner) {
        this.nameTreiner = nameTreiner;
    }

    public Date getstartDate() {
        return startDate;
    }

    public void setstartDate(Date startDate) {
        this.startDate = startDate;
    }

    public double gettime() {
        return time;
    }

    public void settime(int time) {
        this.time = time;
    }

    public int getpriceFORminute() {
        return priceFORminute;
    }

    public void setpriceFORminute(int priceFORminute) {
        this.priceFORminute = priceFORminute;
    }


    // "User{sectionNumber=%s, \n DENIS_COOL=%s|}", 1, 2
    // String.format("User, %s ,
    // "SomeString",
    // "SomeString",
    // "SomeString",);

    @Override
    public String toString() {
        return "User{" +
                "sectionNumber=" + sectionNumber +
                ", colMinets=" + colMinets +
                ", nameSport='" + nameSport + '\'' +
                ", nameUser='" + nameUser + '\'' +
                ", startDate=" + startDate +
                ", time=" + time +
                ", priceFORminute=" + priceFORminute +
                ", nameTreiner=" + nameTreiner +
                '}';
    }
}

