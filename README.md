# Reporte de Arquitectura - DuocShop

## Resumen Ejecutivo

DuocShop es una aplicación de comercio electrónico desarrollada con Spring Boot que sigue una arquitectura de capas bien definida. El sistema implementa el patrón MVC (Modelo-Vista-Controlador) y utiliza tecnologías modernas para el desarrollo de APIs REST.

---

## 📚 Arquitectura General

La aplicación está estructurada en las siguientes capas principales:

1. **Capa de Presentación (Controllers)**
2. **Capa de Lógica de Negocio (Services)**
3. **Capa de Acceso a Datos (Repositories)**
4. **Capa de Modelos (Entities/Models)**
5. **Componentes Auxiliares (DTOs, Assemblers)**

---

## 🏗️ Descripción Detallada de Componentes

### 1. **Models (Entidades de Dominio)**
📍 **Ubicación:** `src/main/java/com/duocshop/duocshop/model/`

**Propósito:** Representan las entidades de negocio del sistema y mapean directamente con las tablas de la base de datos.

#### Entidades Principales:

- **`Usuario`**: Representa a los usuarios del sistema
  - Atributos: id, nombre, apellido, correo, contrasena, sede
  - Relaciones: ManyToOne con Sede, OneToMany con UsuarioProducto y UsuarioCalificacion

- **`Producto`**: Representa los productos disponibles en la tienda
  - Atributos: id, nombre, descripcion, precio, marca
  - Relaciones: ManyToOne con Marca, OneToMany con CategoriaProducto y UsuarioProducto

- **`Categoria`**: Categorías para clasificar productos
  - Atributos: id, nombre
  - Relaciones: OneToMany con CategoriaProducto

- **`Marca`**: Marcas de los productos
  - Atributos: id, nombre
  - Relaciones: OneToMany con Producto

- **`Sede`**: Ubicaciones físicas o sucursales
  - Atributos: id, nombre, direccion
  - Relaciones: OneToMany con Usuario

- **`Calificacion`**: Sistema de puntuación y comentarios
  - Atributos: id, puntaje, comentario
  - Relaciones: OneToMany con UsuarioCalificacion

#### Entidades de Relación (Tablas Intermedias):

- **`UsuarioProducto`**: Relación muchos-a-muchos entre Usuario y Producto
- **`CategoriaProducto`**: Relación muchos-a-muchos entre Categoria y Producto
- **`UsuarioCalificacion`**: Relación muchos-a-muchos entre Usuario y Calificacion

**Tecnologías utilizadas:**
- JPA/Hibernate para mapeo objeto-relacional
- Lombok para reducir código boilerplate (@Data, @Entity, etc.)
- Jackson para serialización/deserialización JSON

---

### 2. **Repositories (Capa de Acceso a Datos)**
📍 **Ubicación:** `src/main/java/com/duocshop/duocshop/repository/`

**Propósito:** Abstraen el acceso a la base de datos y proporcionan métodos para realizar operaciones CRUD y consultas personalizadas.

#### Características:
- Extienden `JpaRepository<Entidad, TipoId>` para funcionalidad básica CRUD
- Incluyen consultas personalizadas usando `@Query` con JPQL
- Métodos de búsqueda específicos del dominio

#### Ejemplos de Funcionalidades:

**ProductoRepository:**
- Búsqueda por marca, categoría, rango de precios
- Consultas complejas que combinan múltiples criterios (marca + categoría + sede)
- Búsquedas por calificación y ubicación

**UsuarioProductoRepository:**
- Consultas para obtener resúmenes de relaciones usuario-producto
- Métodos para eliminar por usuario o producto específico

**Ventajas:**
- Separación clara entre lógica de negocio y acceso a datos
- Reutilización de consultas
- Optimización automática de consultas por Spring Data JPA

---

### 3. **Services (Capa de Lógica de Negocio)**
📍 **Ubicación:** `src/main/java/com/duocshop/duocshop/service/`

**Propósito:** Contienen la lógica de negocio de la aplicación y orquestan las operaciones entre controladores y repositorios.

#### Responsabilidades:
- **Validación de datos de negocio**
- **Coordinación de operaciones complejas**
- **Transformación de datos**
- **Manejo de transacciones** (`@Transactional`)

