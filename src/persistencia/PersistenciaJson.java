package persistencia;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import modelo.ListaGenerica;
import modelo.Paquete;

public class PersistenciaJson
{
    private Path archivo;

    public PersistenciaJson(String nombreArchivo)
    {
        archivo = Path.of(nombreArchivo);
    }

    public ListaGenerica<Paquete<String>> cargar() throws IOException
    {
        ListaGenerica<Paquete<String>> paquetes = new ListaGenerica<>();

        if (!Files.exists(archivo))
        {
            return paquetes;
        }

        String json = Files.readString(archivo);
        Pattern patron = Pattern.compile(
                "\\{\\s*\"id\"\\s*:\\s*(\\d+)\\s*,\\s*"
                + "\"tipo\"\\s*:\\s*\"([^\"]+)\"\\s*,\\s*"
                + "\"peso\"\\s*:\\s*([\\d.]+)\\s*,\\s*"
                + "\"destino\"\\s*:\\s*\"([^\"]+)\"\\s*,\\s*"
                + "\"urgente\"\\s*:\\s*(true|false)\\s*\\}"
        );

        Matcher coincidencia = patron.matcher(json);
        while (coincidencia.find())
        {
            long id = Long.parseLong(coincidencia.group(1));
            String tipo = coincidencia.group(2);
            double peso = Double.parseDouble(coincidencia.group(3));
            String destino = coincidencia.group(4);
            boolean urgente = Boolean.parseBoolean(coincidencia.group(5));

            paquetes.agregar(new Paquete<>(id, tipo, peso, destino, urgente));
        }

        return paquetes;
    }

    public void guardar(ListaGenerica<Paquete<String>> paquetes) throws IOException
    {
        StringBuilder json = new StringBuilder();
        json.append("{\n  \"paquetes\": [\n");

        for (int i = 0; i < paquetes.cantidad(); i++)
        {
            Paquete<String> paquete = paquetes.obtener(i);
            json.append("    { \"id\": ").append(paquete.getId())
                    .append(", \"tipo\": \"").append(paquete.getContenido()).append("\"")
                    .append(", \"peso\": ").append(paquete.getPeso())
                    .append(", \"destino\": \"").append(paquete.getDestino()).append("\"")
                    .append(", \"urgente\": ").append(paquete.isUrgente())
                    .append(" }");

            if (i < paquetes.cantidad() - 1)
            {
                json.append(",");
            }

            json.append("\n");
        }

        json.append("  ]\n}");
        Files.writeString(archivo, json.toString());
    }
}
