package modelo;

public class Paquete<T>
{
    private long id;
    private T contenido;
    private double peso;
    private String destino;
    private boolean urgente;

    public Paquete(long id, T contenido, double peso, String destino)
    {
        this(id, contenido, peso, destino, false);
    }

    public Paquete(long id, T contenido, double peso, String destino, boolean urgente)
    {
        this.id = id;
        this.contenido = contenido;
        this.peso = peso;
        this.destino = destino;
        this.urgente = urgente;
    }

    public long getId()
    {
        return id;
    }

    public T getContenido()
    {
        return contenido;
    }

    public double getPeso()
    {
        return peso;
    }

    public String getDestino()
    {
        return destino;
    }

    public boolean isUrgente()
    {
        return urgente;
    }

    @Override
    public String toString()
    {
        return "Paquete [id=" + id
                + ", contenido=" + contenido
                + ", peso=" + peso
                + ", destino=" + destino
                + ", urgente=" + urgente + "]";
    }
}
