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

# Iteración 2: Red de Depósitos (Árboles)

A. Red de Depósitos (ABB Manual)
    La empresa organiza sus depósitos regionales jerárquicamente por un ID de Depósito numérico.
        • Implementación obligatoria: se debe programar un Árbol Binario de Búsqueda (ABB) desde cero.
        • El nodo del árbol debe contener un atributo boolean visitado y un atributo LocalDateTime fechaUltimaAuditoria.
        • Requerimiento técnico: implementar un método de Auditoría que recorra el árbol (post-orden) y marque como "visitados" solo aquellos depósitos que no han sido auditados en los últimos 30 días.
        • Reporte por niveles: el sistema debe imprimir qué depósitos se encuentran en el "Nivel N" del árbol para coordinar inspecciones regionales. 


# Anexo: Estructura JSON sugerida
inventario.json (Iteración 1):
{ "paquetes": [ { "id": 1, "tipo": "Electronica", "peso": 12.5, "destino": "Cordoba",
"urgente": false } ] }

depositos.json (Iteraciones 2 y 3):
{ "depositos": [ { "id": 50, "nombre": "Hub Central Buenos Aires", "auditado": true,
"conexiones": [{"id": 20, "distancia": 700}, {"id": 80, "distancia": 300}] }, { "id": 20,
"nombre": "Deposito Cordoba", "auditado": false, "conexiones": [{"id": 50, "distancia": 700}]
} ] }