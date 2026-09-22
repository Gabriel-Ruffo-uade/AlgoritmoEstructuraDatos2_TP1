package modelo;

import java.time.LocalDateTime;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

// ABB balanceado: los IDs menores quedan a la izquierda y los mayores a la derecha.
// Las rotaciones AVL se aplican despues de cada insercion para mantener el balance.
public class ArbolBinarioBusqueda
{
    private Deposito raiz;

    public Deposito getRaiz()
    {
        return raiz;
    }

    public void insertar(Deposito deposito)
    {
        raiz = insertar(raiz, deposito);
    }

    private Deposito insertar(Deposito actual, Deposito nuevo)
    {
        if (actual == null)
            return nuevo;

        if (nuevo.getId() < actual.getId())
            actual.setIzquierdo(insertar(actual.getIzquierdo(), nuevo));
        else if (nuevo.getId() > actual.getId())
            actual.setDerecho(insertar(actual.getDerecho(), nuevo));
        else
            return actual;

        actualizarAltura(actual);
        int balance = obtenerBalance(actual);

        if (balance > 1 && nuevo.getId() < actual.getIzquierdo().getId())
            return rotarDerecha(actual);

        if (balance < -1 && nuevo.getId() > actual.getDerecho().getId())
            return rotarIzquierda(actual);

        if (balance > 1 && nuevo.getId() > actual.getIzquierdo().getId())
        {
            actual.setIzquierdo(rotarIzquierda(actual.getIzquierdo()));
            return rotarDerecha(actual);
        }

        if (balance < -1 && nuevo.getId() < actual.getDerecho().getId())
        {
            actual.setDerecho(rotarDerecha(actual.getDerecho()));
            return rotarIzquierda(actual);
        }

        return actual;
    }

    private int obtenerAltura(Deposito deposito)
    {
        return deposito == null ? 0 : deposito.getAltura();
    }

    private void actualizarAltura(Deposito deposito)
    {
        int alturaIzquierda = obtenerAltura(deposito.getIzquierdo());
        int alturaDerecha = obtenerAltura(deposito.getDerecho());
        deposito.actualizarAltura(1 + Math.max(alturaIzquierda, alturaDerecha));
    }

    private int obtenerBalance(Deposito deposito)
    {
        return obtenerAltura(deposito.getIzquierdo())
                - obtenerAltura(deposito.getDerecho());
    }

    private Deposito rotarDerecha(Deposito deposito)
    {
        Deposito nuevaRaiz = deposito.getIzquierdo();
        Deposito subarbolMovido = nuevaRaiz.getDerecho();

        nuevaRaiz.setDerecho(deposito);
        deposito.setIzquierdo(subarbolMovido);

        actualizarAltura(deposito);
        actualizarAltura(nuevaRaiz);
        return nuevaRaiz;
    }

    private Deposito rotarIzquierda(Deposito deposito)
    {
        Deposito nuevaRaiz = deposito.getDerecho();
        Deposito subarbolMovido = nuevaRaiz.getIzquierdo();

        nuevaRaiz.setIzquierdo(deposito);
        deposito.setDerecho(subarbolMovido);

        actualizarAltura(deposito);
        actualizarAltura(nuevaRaiz);
        return nuevaRaiz;
    }

    public void auditar()
    {
        auditar(LocalDateTime.now());
    }

    public void auditar(LocalDateTime fechaAuditoria)
    {
        auditarPostOrden(raiz, fechaAuditoria, fechaAuditoria.minusDays(30));
    }

    private void auditarPostOrden(Deposito actual, LocalDateTime fechaAuditoria,
                                  LocalDateTime limite)
    {
        if (actual == null)
            return;

        auditarPostOrden(actual.getIzquierdo(), fechaAuditoria, limite);
        auditarPostOrden(actual.getDerecho(), fechaAuditoria, limite);

        LocalDateTime fecha = actual.getFechaUltimaAuditoria();
        if (fecha == null || fecha.isBefore(limite))
            actual.auditar(fechaAuditoria);
        else
            actual.marcarNoVisitado();
    }

    public List<Deposito> obtenerEnOrden()
    {
        List<Deposito> depositos = new ArrayList<>();
        obtenerEnOrden(raiz, depositos);
        return depositos;
    }

    private void obtenerEnOrden(Deposito actual, List<Deposito> depositos)
    {
        if (actual == null)
            return;

        obtenerEnOrden(actual.getIzquierdo(), depositos);
        depositos.add(actual);
        obtenerEnOrden(actual.getDerecho(), depositos);
    }

    public String reportePorNiveles()
    {
        StringBuilder reporte = new StringBuilder();
        Queue<Deposito> cola = new ArrayDeque<>();

        if (raiz != null)
            cola.add(raiz);

        int nivel = 0;
        while (!cola.isEmpty())
        {
            int cantidadEnNivel = cola.size();
            reporte.append("Nivel ").append(nivel).append(": ");

            for (int i = 0; i < cantidadEnNivel; i++)
            {
                Deposito deposito = cola.remove();
                reporte.append(deposito.getId());

                if (i < cantidadEnNivel - 1)
                    reporte.append(", ");

                if (deposito.getIzquierdo() != null)
                    cola.add(deposito.getIzquierdo());
                if (deposito.getDerecho() != null)
                    cola.add(deposito.getDerecho());
            }

            reporte.append(System.lineSeparator());
            nivel++;
        }

        return reporte.toString();
    }

    public void imprimirPorNiveles()
    {
        System.out.print(reportePorNiveles());
    }
}