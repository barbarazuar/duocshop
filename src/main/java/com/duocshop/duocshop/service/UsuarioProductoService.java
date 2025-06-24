package com.duocshop.duocshop.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.duocshop.duocshop.model.UsuarioProducto;
import com.duocshop.duocshop.repository.UsuarioProductoRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class UsuarioProductoService {

    @Autowired
    private UsuarioProductoRepository usuarioProductoRepository;

    public List<UsuarioProducto> obtenerUsuarioProductos() {
        return usuarioProductoRepository.findAll();
    }
    public UsuarioProducto guardarUsuarioProducto(UsuarioProducto usuarioProducto) {
        return usuarioProductoRepository.save(usuarioProducto);
    }
    public UsuarioProducto actualizarUsuarioProducto(Integer id, UsuarioProducto usuarioProducto) {
        UsuarioProducto existente = usuarioProductoRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("UsuarioProducto no encontrado"));

        if (usuarioProducto.getUsuario() != null) {
            existente.setUsuario(usuarioProducto.getUsuario());
        }
        if (usuarioProducto.getProducto() != null) {
            existente.setProducto(usuarioProducto.getProducto());
        }
        return usuarioProductoRepository.save(existente);
    }
    public void eliminarUsuarioProducto(Integer id) {

        UsuarioProducto usuarioProducto = usuarioProductoRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("UsuarioProducto no encontrado"));

        usuarioProductoRepository.delete(usuarioProducto);
    }

    public List<Map<String, Object>> obtenerUsuarioProducto() {
        List<Object[]> resultados = usuarioProductoRepository.findUsuarioProducto();
        List<Map<String, Object>> lista = new ArrayList<>();

        for (Object[] fila : resultados) {
            Map<String, Object> datos = new HashMap<>();
            datos.put("usuarioProducto", fila[0]);
            datos.put("nombreUsuario", fila[1]);
            datos.put("nombreProducto", fila[2]);
            lista.add(datos);
        }

        return lista;
    }

    public UsuarioProducto obtenerUsuarioProductoPorId(Integer id) {
        return usuarioProductoRepository.findById(id)
        .orElseThrow(() -> new RuntimeException("UsuarioProducto no encontrado"));
    }
    
}
