package UnitTest;

import modelo.ListaGenerica;
import modelo.Paquete;
import persistencia.PersistenciaJson;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.io.IOException;
import org.junit.jupiter.api.Test;

public class SistemaTest 
{
    @Test
    public void unIdExistenteDebeRetornarProducto() throws IOException {
        PersistenciaJson persistencia = new PersistenciaJson("inventario.json");
        ListaGenerica<Paquete<String>> paquetes = persistencia.cargar();

        Paquete<String> paqueteObtenido = null;

        for (int i = 0; i < paquetes.cantidad(); i++) {
            if (paquetes.obtener(i).getId() == 2) {
                paqueteObtenido = paquetes.obtener(i);
                break;
            }
        }

        assertNotNull(paqueteObtenido);
        assertEquals(2, paqueteObtenido.getId());
    }
}
