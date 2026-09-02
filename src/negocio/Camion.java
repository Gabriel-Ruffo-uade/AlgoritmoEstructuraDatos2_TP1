package negocio;

import modelo.Paquete;

public class Camion
{
    private Paquete<?>[] paquetes;
    private int tope;

    public Camion(int capacidad)
    {
        paquetes = new Paquete<?>[capacidad];
        tope = -1;
    }

    public void cargar(Paquete<?> paquete)
    {
        if (estaLleno())
        {
            System.out.println("El camion esta lleno");
            return;
        }

        tope++;
        paquetes[tope] = paquete;
    }

    public Paquete<?> descargar()
    {
        if (estaVacio())
        {
            return null;
        }

        Paquete<?> paquete = paquetes[tope];
        paquetes[tope] = null;
        tope--;

        return paquete;
    }

    public Paquete<?> deshacerUltimaCarga()
    {
        return descargar();
    }

    public Paquete<?> verProximoPaquete()
    {
        if (estaVacio())
        {
            return null;
        }

        return paquetes[tope];
    }

    public boolean estaVacio()
    {
        return tope == -1;
    }

    public boolean estaLleno()
    {
        return tope == paquetes.length - 1;
    }

    public int cantidadPaquetes()
    {
        return tope + 1;
    }

    public Paquete<?> obtenerEnOrdenDeDescarga(int posicion)
    {
        if (posicion < 0 || posicion > tope)
        {
            return null;
        }

        return paquetes[tope - posicion];
    }
}
