package mx.unam.aragon.tsp.vmartinez.pizzas.controllers;

import mx.unam.aragon.tsp.vmartinez.pizzas.models.Bebida;
import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentSkipListMap;
import java.util.concurrent.atomic.AtomicInteger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/bebidas")
public class BebidaController {

    private final Map<Integer, Bebida> datos = new ConcurrentSkipListMap<>();
    private final AtomicInteger contador = new AtomicInteger();

    @GetMapping
    public Collection<Bebida> listar() {
        return datos.values();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Bebida> obtener(@PathVariable("id") int id) {
        Bebida item = datos.get(id);
        return item == null ? ResponseEntity.notFound().build() : ResponseEntity.ok(item);
    }

    @PostMapping
    public ResponseEntity<Bebida> crear(@RequestBody Bebida nueva) {
        int id = contador.incrementAndGet();
        Bebida item = new Bebida(id, nueva.nombre(), nueva.mililitros(), nueva.precio());
        datos.put(id, item);
        return ResponseEntity.status(HttpStatus.CREATED).body(item);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Bebida> actualizar(@PathVariable("id") int id, @RequestBody Bebida cambios) {
        if (!datos.containsKey(id)) {
            return ResponseEntity.notFound().build();
        }
        Bebida item = new Bebida(id, cambios.nombre(), cambios.mililitros(), cambios.precio());
        datos.put(id, item);
        return ResponseEntity.ok(item);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable("id") int id) {
        return datos.remove(id) == null ? ResponseEntity.notFound().build() : ResponseEntity.noContent().build();
    }
}