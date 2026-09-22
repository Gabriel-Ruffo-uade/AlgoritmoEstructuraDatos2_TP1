package persistencia;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import modelo.ArbolBinarioBusqueda;
import modelo.Deposito;

public class PersistenciaDepositosJson
{
    private final Path archivo;

    public PersistenciaDepositosJson(String nombreArchivo)
    {
        archivo = Path.of(nombreArchivo);
    }

    public ArbolBinarioBusqueda cargar() throws IOException
    {
        ArbolBinarioBusqueda arbol = new ArbolBinarioBusqueda();

        if (!Files.exists(archivo))
            return arbol;

        String json = Files.readString(archivo);
        Pattern patron = Pattern.compile(
                "\\{\\s*\"id\"\\s*:\\s*(\\d+)\\s*,\\s*"
                + "\"nombre\"\\s*:\\s*\"([^\"]+)\"\\s*,\\s*"
                + "\"auditado\"\\s*:\\s*(true|false)\\s*,\\s*"
                + "\"fechaUltimaAuditoria\"\\s*:\\s*(null|\"[^\"]+\")\\s*\\}"
        );

        Matcher coincidencia = patron.matcher(json);
        while (coincidencia.find())
        {
            int id = Integer.parseInt(coincidencia.group(1));
            String nombre = coincidencia.group(2);
            boolean auditado = Boolean.parseBoolean(coincidencia.group(3));
            String fechaTexto = coincidencia.group(4);
            LocalDateTime fecha = fechaTexto.equals("null")
                    ? null
                    : LocalDateTime.parse(fechaTexto.substring(1, fechaTexto.length() - 1));

            arbol.insertar(new Deposito(id, nombre, auditado, fecha));
        }

        return arbol;
    }

    public void guardar(ArbolBinarioBusqueda arbol) throws IOException
    {
        List<Deposito> depositos = arbol.obtenerEnOrden();
        StringBuilder json = new StringBuilder();
        json.append("{\n  \"depositos\": [\n");

        for (int i = 0; i < depositos.size(); i++)
        {
            Deposito deposito = depositos.get(i);
            json.append("    { \"id\": ").append(deposito.getId())
                    .append(", \"nombre\": \"").append(deposito.getNombre()).append("\"")
                    .append(", \"auditado\": ").append(deposito.isAuditado())
                    .append(", \"fechaUltimaAuditoria\": ");

            if (deposito.getFechaUltimaAuditoria() == null)
                json.append("null");
            else
                json.append("\"").append(deposito.getFechaUltimaAuditoria()).append("\"");

            json.append(" }");

            if (i < depositos.size() - 1)
                json.append(",");

            json.append("\n");
        }

        json.append("  ]\n}");
        Files.writeString(archivo, json.toString());
    }
}