#### Servicios Principales:

**UsuarioService:**
- Gestión completa de usuarios (CRUD)
- Validaciones de negocio específicas
- Manejo de relaciones con otras entidades

**ProductoService:**
- Gestión de productos y sus relaciones
- Búsquedas avanzadas y filtrado
- Lógica de negocio para consultas complejas

**CategoriaService, MarcaService, SedeService:**
- Operaciones básicas CRUD para entidades de referencia
- Validaciones específicas del dominio

**Servicios de Relación:**
- `UsuarioProductoService`: Manejo de carritos/favoritos
- `UsuarioCalificacionService`: Sistema de reseñas y puntuaciones
- `CategoriaProductoService`: Clasificación de productos

**Características técnicas:**
- Anotación `@Service` para registro automático en Spring
- `@Transactional` para manejo de transacciones
- Inyección de dependencias con `@Autowired`

---

### 4. **Controllers (Capa de Presentación)**
📍 **Ubicación:** `src/main/java/com/duocshop/duocshop/controller/`

**Propósito:** Exponen la funcionalidad del sistema a través de endpoints REST y manejan las solicitudes HTTP.

#### Estructura de Controllers:

**Versión 1 (v1) - API REST Tradicional:**
- **ProductoController**: CRUD completo + búsquedas avanzadas
- **UsuarioController**: Gestión de usuarios con DTOs
- **CategoriaController**: Manejo de categorías
- **SedeController**: Gestión de sedes
- **MarcaController**: Operaciones de marcas
- **UsuarioProductoController**: Relaciones usuario-producto
- **UsuarioCalificacionController**: Sistema de calificaciones

**Versión 2 (v2) - API con HATEOAS:**
- Implementa el estándar HATEOAS (Hypermedia as the Engine of Application State)
- Respuestas enriquecidas con links relacionados
- Mejor navegabilidad de la API

#### Características de los Controllers:

**Documentación con OpenAPI/Swagger:**
```java
@Tag(name = "Producto", description = "Operaciones relacionadas con productos")
@Operation(summary = "Listar productos")
@ApiResponses(value = {
    @ApiResponse(responseCode = "200", description = "Lista de productos obtenida exitosamente"),
    @ApiResponse(responseCode = "204", description = "No hay productos disponibles")
})
```

**Operaciones HTTP Estándar:**
- `GET`: Consultar datos (listado, búsqueda por ID, filtros)
- `POST`: Crear nuevos recursos
- `PUT`: Actualización completa
- `PATCH`: Actualización parcial
- `DELETE`: Eliminación de recursos

**Manejo de Respuestas:**
- Uso de `ResponseEntity<T>` para control completo de respuestas HTTP
- Códigos de estado apropiados (200, 201, 204, 404, etc.)
- Manejo de errores y excepciones

---

### 5. **DTOs (Data Transfer Objects)**
📍 **Ubicación:** `src/main/java/com/duocshop/duocshop/dto/`

**Propósito:** Objetos especializados para transferencia de datos entre capas, especialmente para exponer información sin revelar detalles internos de las entidades.

#### Ejemplo - UsuarioDTO:
```java
public class UsuarioDTO {
    private Integer id;
    private String nombre;
    private String apellido;
    private String correo;
    private Integer sedeId;
    private String sedeNombre;
    // Excluye campos sensibles como contraseña
}
```

**Ventajas:**
- **Seguridad**: Filtran información sensible
- **Performance**: Reducen payload de red
- **Flexibilidad**: Permiten diferentes vistas de los mismos datos
- **Evolución**: Facilitan cambios en la API sin afectar entidades

---

### 6. **Assemblers (HATEOAS)**
📍 **Ubicación:** `src/main/java/com/duocshop/duocshop/assemblers/`

**Propósito:** Transforman entidades en representaciones HATEOAS con links navegables.

#### Funcionalidad:
- Implementan `RepresentationModelAssembler<T, EntityModel<T>>`
- Agregan links relacionados automáticamente
- Facilitan la navegación entre recursos de la API

