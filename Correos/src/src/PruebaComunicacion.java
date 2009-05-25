
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.logging.Level;
import java.util.logging.Logger;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
 /* Universidad Carlos III
 * Campus de Leganes
 * Ingenieria Informatica
 * ASIGNATURA: Sistemas Informaticos
*/
/**
 *
 * @author Miguel Degayón Cortés, 100033447
 */
public class PruebaComunicacion {

    private class MiHilo extends Thread{

        private MailBox mail;

        MiHilo(MailBox mb){
            mail = mb;
        }

        @Override
        public void run(){

            MessageInterface m = null;
            do{
                try {

                    System.out.print("Escuchando...");

                    do{
                    m = mail.receive();
                    }while(m == null);

                   System.out.println("Mensaje Recibido: " + m.toString());

                } catch (Exception ex) {
                    System.out.println("PETO");
                    Logger.getLogger(PruebaComunicacion.class.getName()).log(Level.SEVERE, null, ex);
                }
            }while(true);
        }
    }

    public void ejecutar(String cola1, String cola2, String esclavo, String ip)
            throws IOException{

        MailBox buzon = null;
        
        try {
            System.out.println(cola1+" "+cola2+" "+esclavo);

            // Solo esta sentencia puede lanzar excepcion
            buzon = new MailBox(ip, 33447,
                    cola1, cola2, "K-SCADA", Boolean.parseBoolean(esclavo));

            MiHilo h = new MiHilo(buzon);
            System.out.print("Arrancar el hilo...");
            h.start();
            System.out.println("OK");

        } catch (RemoteException ex) {
            System.out.println("PETO");
            Logger.getLogger(PruebaComunicacion.class.getName()).log(Level.SEVERE, null, ex);
        } catch (MalformedURLException ex) {
            System.out.println("PETO");
            Logger.getLogger(PruebaComunicacion.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NotBoundException ex) {
            System.out.println("PETO");
            Logger.getLogger(PruebaComunicacion.class.getName()).log(Level.SEVERE, null, ex);
        }

        //Arrancar el hilo que lee

        String linea;

        while(true){
            System.out.println();
            System.out.print("Enviar Mensaje: ");
            BufferedReader br = new BufferedReader
                    (new InputStreamReader(System.in));
             linea = br.readLine();
            System.out.println(buzon.send(new TextMessage(linea)));
        }


        
    }

    public static void main(String args[]) throws IOException{

        try {
            
            PruebaComunicacion p = new PruebaComunicacion();
            p.ejecutar(args[0], args[1], args[2], args[3]);

        } catch (RemoteException ex) {
            System.out.println("PETO");
            Logger.getLogger(PruebaComunicacion.class.getName()).log(Level.SEVERE, null, ex);
        } catch (MalformedURLException ex) {
            System.out.println("PETO");
            Logger.getLogger(PruebaComunicacion.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
}
