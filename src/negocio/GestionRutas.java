package negocio;

import modelo.GrafoDepositos;
import modelo.ResultadoRuta;

public class GestionRutas
{
    private GestionDepositos gestionDepositos;

    public GestionRutas(GestionDepositos gestionDepositos)
    {
        this.gestionDepositos = gestionDepositos;
    }

    public ResultadoRuta buscarRutaConMenosSaltos(int idOrigen, int idDestino)
    {
        GrafoDepositos grafo = new GrafoDepositos(gestionDepositos.obtenerDepositos());
        return grafo.buscarRutaBfs(idOrigen, idDestino);
    }
}
