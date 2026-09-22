package negocio;

import java.io.IOException;
import modelo.ArbolBinarioBusqueda;
import persistencia.PersistenciaDepositosJson;

public class GestionDepositos
{
    private final PersistenciaDepositosJson persistencia;
    private ArbolBinarioBusqueda arbol;

    public GestionDepositos(PersistenciaDepositosJson persistencia)
    {
        this.persistencia = persistencia;
        arbol = new ArbolBinarioBusqueda();
    }

    public void cargar() throws IOException
    {
        arbol = persistencia.cargar();
    }

    public int cantidad()
    {
        return arbol.obtenerEnOrden().size();
    }

    public String auditarYObtenerReporte()
    {
        arbol.auditar();
        return arbol.reportePorNiveles();
    }

    public void guardar() throws IOException
    {
        persistencia.guardar(arbol);
    }
}