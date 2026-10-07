package mx.unam.aragon.tsp.vmartinez.pizzas.services;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentSkipListMap;
import java.util.concurrent.atomic.AtomicInteger;
import mx.unam.aragon.tsp.vmartinez.pizzas.dtos.PizzaDto;
import mx.unam.aragon.tsp.vmartinez.pizzas.dtos.PizzaDtoIn;
import mx.unam.aragon.tsp.vmartinez.pizzas.models.Pizza;
import org.springframework.stereotype.Service;

@Service
public class PizzaService {

    private final Map<Integer, Pizza> datos = new ConcurrentSkipListMap<>();
    private final AtomicInteger contador = new AtomicInteger();

    public List<PizzaDto> listar() {
        return datos.values().stream().map(this::aDto).toList();
    }

    public Optional<PizzaDto> obtener(int id) {
        return Optional.ofNullable(datos.get(id)).map(this::aDto);
    }

    public PizzaDto crear(PizzaDtoIn in) {
        int id = contador.incrementAndGet();
        Pizza pizza = new Pizza(id, in.nombre(), in.descripcion(), in.precio());
        datos.put(id, pizza);
        return aDto(pizza);
    }

    public Optional<PizzaDto> actualizar(int id, PizzaDtoIn in) {
        if (!datos.containsKey(id)) {
            return Optional.empty();
        }
        Pizza pizza = new Pizza(id, in.nombre(), in.descripcion(), in.precio());
        datos.put(id, pizza);
        return Optional.of(aDto(pizza));
    }

    public boolean eliminar(int id) {
        return datos.remove(id) != null;
    }

    private PizzaDto aDto(Pizza p) {
        return new PizzaDto(p.id(), p.nombre(), p.descripcion(), p.precio());
    }
}