import java.net.*;
import java.io.*;
import java.util.Scanner;

public class ClienteTCP {

    private String host;
    private int puerto;
    private Socket cliente;
    private DataInputStream in;
    private DataOutputStream out;
    private Scanner teclado;
    private String mensaje;

    public ClienteTCP(String host, int puerto) {
        this.host = host;
        this.puerto = puerto;
    }

    public void iniciar() {
        // Se inicia el Scanner de entrada
        teclado = new Scanner(System.in);

        try {
            // Inicia el socket del cliente
            cliente = new Socket(host, puerto);

            // Inicia los flujos de entrada y salida entre servidor y cliente
            in = new DataInputStream(cliente.getInputStream());
            out = new DataOutputStream(cliente.getOutputStream());

            System.out.println("Bienvenido al servidor");

            do {
                // Pide entrada al usuario
                System.out.println("Ingrese un mensaje o \"salir\" para salir");
                System.out.print("-> ");
                mensaje = teclado.nextLine();

                // Envía el mensaje al servidor
                out.writeUTF(mensaje);

                if (mensaje.equals("salir")) {
                    System.out.println("Cerrando el programa...");
                }
                else {
                    String respuesta = in.readUTF();
                    System.out.println("El servidor responde:\n-> " + respuesta + "\n");
                }
            } while(!mensaje.equals("salir"));

        } catch (UnknownHostException e) {
            System.out.println("Servidor no encontrado: " + e.getMessage() + "\n");
        } catch (IOException e) {
            System.out.println("Error de entrada/salida: " + e.getMessage() + "\n");
        }
    }
}
