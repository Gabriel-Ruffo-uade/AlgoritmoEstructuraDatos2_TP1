package modelo;

import java.time.LocalDateTime;

public class Deposito
{
    private final int id;
    private final String nombre;
    private Deposito izquierdo;
    private Deposito derecho;
    private int altura = 1;
    private boolean auditado;
    private boolean visitado;
    private LocalDateTime fechaUltimaAuditoria;

    public Deposito(int id, String nombre, boolean auditado,
                    LocalDateTime fechaUltimaAuditoria)
    {
        this.id = id;
        this.nombre = nombre;
        this.auditado = auditado;
        this.fechaUltimaAuditoria = fechaUltimaAuditoria;
    }

    public int getId()
    {
        return id;
    }

    public String getNombre()
    {
        return nombre;
    }

    public Deposito getIzquierdo()
    {
        return izquierdo;
    }

    public Deposito getDerecho()
    {
        return derecho;
    }

    public boolean isAuditado()
    {
        return auditado;
    }

    public boolean isVisitado()
    {
        return visitado;
    }

    public LocalDateTime getFechaUltimaAuditoria()
    {
        return fechaUltimaAuditoria;
    }

    int getAltura()
    {
        return altura;
    }

    void actualizarAltura(int altura)
    {
        this.altura = altura;
    }

    void setIzquierdo(Deposito izquierdo)
    {
        this.izquierdo = izquierdo;
    }

    void setDerecho(Deposito derecho)
    {
        this.derecho = derecho;
    }

    void auditar(LocalDateTime fechaAuditoria)
    {
        auditado = true;
        visitado = true;
        fechaUltimaAuditoria = fechaAuditoria;
    }

    void marcarNoVisitado()
    {
        visitado = false;
    }
}