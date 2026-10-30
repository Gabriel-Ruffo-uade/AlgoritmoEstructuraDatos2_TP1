package modelo;

import java.util.List;

public class GrafoDepositos
{
    private List<Deposito> depositos;
    private double[][] matrizAdyacencia;

    public GrafoDepositos(List<Deposito> depositos)
    {
        this.depositos = depositos;
        matrizAdyacencia = new double[depositos.size()][depositos.size()];
        cargarConexiones();
    }

    private void cargarConexiones()
    {
        for (int i = 0; i < depositos.size(); i++)
        {
            Deposito deposito = depositos.get(i);

            for (int j = 0; j < deposito.getConexiones().cantidad(); j++)
            {
                Conexion conexion = deposito.getConexiones().obtener(j);
                int posicionDestino = buscarPosicion(conexion.getIdDepositoDestino());

                if (posicionDestino != -1)
                {
                    matrizAdyacencia[i][posicionDestino] = conexion.getDistancia();
                }
            }
        }
    }

    public ResultadoRuta buscarRutaBfs(int idOrigen, int idDestino)
    {
        int origen = buscarPosicion(idOrigen);
        int destino = buscarPosicion(idDestino);
        ListaGenerica<Deposito> recorrido = new ListaGenerica<>();

        if (origen == -1 || destino == -1)
        {
            return new ResultadoRuta(false, recorrido, 0, 0,
                    "El deposito de origen o destino no existe");
        }

        if (origen == destino)
        {
            recorrido.agregar(depositos.get(origen));
            return new ResultadoRuta(true, recorrido, 0, 0,
                    "El origen y el destino son el mismo deposito");
        }

        boolean[] visitados = new boolean[depositos.size()];
        int[] anteriores = new int[depositos.size()];
        int[] cola = new int[depositos.size()];

        for (int i = 0; i < anteriores.length; i++)
        {
            anteriores[i] = -1;
        }

        int frente = 0;
        int fin = 0;
        cola[fin++] = origen;
        visitados[origen] = true;

        while (frente < fin && !visitados[destino])
        {
            int actual = cola[frente++];

            for (int vecino = 0; vecino < depositos.size(); vecino++)
            {
                if (matrizAdyacencia[actual][vecino] > 0 && !visitados[vecino])
                {
                    visitados[vecino] = true;
                    anteriores[vecino] = actual;
                    cola[fin++] = vecino;
                }
            }
        }

        if (!visitados[destino])
        {
            return new ResultadoRuta(false, recorrido, 0, 0,
                    "No existe una ruta entre los depositos indicados");
        }

        int[] caminoInvertido = new int[depositos.size()];
        int cantidad = 0;

        for (int posicion = destino; posicion != -1; posicion = anteriores[posicion])
        {
            caminoInvertido[cantidad++] = posicion;
        }

        double distanciaTotal = 0;

        for (int i = cantidad - 1; i >= 0; i--)
        {
            int posicion = caminoInvertido[i];
            recorrido.agregar(depositos.get(posicion));

            if (i > 0)
            {
                distanciaTotal += matrizAdyacencia[posicion][caminoInvertido[i - 1]];
            }
        }

        return new ResultadoRuta(true, recorrido, cantidad - 1, distanciaTotal,
                "Ruta encontrada");
    }

    private int buscarPosicion(int idDeposito)
    {
        for (int i = 0; i < depositos.size(); i++)
        {
            if (depositos.get(i).getId() == idDeposito)
            {
                return i;
            }
        }

        return -1;
    }
}
