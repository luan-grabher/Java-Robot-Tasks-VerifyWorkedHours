package Verificar_Horas_Zac;

import Model.VerifyWorkedHours;
import Robo.AppRobo;

public class Principal {

    public static void main(String[] args) {
        
        AppRobo robo = new AppRobo("Verificar Horas ZAC");
        
        robo.definirParametros();
        robo.executar(VerifyWorkedHours.run()
        );
        
        System.exit(0);
    }
    
}
