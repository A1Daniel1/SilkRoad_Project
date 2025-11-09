# 🧭 SilkRoad Simulator

**Proyecto del curso:** Desarrollo Orientado por Objetos (POOB)  
**Ciclo:** 4 – 2025-2  
**Autores:** Daniel Ahumada & Juan Neira  

Inspirado en el problema *“The Silk Road … with Robots!”* de la maratón internacional de programación 2024.

---

## 📘 Descripción

**SilkRoad Simulator** es una aplicación educativa en Java que modela la legendaria **Ruta de la Seda**, donde robots y tiendas interactúan a lo largo de un camino.  
Cada robot puede desplazarse, recolectar dinero (*tenges*) y competir por maximizar ganancias según su tipo, mientras las tiendas ofrecen distintos comportamientos y reglas.

El proyecto evoluciona a lo largo de los ciclos del curso POOB, y esta versión corresponde al **Ciclo 4: Refactorización y Extensión**, donde se consolidan principios de **herencia, encapsulamiento y extensibilidad**.

---

## ⚙️ Funcionalidades Principales

### 🔹 Simulación General
- Creación de una **ruta de seda** de tamaño variable.  
- Representación visual usando el paquete `shapes`.  
- Control de visibilidad del simulador (`makeVisible`, `makeInvisible`).  

### 🏪 Tiendas
- Crear, eliminar y reabastecer tiendas.  
- Tipos disponibles:
  - **Normal:** almacena y entrega dinero normalmente.  
  - **Autonomous:** escoge su propia posición libre.  
  - **Fighter:** solo puede ser vaciada por robots más ricos.  
  - **RobinHood:** (tipo propuesto) reparte dinero entre tiendas pobres.

### 🤖 Robots
- Agregar, eliminar y mover robots individualmente o automáticamente.  
- Tipos disponibles:
  - **Normal:** comportamiento estándar.  
  - **NeverBack:** no puede retroceder.  
  - **Tender:** solo toma la mitad del dinero.  
  - **IllBeBack:** puede regresar a su posición inicial.  

### 🧩 Control de Simulación
- Movimiento automático con búsqueda de la mejor tienda.  
- Reinicio completo del sistema (`reboot`).  
- Barra de progreso y efectos visuales (parpadeo del robot con mayor ganancia).  

---

## 🧪 Pruebas Implementadas

| Clase de prueba | Tipo | Descripción |
|-----------------|------|-------------|
| `SilkRoadC4Test` | Unitarias (JUnit 5) | Verifica la creación y comportamiento de los nuevos tipos de robots y tiendas. |
| `SilkRoadCC4Test` | Colectiva | Casos de prueba compartidos entre equipos del curso. |
| `SilkRoadAtest` | Aceptación visual | Ejecuta la simulación visual y pide confirmación al usuario. |

---

## 🛠️ Tecnologías Utilizadas

- ☕ **Java 17+**
- 🧩 **BlueJ** – entorno educativo
- 🧪 **JUnit 5** – framework de pruebas
- 🧱 **Astah UML** – diagramas de clases y paquetes
- 🎨 **Shapes** – librería gráfica provista por el curso

---

## 📄 Versión Actual

**Ciclo 4 – Refactorización y Extensión (2025-2)**  
Incluye:
- Refactorización completa del paquete `shapes` usando herencia.  
- Separación del sistema en dos paquetes (`shapes` y `silkroad`).  
- Nuevos tipos de robots y tiendas.  
- Pruebas unitarias y de aceptación integradas.  

---

## 👥 Autores

- **Daniel Ahumada**  
- **Juan Neira**

---

> Proyecto desarrollado como parte del curso *Desarrollo Orientado por Objetos (POOB)* en la Escuela Colombiana de Ingeniería Julio Garavito.