#### Ejemplo de Links Generados:
```java
EntityModel.of(producto,
    linkTo(methodOn(ProductoControllerV2.class).getProductoById(producto.getId())).withSelfRel(),
    linkTo(methodOn(ProductoControllerV2.class).getAllProductos()).withRel("productos"),
    linkTo(methodOn(ProductoControllerV2.class).createProducto(producto)).withRel("crear")
);
```

---

## 🔄 Flujo de Datos

### Flujo Típico de una Solicitud:

1. **Cliente** envía solicitud HTTP al endpoint
2. **Controller** recibe la solicitud
3. **Controller** valida parámetros y llama al **Service**
4. **Service** ejecuta lógica de negocio
5. **Service** utiliza **Repository** para acceso a datos
6. **Repository** ejecuta consulta en base de datos
7. Los datos fluyen de vuelta: **Repository** → **Service** → **Controller**
8. **Controller** formatea respuesta y la envía al **Cliente**

### Ejemplo Práctico:
```
GET /api/v1/productos/categoria/1
    ↓
ProductoController.obtenerPorCategoriaId()
    ↓
ProductoService.obtenerProductosPorCategoriaId()
    ↓
ProductoRepository.findByCategoriaId()
    ↓
Base de Datos (consulta JPQL)
    ↓
Lista<Producto> → ProductoService → ProductoController → JSON Response
```

---

## 🛠️ Tecnologías y Patrones Utilizados

### Framework Principal:
- **Spring Boot**: Framework principal
- **Spring Data JPA**: Persistencia de datos
- **Spring Web**: Desarrollo de APIs REST
- **Spring HATEOAS**: Implementación de APIs hipermedia

### Patrones de Diseño:
- **MVC (Model-View-Controller)**: Separación de responsabilidades
- **Repository Pattern**: Abstracción de acceso a datos
- **Service Layer Pattern**: Encapsulación de lógica de negocio
- **DTO Pattern**: Transferencia controlada de datos
- **Dependency Injection**: Gestión de dependencias

### Herramientas de Desarrollo:
- **Lombok**: Reducción de código repetitivo
- **OpenAPI/Swagger**: Documentación automática de APIs
- **Jackson**: Serialización JSON
- **Maven**: Gestión de dependencias y construcción

---

## 📊 Funcionalidades del Sistema

### Gestión de Entidades:
- **CRUD completo** para todas las entidades principales
- **Búsquedas avanzadas** con múltiples criterios
- **Filtrado** por categoría, marca, precio, calificación, sede
- **Relaciones complejas** entre entidades

### Características de la API:
- **Versionado** (v1 y v2) para evolución sin romper compatibilidad
- **Documentación automática** con Swagger/OpenAPI
- **Respuestas consistentes** con códigos HTTP apropiados
- **HATEOAS** para navegabilidad mejorada

### Funcionalidades de Negocio:
- **Sistema de usuarios** con gestión de sedes
- **Catálogo de productos** con categorización y marcas
- **Sistema de calificaciones** y reseñas
- **Relaciones usuario-producto** (carrito, favoritos)
- **Búsquedas complejas** combinando múltiples criterios

---

## 🔒 Aspectos de Seguridad y Buenas Prácticas

### Seguridad:
- **DTOs** para filtrar información sensible
- **Validación** de datos en múltiples capas
- **Transacciones** para consistencia de datos

### Buenas Prácticas:
- **Separación clara de responsabilidades**
- **Código limpio** con anotaciones descriptivas
- **Documentación completa** de APIs
- **Manejo consistente de errores**
- **Versionado de APIs** para evolución controlada

---

## 📈 Beneficios de esta Arquitectura

1. **Mantenibilidad**: Código organizado y fácil de mantener
2. **Escalabilidad**: Capas independientes permiten crecimiento
3. **Testabilidad**: Fácil testing por separación de responsabilidades
4. **Reutilización**: Servicios y repositorios reutilizables
5. **Evolución**: APIs versionadas permiten cambios sin romper compatibilidad
6. **Documentación**: APIs auto-documentadas para facilitar integración

---

*Este reporte proporciona una visión completa de la arquitectura del sistema DuocShop, explicando el propósito y funcionamiento de cada componente dentro del ecosistema Spring Boot.*
