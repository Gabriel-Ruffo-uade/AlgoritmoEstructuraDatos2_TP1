package negocio;

import java.io.IOException;
import java.util.List;
import modelo.ArbolBinarioBusqueda;
import modelo.Deposito;
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

    public List<Deposito> obtenerDepositos()
    {
        return arbol.obtenerEnOrden();
    }
}
