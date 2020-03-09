/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package calcClientServ;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author dam2
 */
public class ServCalc extends Thread {

    private Socket clientSocket;

    public ServCalc(Socket socket) {
        clientSocket = socket;
    }

    public void run() {

        try {
            while(true){
            System.out.println("Arrancando hilo");

            InputStream is = clientSocket.getInputStream();
            OutputStream os = clientSocket.getOutputStream();

            //Enviamos mensaje
            String mensaje1 = "Introduzca primer digito";
            os.write(mensaje1.getBytes());
            os.flush();

            //Recibimos mensaje
            byte[] digito = new byte[25];
            is.read(digito);
            System.out.println("Mensaje recibido: " + new String(digito));

            //Enviamos mensaje
            String mensaje2 = "Introduzca el operando a calcular ( * | / | + | - )";
            os.write(mensaje2.getBytes());
            os.flush();

            //Recibimos mensaje
            byte[] operando = new byte[1];
            is.read(operando);
            System.out.println("Mensaje recibido: " + new String(operando));

            //Enviamos mensaje
            String mensaje3 = "Introduzca segundo digito";
            os.write(mensaje3.getBytes());
            os.flush();

            //Recibimos mensaje
            byte[] digito2 = new byte[25];
            is.read(digito2);
            System.out.println("Mensaje recibido: " + new String(digito2));

            //Enviamos mensaje
            float resultado = calculo(new Float(new String(digito)), new Float(new String(digito2)), new String(operando));
            String mensaje4 = String.valueOf(resultado);
            os.write(mensaje4.getBytes());
            os.flush();

            System.out.println("Terminado");
            }
        } catch (IOException ex) {
            Logger.getLogger(ServCalc.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void main(String[] args) {

        try {
            System.out.println("Creando socket servidor");

            ServerSocket serverSocket = new ServerSocket();

            System.out.println("Realizando el bind");

            InetSocketAddress addr = new InetSocketAddress("localhost", Integer.parseInt(JOptionPane.showInputDialog(null, "puerto")));
            serverSocket.bind(addr);

            System.out.println("Aceptando conexiones");

            while (serverSocket != null) {
                Socket newSocket = serverSocket.accept();
                System.out.println("Conexión recibida");

                ServCalc hilo = new ServCalc(newSocket);
                hilo.start();
            }

            System.out.println("Conexion recibida");
        } catch (IOException ex) {
            Logger.getLogger(ServCalc.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public static float calculo(float digito, float digito2, String operando) {
        float resultado = 0;

        switch (operando) {
            case "*":
                resultado = digito * digito2;
                break;

            case "/":
                resultado = digito / digito2;
                break;

            case "+":
                resultado = digito + digito2;
                break;

            case "-":
                resultado = digito - digito2;
                break;

        }
        return resultado;
    }
}
