package modelo;

public class ListaGenerica<T>
{
    private Object[] elementos;
    private int cantidad;

    public ListaGenerica()
    {
        elementos = new Object[10];
        cantidad = 0;
    }

    public void agregar(T elemento)
    {
        if (cantidad == elementos.length)
        {
            ampliarCapacidad();
        }

        elementos[cantidad] = elemento;
        cantidad++;
    }

    @SuppressWarnings("unchecked")
    public T obtener(int posicion)
    {
        if (posicion < 0 || posicion >= cantidad)
        {
            throw new IndexOutOfBoundsException("Posicion incorrecta");
        }

        return (T) elementos[posicion];
    }

    @SuppressWarnings("unchecked")
    public T eliminarUltimo()
    {
        if (estaVacia())
        {
            return null;
        }

        cantidad--;
        T elemento = (T) elementos[cantidad];
        elementos[cantidad] = null;

        return elemento;
    }

    @SuppressWarnings("unchecked")
    public T eliminar(int posicion)
    {
        if (posicion < 0 || posicion >= cantidad)
        {
            throw new IndexOutOfBoundsException("Posicion incorrecta");
        }

        T elemento = (T) elementos[posicion];

        for (int i = posicion; i < cantidad - 1; i++)
        {
            elementos[i] = elementos[i + 1];
        }

        cantidad--;
        elementos[cantidad] = null;

        return elemento;
    }

    public int cantidad()
    {
        return cantidad;
    }

    public boolean estaVacia()
    {
        return cantidad == 0;
    }

    private void ampliarCapacidad()
    {
        Object[] nuevoArreglo = new Object[elementos.length * 2];

        for (int i = 0; i < elementos.length; i++)
        {
            nuevoArreglo[i] = elementos[i];
        }

        elementos = nuevoArreglo;
    }
}
