package Testes;



import Model.VerifyWorkedHours;

public class Testes {
    
    public static void main(String [] args){
        System.out.println(VerifyWorkedHours.run().replaceAll("<br>", "\n"));
    }
}
