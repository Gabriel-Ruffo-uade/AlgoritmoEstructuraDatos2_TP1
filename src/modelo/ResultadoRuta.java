package modelo;

public class ResultadoRuta
{
    private boolean encontrada;
    private ListaGenerica<Deposito> recorrido;
    private int cantidadSaltos;
    private double distanciaTotal;
    private String mensaje;

    public ResultadoRuta(boolean encontrada, ListaGenerica<Deposito> recorrido,
                         int cantidadSaltos, double distanciaTotal, String mensaje)
    {
        this.encontrada = encontrada;
        this.recorrido = recorrido;
        this.cantidadSaltos = cantidadSaltos;
        this.distanciaTotal = distanciaTotal;
        this.mensaje = mensaje;
    }

    public boolean isEncontrada()
    {
        return encontrada;
    }

    public ListaGenerica<Deposito> getRecorrido()
    {
        return recorrido;
    }

    public int getCantidadSaltos()
    {
        return cantidadSaltos;
    }

    public double getDistanciaTotal()
    {
        return distanciaTotal;
    }

    public String getMensaje()
    {
        return mensaje;
    }
}
