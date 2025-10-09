# SilkRoad Simulator

Proyecto inicial del curso **Desarrollo Orientado por Objetos (POOB)** – Ciclo 1, 2025-2.  
Inspirado en el problema *The Silk Road … with Robots!* de la maratón internacional de programación 2024.

## Descripción
Este proyecto implementa un **simulador de la Ruta de la Seda**, en el cual se pueden gestionar:
- Tiendas (agregar, eliminar, reabastecer).
- Robots (agregar, eliminar, mover, devolver a su posición inicial).
- La ruta de la seda con una longitud definida.

El simulador puede funcionar en modo **visible** (usando `shapes`) o en modo **invisible**.

## Funcionalidades
1. Crear una ruta de seda.
2. Adicionar / eliminar tiendas.
3. Adicionar / eliminar robots.
4. Mover un robot.
5. Reiniciar la ruta de seda.
6. Hacer visible o invisible el simulador.
7. Finalizar el simulador.
8. Simular problema de la maraton
9. Solucionar problema de la maraton

## Tecnologías
- **Java (BlueJ)**  
- **Astah** (diagramas UML)  
- **Shapes** (paquete gráfico provisto por el curso)
# Retrospectiva — Proyecto SilkRoad

## Diagramas de clase
![Captura de pantalla](imagenes/Captura%20de%20pantalla_2025-09-11_18-00-24.png)

## Diagramas de secuencia
![Captura de pantalla 2](imagenes/Captura%20de%20pantalla_2025-09-11_18-00-47.png)
![Captura de pantalla 3](imagenes/Captura%20de%20pantalla_2025-09-11_18-01-05.png)
![Captura de pantalla 4](imagenes/Captura%20de%20pantalla_2025-09-11_18-02-07.png)

### 1. ¿Cuáles fueron los mini-ciclos definidos? Justifíquenlos.  
Definimos tres mini-ciclos principales:  
- **Diseño básico de la idea y requerimientos.**  
- **Implementación del código en Java.**  
- **Pruebas y ajustes finales.**  

Los mini-ciclos se definieron así porque nos permitió avanzar poco a poco, sin tener una guía clara, y asegurar que el proyecto fuera funcional antes de la entrega.

---

### 2. ¿Cuál es el estado actual del proyecto en términos de mini-ciclos? ¿Por qué?  
El proyecto se encuentra en el **tercer mini-ciclo**, porque ya tenemos el código funcionando y probado, y lo que falta es documentación y algunos detalles menores.

---

### 3. ¿Cuál fue el tiempo total invertido por cada uno de ustedes? (Horas/Hombre)  
- **Juan Manuel:** 8 horas.  
- **Daniel:** 6 horas.  

---

### 4. ¿Cuál consideran fue el mayor logro? ¿Por qué?  
El mayor logro fue **lograr que el código en Java funcionara correctamente**. Fue la parte más difícil, ya que no contábamos con guías claras y tuvimos que deducir muchas cosas por nuestra cuenta.

---

### 5. ¿Cuál consideran que fue el mayor problema técnico? ¿Qué hicieron para resolverlo?  

El mayor problema técnico fue entender cómo programar las clases (`SilkRoad`, `Store`, `Robot`) y lograr que interactuaran entre sí de manera correcta.  
La solución fue trabajar en conjunto, dividir responsabilidades y hacer varias pruebas hasta que el sistema funcionó como se esperaba.
Ademas de que tuvimos que trabajar mas por nuestra cuenta con la informacion que teniamos a mano

---

### 6. ¿Qué hicieron bien como equipo? ¿Qué se comprometen a hacer para mejorar los resultados?  
- **Lo que hicimos bien:** apoyarnos entre los dos, revisar el trabajo del compañero y avanzar por nuestra cuenta cuando era necesario.  
- **Para mejorar:** organizarnos mejor con el tiempo y buscar más apoyo externo (profesor, foros, ejemplos) en lugar de resolver todo improvisando.

---

### 7. Considerando las prácticas XP incluidas en los laboratorios, ¿cuál fue la más útil? ¿Por qué?  
La práctica más útil fue la **programación en parejas**, porque permitió revisar errores y mejorar el código al tener dos puntos de vista diferentes.

---

### 8. ¿Qué referencias usaron? ¿Cuál fue la más útil? Incluyan citas con estándares adecuados.  
Usamos principalmente recursos en línea:  
- Oracle. (2025). *Java SE Documentation*. Recuperado de: [https://docs.oracle.com/javase/](https://docs.oracle.com/javase/)  
- Stack Overflow. (2025). Consultas específicas sobre Java. Recuperado de: [https://stackoverflow.com/](https://stackoverflow.com/)  

La más útil fue la documentación oficial de **Java**, porque nos ayudó a confirmar cómo usar colecciones, comparadores y estructuras básicas.

