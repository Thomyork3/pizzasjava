package mx.unam.aragon.tsp.vmartinez.pizzas.controllers;

import mx.unam.aragon.tsp.vmartinez.pizzas.models.Cliente;
import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentSkipListMap;
import java.util.concurrent.atomic.AtomicInteger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/clientes")
public class ClienteController {

    private final Map<Integer, Cliente> datos = new ConcurrentSkipListMap<>();
    private final AtomicInteger contador = new AtomicInteger();

    @GetMapping
    public Collection<Cliente> listar() {
        return datos.values();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Cliente> obtener(@PathVariable("id") int id) {
        Cliente item = datos.get(id);
        return item == null ? ResponseEntity.notFound().build() : ResponseEntity.ok(item);
    }

    @PostMapping
    public ResponseEntity<Cliente> crear(@RequestBody Cliente nueva) {
        int id = contador.incrementAndGet();
        Cliente item = new Cliente(id, nueva.nombre(), nueva.telefono(), nueva.direccion());
        datos.put(id, item);
        return ResponseEntity.status(HttpStatus.CREATED).body(item);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Cliente> actualizar(@PathVariable("id") int id, @RequestBody Cliente cambios) {
        if (!datos.containsKey(id)) {
            return ResponseEntity.notFound().build();
        }
        Cliente item = new Cliente(id, cambios.nombre(), cambios.telefono(), cambios.direccion());
        datos.put(id, item);
        return ResponseEntity.ok(item);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable("id") int id) {
        return datos.remove(id) == null ? ResponseEntity.notFound().build() : ResponseEntity.noContent().build();
    }
}