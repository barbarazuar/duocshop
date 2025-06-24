package com.duocshop.duocshop;

import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import com.duocshop.duocshop.model.Calificacion;
import com.duocshop.duocshop.model.Categoria;
import com.duocshop.duocshop.model.CategoriaProducto;
import com.duocshop.duocshop.model.Marca;
import com.duocshop.duocshop.model.Producto;
import com.duocshop.duocshop.model.Sede;
import com.duocshop.duocshop.model.Usuario;
import com.duocshop.duocshop.model.UsuarioCalificacion;
import com.duocshop.duocshop.model.UsuarioProducto;
import com.duocshop.duocshop.repository.CalificacionRepository;
import com.duocshop.duocshop.repository.CategoriaProductoRepository;
import com.duocshop.duocshop.repository.CategoriaRepository;
import com.duocshop.duocshop.repository.MarcaRepository;
import com.duocshop.duocshop.repository.ProductoRepository;
import com.duocshop.duocshop.repository.SedeRepository;
import com.duocshop.duocshop.repository.UsuarioCalificacionRepository;
import com.duocshop.duocshop.repository.UsuarioProductoRepository;
import com.duocshop.duocshop.repository.UsuarioRepository;

import net.datafaker.Faker;

@Component
@Profile("test")
public class DataLoader implements CommandLineRunner {

    @Autowired
    private CalificacionRepository calificacionRepository;

    @Autowired
    private CategoriaRepository categoriaRepository;

    @Autowired
    private MarcaRepository marcaRepository;

    @Autowired
    private ProductoRepository productoRepository;

    @Autowired
    private SedeRepository sedeRepository;

    @Autowired
    private UsuarioCalificacionRepository usuarioCalificacionRepository;

    @Autowired
    private UsuarioProductoRepository usuarioProductoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private CategoriaProductoRepository categoriaProductoRepository;

    @Override
    public void run(String... args) throws Exception {
        Faker faker = new Faker();
        Random random = new Random();

        try {
            // Generar sedes
            for (int i = 0; i < 5; i++) {
                Sede sede = new Sede();
                sede.setNombre("DUOC UC - Campus " + faker.address().cityName());
                sede.setDireccion(faker.address().fullAddress());
                sedeRepository.save(sede);
            }
            
            // Generar marcas
            for (int i = 0; i < 10; i++) {
                Marca marca = new Marca();
                marca.setNombre(faker.company().name());
                marcaRepository.save(marca);
            }
            
            // Generar categorías
            for (int i = 0; i < 8; i++) {
                Categoria categoria = new Categoria();
                categoria.setNombre(faker.commerce().department());
                categoriaRepository.save(categoria);
            }
            
            List<Sede> sedes = sedeRepository.findAll();
            List<Marca> marcas = marcaRepository.findAll();

            // Generar usuarios
            for (int i = 0; i < 20; i++) {
                Usuario usuario = new Usuario();
                usuario.setNombre(faker.name().firstName());
                usuario.setApellido(faker.name().lastName());
                usuario.setCorreo(faker.internet().emailAddress());
                usuario.setContrasena(faker.internet().password(6, 6, true, true, true));
                usuario.setSede(sedes.get(random.nextInt(sedes.size())));
                usuarioRepository.save(usuario);
            }
            
            // Generar productos
            for (int i = 0; i < 30; i++) {
                Producto producto = new Producto();
                producto.setNombre(faker.commerce().productName());
                producto.setDescripcion(faker.lorem().paragraph());
                producto.setPrecio(faker.number().numberBetween(1000, 50000));
                producto.setMarca(marcas.get(random.nextInt(marcas.size())));
                productoRepository.save(producto);
            }
            
            // Generar calificaciones
            for (int i = 0; i < 25; i++) {
                Calificacion calificacion = new Calificacion();
                calificacion.setPuntaje(faker.number().numberBetween(1, 6));
                calificacion.setComentario(faker.lorem().sentence());
                calificacionRepository.save(calificacion);
            }
            
            List<Usuario> usuarios = usuarioRepository.findAll();
            List<Producto> productos = productoRepository.findAll();
            List<Calificacion> calificaciones = calificacionRepository.findAll();
            List<Categoria> categorias = categoriaRepository.findAll();

            // Generar relaciones Usuario-Producto
            for (int i = 0; i < 50; i++) {
                UsuarioProducto usuarioProducto = new UsuarioProducto();
                usuarioProducto.setUsuario(usuarios.get(random.nextInt(usuarios.size())));
                usuarioProducto.setProducto(productos.get(random.nextInt(productos.size())));
                usuarioProductoRepository.save(usuarioProducto);
            }
            
            // Generar relaciones Usuario-Calificación
            for (int i = 0; i < 40; i++) {
                UsuarioCalificacion usuarioCalificacion = new UsuarioCalificacion();
                usuarioCalificacion.setUsuario(usuarios.get(random.nextInt(usuarios.size())));
                usuarioCalificacion.setCalificacion(calificaciones.get(random.nextInt(calificaciones.size())));
                usuarioCalificacionRepository.save(usuarioCalificacion);
            }
        
            // Generar relaciones Categoría-Producto
            for (int i = 0; i < 60; i++) {
                CategoriaProducto categoriaProducto = new CategoriaProducto();
                categoriaProducto.setCategoria(categorias.get(random.nextInt(categorias.size())));
                categoriaProducto.setProducto(productos.get(random.nextInt(productos.size())));
                categoriaProductoRepository.save(categoriaProducto);
            }

        } catch (Exception e) {
            System.err.println("Error durante la carga de datos de prueba: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
