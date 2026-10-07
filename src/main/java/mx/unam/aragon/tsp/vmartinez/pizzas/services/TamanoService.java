package mx.unam.aragon.tsp.vmartinez.pizzas.services;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentSkipListMap;
import java.util.concurrent.atomic.AtomicInteger;
import mx.unam.aragon.tsp.vmartinez.pizzas.dtos.TamanoDto;
import mx.unam.aragon.tsp.vmartinez.pizzas.dtos.TamanoDtoIn;
import mx.unam.aragon.tsp.vmartinez.pizzas.models.Tamano;
import org.springframework.stereotype.Service;

@Service
public class TamanoService {

    private final Map<Integer, Tamano> datos = new ConcurrentSkipListMap<>();
    private final AtomicInteger contador = new AtomicInteger();

    public List<TamanoDto> listar() {
        return datos.values().stream().map(this::aDto).toList();
    }

    public Optional<TamanoDto> obtener(int id) {
        return Optional.ofNullable(datos.get(id)).map(this::aDto);
    }

    public TamanoDto crear(TamanoDtoIn in) {
        int id = contador.incrementAndGet();
        Tamano tamano = new Tamano(id, in.nombre(), in.centimetros());
        datos.put(id, tamano);
        return aDto(tamano);
    }

    public Optional<TamanoDto> actualizar(int id, TamanoDtoIn in) {
        if (!datos.containsKey(id)) {
            return Optional.empty();
        }
        Tamano tamano = new Tamano(id, in.nombre(), in.centimetros());
        datos.put(id, tamano);
        return Optional.of(aDto(tamano));
    }

    public boolean eliminar(int id) {
        return datos.remove(id) != null;
    }

    private TamanoDto aDto(Tamano t) {
        return new TamanoDto(t.id(), t.nombre(), t.centimetros());
    }
}