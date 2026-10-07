package mx.unam.aragon.tsp.vmartinez.pizzas.services;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentSkipListMap;
import java.util.concurrent.atomic.AtomicInteger;
import mx.unam.aragon.tsp.vmartinez.pizzas.dtos.ClienteDto;
import mx.unam.aragon.tsp.vmartinez.pizzas.dtos.ClienteDtoIn;
import mx.unam.aragon.tsp.vmartinez.pizzas.models.Cliente;
import org.springframework.stereotype.Service;

@Service
public class ClienteService {

    private final Map<Integer, Cliente> datos = new ConcurrentSkipListMap<>();
    private final AtomicInteger contador = new AtomicInteger();

    public List<ClienteDto> listar() {
        return datos.values().stream().map(this::aDto).toList();
    }

    public Optional<ClienteDto> obtener(int id) {
        return Optional.ofNullable(datos.get(id)).map(this::aDto);
    }

    public ClienteDto crear(ClienteDtoIn in) {
        int id = contador.incrementAndGet();
        Cliente cliente = new Cliente(id, in.nombre(), in.telefono(), in.direccion());
        datos.put(id, cliente);
        return aDto(cliente);
    }

    public Optional<ClienteDto> actualizar(int id, ClienteDtoIn in) {
        if (!datos.containsKey(id)) {
            return Optional.empty();
        }
        Cliente cliente = new Cliente(id, in.nombre(), in.telefono(), in.direccion());
        datos.put(id, cliente);
        return Optional.of(aDto(cliente));
    }

    public boolean eliminar(int id) {
        return datos.remove(id) != null;
    }

    private ClienteDto aDto(Cliente c) {
        return new ClienteDto(c.id(), c.nombre(), c.telefono(), c.direccion());
    }
}