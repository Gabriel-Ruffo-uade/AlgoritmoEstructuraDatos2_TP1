package modelo;

public class Conexion
{
    private int idDepositoDestino;
    private double distancia;

    public Conexion(int idDepositoDestino, double distancia)
    {
        this.idDepositoDestino = idDepositoDestino;
        this.distancia = distancia;
    }

    public int getIdDepositoDestino()
    {
        return idDepositoDestino;
    }

    public double getDistancia()
    {
        return distancia;
    }
}
