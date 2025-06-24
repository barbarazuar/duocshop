# Visión General de DuocShop  
  
DuocShop es una plataforma de comercio electrónico desarrollada con Spring Boot. El sistema está estructurado siguiendo una arquitectura de tres capas:  
  
1. **Capa de Controladores**: Maneja las solicitudes HTTP y devuelve respuestas apropiadas  
2. **Capa de Servicios**: Contiene la lógica de negocio  
3. **Capa de Repositorios**: Gestiona el acceso a datos  
  
## Principales Componentes del Sistema  
  
### Gestión de Usuarios  
  
El sistema permite la gestión de usuarios a través de la entidad `Usuario`. Los usuarios tienen información personal y están asociados a una sede específica. [1](#1-0)   
  
La API de usuarios proporciona los siguientes endpoints:  
- `GET /api/v1/usuario`: Listar todos los usuarios  
- `POST /api/v1/usuario`: Crear un nuevo usuario  
- `PUT /api/v1/usuario/{id}`: Actualizar todos los atributos de un usuario  
- `PATCH /api/v1/usuario/{id}`: Actualizar atributos específicos de un usuario  
- `DELETE /api/v1/usuario/{id}`: Eliminar un usuario [2](#1-1)   
  
### Gestión de Categorías  
  
El sistema permite organizar productos en categorías lógicas. La gestión de categorías se realiza a través de dos entidades principales:  
- `Categoria`: Representa una categoría de producto con un identificador único y nombre [3](#1-2)   
- `CategoriaProducto`: Representa la relación muchos a muchos entre categorías y productos  
  
La API de categorías proporciona endpoints similares a los de usuarios:  
- `GET /api/v1/categoria`: Listar todas las categorías  
- `POST /api/v1/categoria`: Crear una nueva categoría  
- `PUT /api/v1/categoria/{id}`: Actualizar una categoría existente  
- `PATCH /api/v1/categoria/{id}`: Actualizar parcialmente una categoría  
- `DELETE /api/v1/categoria/{id}`: Eliminar una categoría [4](#1-3)   
  
### Gestión de Sedes  
  
El sistema también maneja sedes o ubicaciones físicas, representadas por la entidad `Sede`. Los usuarios están asociados a una sede específica. [5](#1-4)   
  
## Arquitectura Técnica  
  
DuocShop está construido con las siguientes tecnologías:  
- **Spring Boot**: Framework principal para el desarrollo de la aplicación  
- **Spring Data JPA**: Para la persistencia de datos  
- **Lombok**: Para reducir código repetitivo en las entidades  
  
La aplicación sigue el patrón de diseño MVC (Modelo-Vista-Controlador) y utiliza anotaciones de Spring para definir componentes como controladores, servicios y repositorios.  
  
## Flujo de Datos  
  
El flujo típico de datos en la aplicación es el siguiente:  
1. El cliente envía una solicitud HTTP a un endpoint específico  
2. El controlador correspondiente recibe la solicitud [6](#1-5)   
3. El controlador llama al servicio apropiado [7](#1-6)   
4. El servicio utiliza el repositorio para acceder a los datos  
5. El repositorio interactúa con la base de datos  
6. Los datos fluyen de vuelta a través de las capas hasta el cliente  
  
## Manejo de Errores  
  
Los controladores implementan un manejo básico de errores mediante bloques try-catch y códigos de estado HTTP apropiados:  
- **200 OK**: Solicitud GET, PUT o PATCH exitosa  
- **201 Created**: Solicitud POST exitosa [8](#1-7)   
- **204 No Content**: Solicitud DELETE exitosa o resultado GET vacío [9](#1-8)   
- **404 Not Found**: Recurso no encontrado (durante operaciones de actualización, parche o eliminación) [10](#1-9)
