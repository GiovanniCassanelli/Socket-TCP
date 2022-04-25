public class MainServidor {

    public static void main(String[] args) {

        int puerto = -1234;

        try {
            puerto = Integer.parseInt(args[0]);
        } catch (Exception e) {
            System.out.println("USO: java -jar MainServidor.jar <puerto>");
            System.exit(0);
        }

        if (puerto < 1) {
            System.out.println("USO: java -jar MainServidor.jar <puerto>");
            System.exit(0);
        }

        ServidorTCP servidor = new ServidorTCP(puerto);
        servidor.iniciar();
    }
}
