package mx.unam.aragon.tsp.vmartinez.pizzas.services;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentSkipListMap;
import java.util.concurrent.atomic.AtomicInteger;
import mx.unam.aragon.tsp.vmartinez.pizzas.dtos.BebidaDto;
import mx.unam.aragon.tsp.vmartinez.pizzas.dtos.BebidaDtoIn;
import mx.unam.aragon.tsp.vmartinez.pizzas.models.Bebida;
import org.springframework.stereotype.Service;

@Service
public class BebidaService {

    private final Map<Integer, Bebida> datos = new ConcurrentSkipListMap<>();
    private final AtomicInteger contador = new AtomicInteger();

    public List<BebidaDto> listar() {
        return datos.values().stream().map(this::aDto).toList();
    }

    public Optional<BebidaDto> obtener(int id) {
        return Optional.ofNullable(datos.get(id)).map(this::aDto);
    }

    public BebidaDto crear(BebidaDtoIn in) {
        int id = contador.incrementAndGet();
        Bebida bebida = new Bebida(id, in.nombre(), in.mililitros(), in.precio());
        datos.put(id, bebida);
        return aDto(bebida);
    }

    public Optional<BebidaDto> actualizar(int id, BebidaDtoIn in) {
        if (!datos.containsKey(id)) {
            return Optional.empty();
        }
        Bebida bebida = new Bebida(id, in.nombre(), in.mililitros(), in.precio());
        datos.put(id, bebida);
        return Optional.of(aDto(bebida));
    }

    public boolean eliminar(int id) {
        return datos.remove(id) != null;
    }

    private BebidaDto aDto(Bebida b) {
        return new BebidaDto(b.id(), b.nombre(), b.mililitros(), b.precio());
    }
}