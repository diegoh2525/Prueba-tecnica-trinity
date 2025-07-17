package com.prueba.trinity.Service;

import com.prueba.trinity.Dto.Request.ClienteRequest;
import com.prueba.trinity.Dto.Response.ClienteResponse;
import com.prueba.trinity.Entity.Cliente;
import com.prueba.trinity.Mapper.ClienteMapper;
import com.prueba.trinity.Repository.ClienteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ClienteService {

    private final ClienteRepository clienteRepository;

    public ClienteResponse crearCliente(ClienteRequest dto) {
        if (esMenorDeEdad(dto.getFechaNacimiento())) {
            throw new IllegalArgumentException("El cliente no puede ser menor de edad");
        }

        if (clienteRepository.existsByNumeroIdentificacion(dto.getNumeroIdentificacion())) {
            throw new IllegalArgumentException("Ya existe un cliente con ese número de identificación");
        }

        Cliente cliente = ClienteMapper.toEntity(dto);
        Cliente guardado = clienteRepository.save(cliente);
        return ClienteMapper.toResponseDTO(guardado);
    }

    public ClienteResponse actualizarCliente(Long id, ClienteRequest dto) {
        Cliente cliente = clienteRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Cliente no encontrado"));

        ClienteMapper.updateEntity(cliente, dto);
        Cliente actualizado = clienteRepository.save(cliente);
        return ClienteMapper.toResponseDTO(actualizado);
    }

    public void eliminarCliente(Long id) {
        Cliente cliente = clienteRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Cliente no encontrado"));
        // Validar si tiene productos antes de eliminar
        clienteRepository.delete(cliente);
    }

    public List<ClienteResponse> listarClientes() {
        return clienteRepository.findAll()
                .stream()
                .map(ClienteMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    public ClienteResponse obtenerCliente(Long id) {
        Cliente cliente = clienteRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Cliente no encontrado"));
        return ClienteMapper.toResponseDTO(cliente);
    }

    private boolean esMenorDeEdad(LocalDate fechaNacimiento) {
        return Period.between(fechaNacimiento, LocalDate.now()).getYears() < 18;
    }
}