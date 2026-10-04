package mx.unam.aragon.tsp.vmartinez.pizzas.controllers;

import mx.unam.aragon.tsp.vmartinez.pizzas.models.Tamano;
import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentSkipListMap;
import java.util.concurrent.atomic.AtomicInteger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/tamanos")
public class TamanoController {

    private final Map<Integer, Tamano> datos = new ConcurrentSkipListMap<>();
    private final AtomicInteger contador = new AtomicInteger();

    @GetMapping
    public Collection<Tamano> listar() {
        return datos.values();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Tamano> obtener(@PathVariable("id") int id) {
        Tamano item = datos.get(id);
        return item == null ? ResponseEntity.notFound().build() : ResponseEntity.ok(item);
    }

    @PostMapping
    public ResponseEntity<Tamano> crear(@RequestBody Tamano nueva) {
        int id = contador.incrementAndGet();
        Tamano item = new Tamano(id, nueva.nombre(), nueva.centimetros());
        datos.put(id, item);
        return ResponseEntity.status(HttpStatus.CREATED).body(item);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Tamano> actualizar(@PathVariable("id") int id, @RequestBody Tamano cambios) {
        if (!datos.containsKey(id)) {
            return ResponseEntity.notFound().build();
        }
        Tamano item = new Tamano(id, cambios.nombre(), cambios.centimetros());
        datos.put(id, item);
        return ResponseEntity.ok(item);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable("id") int id) {
        return datos.remove(id) == null ? ResponseEntity.notFound().build() : ResponseEntity.noContent().build();
    }
}