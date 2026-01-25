package org.cris.AdaDos;
import org.cris.AdaDos.TareaDos.ControlAcceso;
import org.cris.AdaDos.tareaUno.tareaUno;
/*
Aqui se mostraran todos los avances de lo que hagamos
pues, debido a lo que se agregara despues, se utilizara un MVC
o bueno similar a este
*/

public class Main {
    public static void main(String[] args) throws InterruptedException {
        ControlAcceso login = new ControlAcceso();
        tareaUno tareaUNO = new tareaUno();

        if(login.login()){
            tareaUNO.generacionCalificaciones();
        }else{//Borrar esta mafufada al final xd
            System.out.println("ACCESO DENEGADO");
            for (int i = 5; i >= 0; i--) {
                System.out.println("Su dispositivo explotara en " + i);
                Thread.sleep(900);// espera 1 segundo
            }
        }
    }
}