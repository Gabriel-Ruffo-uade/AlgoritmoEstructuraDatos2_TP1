package negocio;

import modelo.ListaGenerica;
import modelo.Paquete;

public class CentroDistribucion
{
    public ListaGenerica<Paquete<String>> ordenarParaProcesar(
            ListaGenerica<Paquete<String>> paquetes)
    {
        ListaGenerica<Paquete<String>> paquetesOrdenados = new ListaGenerica<>();

        for (int nivel = 3; nivel >= 0; nivel--)
        {
            for (int i = 0; i < paquetes.cantidad(); i++)
            {
                Paquete<String> paquete = paquetes.obtener(i);

                if (obtenerNivelPrioridad(paquete) == nivel)
                {
                    paquetesOrdenados.agregar(paquete);
                }
            }
        }

        return paquetesOrdenados;
    }

    public int obtenerNivelPrioridad(Paquete<?> paquete)
    {
        boolean urgente = paquete.isUrgente();
        boolean pesado = paquete.getPeso() > 50;

        if (urgente && pesado)
        {
            return 3;
        }

        if (urgente)
        {
            return 2;
        }

        if (pesado)
        {
            return 1;
        }

        return 0;
    }

    public String obtenerTipoPrioridad(Paquete<?> paquete)
    {
        return switch (obtenerNivelPrioridad(paquete))
        {
            case 3 -> "URGENTE Y PESADO";
            case 2 -> "URGENTE";
            case 1 -> "PESADO";
            default -> "ESTANDAR";
        };
    }
}
