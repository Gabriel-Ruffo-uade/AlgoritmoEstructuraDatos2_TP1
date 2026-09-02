# Sistema de logística — Iteración 1

A. TDA Paquete (Genérico)
Implementar un TDA genérico Paquete<T> que permita transportar distintos tipos de contenido (ej. Electronica, Alimentos, Fragiles).
    • Cada paquete tiene un ID único, un peso y un destino.
B. Gestión de Camiones. 
    Para el proceso de carga en los camiones:
        • El último paquete en entrar al camión es el primero en ser descargado en el punto de
    Distribución.
        • Operación: Se debe permitir "deshacer" la última carga de un paquete en caso de error de destino (O(1)).
C. Centro de Distribución
    Los pedidos llegan al centro y se preparan para su procesamiento.
        • Los paquetes marcados como "Urgente" o con peso > 50kg deben procesarse antes que los envíos estándar.
D. Persistencia Inicial
    Cargar la lista de paquetes y su configuración desde un archivo inventario.json. Realizar un menú para carga manual de datos. 

