package aplicacion;

import java.io.IOException;
import java.util.Scanner;
import modelo.ArbolBinarioBusqueda;
import modelo.ListaGenerica;
import modelo.Paquete;
import negocio.Camion;
import negocio.CentroDistribucion;
import persistencia.PersistenciaDepositosJson;
import persistencia.PersistenciaJson;

public class Menu
{
    private Scanner teclado;
    private ListaGenerica<Paquete<String>> paquetes;
    private Camion camion;
    private PersistenciaJson persistencia;
    private PersistenciaDepositosJson persistenciaDepositos;
    private ArbolBinarioBusqueda arbolDepositos;
    private CentroDistribucion centroDistribucion;

    public Menu()
    {
        teclado = new Scanner(System.in);
        persistencia = new PersistenciaJson("inventario.json");
        persistenciaDepositos = new PersistenciaDepositosJson("depositos.json");
        paquetes = new ListaGenerica<>();
        arbolDepositos = new ArbolBinarioBusqueda();
        centroDistribucion = new CentroDistribucion();
    }

    public void iniciar()
    {
        cargarDatos();
        int opcion;

        do
        {
            mostrarOpciones();
            opcion = leerEntero("Opcion: ");

            switch (opcion)
            {
                case 1 -> agregarPaquete();
                case 2 -> deshacerCarga();
                case 3 -> verProximoPaquete();
                case 4 -> listarPaquetesCargados();
                case 5 -> listarPaquetesDelCamion();
                case 6 -> auditarDepositos();
                case 0 -> System.out.println("Programa finalizado");
                default -> System.out.println("Opcion incorrecta");
            }
        }
        while (opcion != 0);
    }

    private void cargarDatos()
    {
        try
        {
            paquetes = persistencia.cargar();
            prepararCamion();

            System.out.println("Paquetes cargados desde el JSON: " + paquetes.cantidad());
        }
        catch (IOException e)
        {
            camion = new Camion(100);
            System.out.println("No se pudo leer inventario.json");
        }

        try
        {
            arbolDepositos = persistenciaDepositos.cargar();
            System.out.println("Depositos cargados desde el JSON: "
                    + arbolDepositos.obtenerEnOrden().size());
        }
        catch (IOException e)
        {
            System.out.println("No se pudo leer depositos.json");
        }
    }

    private void mostrarOpciones()
    {
        System.out.println("\n--- GESTION DE CAMION ---");
        System.out.println("1. Agregar paquete");
        System.out.println("2. Deshacer ultima carga");
        System.out.println("3. Ver proximo paquete a descargar");
        System.out.println("4. Listar paquetes cargados");
        System.out.println("5. Listar paquetes dentro del camion");
        System.out.println("6. Auditar depositos y mostrar niveles");
        System.out.println("0. Salir");
    }

    private void agregarPaquete()
    {
        long id = generarNuevoId();
        System.out.println("Nuevo ID: " + id);

        System.out.print("Tipo de contenido: ");
        String tipo = teclado.nextLine();
        double peso = leerDouble("Peso: ");
        System.out.print("Destino: ");
        String destino = teclado.nextLine();
        System.out.print("Es urgente (s/n): ");
        boolean urgente = teclado.nextLine().equalsIgnoreCase("s");

        Paquete<String> paquete = new Paquete<>(id, tipo, peso, destino, urgente);
        paquetes.agregar(paquete);
        prepararCamion();
        guardarDatos();
        System.out.println("Paquete agregado al camion");
    }

    private void deshacerCarga()
    {
        Paquete<?> paquete = camion.deshacerUltimaCarga();

        if (paquete == null)
        {
            System.out.println("El camion esta vacio");
            return;
        }

        eliminarPaqueteDeLaLista(paquete.getId());
        guardarDatos();
        System.out.println("Se quito la ultima carga: " + paquete);
    }

    private void verProximoPaquete()
    {
        Paquete<?> paquete = camion.verProximoPaquete();
        System.out.println(paquete == null ? "El camion esta vacio" : paquete);
    }

    private void listarPaquetesCargados()
    {
        if (paquetes.estaVacia())
        {
            System.out.println("No hay paquetes cargados");
            return;
        }

        for (int i = 0; i < paquetes.cantidad(); i++)
        {
            System.out.println(paquetes.obtener(i));
        }
    }

    private void listarPaquetesDelCamion()
    {
        if (camion.estaVacio())
        {
            System.out.println("El camion esta vacio");
            return;
        }

        System.out.println("Orden de descarga del camion:");

        for (int i = 0; i < camion.cantidadPaquetes(); i++)
        {
            Paquete<?> paquete = camion.obtenerEnOrdenDeDescarga(i);
            String prioridad = centroDistribucion.obtenerTipoPrioridad(paquete);
            System.out.println(paquete + " - " + prioridad);
        }
    }

    private void prepararCamion()
    {
        ListaGenerica<Paquete<String>> ordenados =
                centroDistribucion.ordenarParaProcesar(paquetes);
        camion = new Camion(ordenados.cantidad() + 100);

        for (int i = ordenados.cantidad() - 1; i >= 0; i--)
        {
            camion.cargar(ordenados.obtener(i));
        }
    }

    private void auditarDepositos()
    {
        arbolDepositos.auditar();
        arbolDepositos.imprimirPorNiveles();

        try
        {
            persistenciaDepositos.guardar(arbolDepositos);
            System.out.println("Auditoria de depositos guardada");
        }
        catch (IOException e)
        {
            System.out.println("No se pudo actualizar depositos.json");
        }
    }

    private void eliminarPaqueteDeLaLista(long id)
    {
        for (int i = 0; i < paquetes.cantidad(); i++)
        {
            if (paquetes.obtener(i).getId() == id)
            {
                paquetes.eliminar(i);
                return;
            }
        }
    }

    private long generarNuevoId()
    {
        long idMayor = 0;

        for (int i = 0; i < paquetes.cantidad(); i++)
        {
            Paquete<String> paquete = paquetes.obtener(i);

            if (paquete.getId() > idMayor)
            {
                idMayor = paquete.getId();
            }
        }

        return idMayor + 1;
    }

    private void guardarDatos()
    {
        try
        {
            persistencia.guardar(paquetes);
        }
        catch (IOException e)
        {
            System.out.println("No se pudo actualizar inventario.json");
        }
    }

    private int leerEntero(String mensaje)
    {
        return (int) leerLong(mensaje);
    }

    private long leerLong(String mensaje)
    {
        while (true)
        {
            System.out.print(mensaje);
            try
            {
                return Long.parseLong(teclado.nextLine());
            }
            catch (NumberFormatException e)
            {
                System.out.println("Ingrese un numero entero");
            }
        }
    }

    private double leerDouble(String mensaje)
    {
        while (true)
        {
            System.out.print(mensaje);
            try
            {
                return Double.parseDouble(teclado.nextLine().replace(',', '.'));
            }
            catch (NumberFormatException e)
            {
                System.out.println("Ingrese un numero valido");
            }
        }
    }
}
