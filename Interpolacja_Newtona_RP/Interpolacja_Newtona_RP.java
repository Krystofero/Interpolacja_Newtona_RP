package Interpolacja_Newtona_RP;

import java.io.IOException;
import java.util.Scanner;

import static java.lang.Math.pow;

public class Interpolacja_Newtona_RP {
    private int n; //ilość zmiennych(x'ów) (czyli mamy x0, x1 , x2 ... xn-1)
    private int k; //liczba rzędów
    private float[][]tabi; //tablica ilorazów różniczkowych
    private float[]tabx; //tablica x'ów
    private float[]taby; //tablica f(x) (wartości funkcji)

    private float h; //np. h = 1    (h =𝑥𝑖+1 − 𝑥𝑖)

    Interpolacja_Newtona_RP(int n) throws IOException {
        this.n =n;
        this.k = n-1;
        this.tabx = new float[n];
        this.taby = new float[n];
        this.tabi = new float[this.k][this.k];

        WprowadzWartosci();
        ObliczTabi();
        WypiszTabi();
        WypiszWielomian();
        ObliczWielomian();
    }

    public void WprowadzWartosci(){
        Scanner sc = new Scanner(System.in);
        for(int x=0; x<n; x++){
            System.out.println("Podaj wartość x"+x + " : ");
            this.tabx[x] = sc.nextFloat();
            System.out.println("Podaj wartość f(x"+x + ") : ");
            this.taby[x] = sc.nextFloat();
        }
        this.h = tabx[1] - tabx[0];
    }

    public void ObliczTabi(){
        System.out.println("Wyliczam tablicę ilorazów różnicowych: ...");
        for(int y=0; y<k; y++){//I.rząd
            this.tabi[y][0] = (taby[y+1] - taby[y]);
        }
        for(int x=1; x<k; x++){//kolejne rzędy
            for(int y=0; y<k-x;y++){
                this.tabi[y][x] = (tabi[y+1][x-1] - tabi[y][x-1]);
            }
        }
    }

    public void WypiszTabi(){
        System.out.println("Wypisuję tablicę ilorazów różnicowych: ");
        System.out.print("xi\t|\tf(xi)\t|\t");
        for(int x = 1; x <= k; x++)  System.out.print(x + "-rzędu\t|\t");
        System.out.print(System.lineSeparator());

        for(int x=0; x<k; x++){
            System.out.print(tabx[x] + "\t|\t" + taby[x] + "\t|\t");
            for(int y=0; y<k-x;y++){
                System.out.print(this.tabi[x][y] + "\t|\t");
            }
            System.out.print(System.lineSeparator());
            if(x==k-1){
                System.out.print(tabx[x+1] + "\t|\t" + taby[x+1] + "\t|\t");
            }
        }
        System.out.print(System.lineSeparator());
    }
    public void WypiszWielomian() {
        System.out.println("Wypisuję wielomian: ");
        String s1 = "W(x) = " + taby[0];
        for(int x=0; x<k; x++){
             s1+= " + (" + tabi[0][x] + "/(" + silnia_rekurencyjna(x+1)+ "*" +pow(h,x+1) + "))";
            for(int y=0; y<=x;y++){
                s1+= "(x";
                s1+= "-" + tabx[y] + ")";
            }
        }
        System.out.println(s1);
    }

    public static float silnia_rekurencyjna (float n) {
        if (n==0)
            return 1;
        else
            return (n*silnia_rekurencyjna(n-1));
    }

    public void ObliczWielomian() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Podaj punkt x ,w którym trzeba obliczyć wielomian: ");
        float xi = sc.nextFloat();
        sc.close();
        System.out.println("Rozwiązanie wielomianu w punkcie x = "+ xi +": ");
        String s1 = "W("+ xi + ") = ";
        float wynik = taby[0];
        float temp1, temp2;
        for(int x=0; x<k; x++){
            temp1 = (float) (tabi[0][x] / (silnia_rekurencyjna(x+1)*pow(h,x+1)));
            for(int y=0; y<=x;y++){
                temp2 = xi - tabx[y];
                temp1 *= temp2;
            }
            wynik+=temp1;
        }
        System.out.println(s1 + wynik);
    }

}
