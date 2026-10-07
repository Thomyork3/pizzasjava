package mx.unam.aragon.tsp.vmartinez.pizzas.controllers;

import java.util.List;
import mx.unam.aragon.tsp.vmartinez.pizzas.dtos.ClienteDto;
import mx.unam.aragon.tsp.vmartinez.pizzas.dtos.ClienteDtoIn;
import mx.unam.aragon.tsp.vmartinez.pizzas.services.ClienteService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/clientes")
public class ClienteController {

    private final ClienteService service;

    public ClienteController(ClienteService service) {
        this.service = service;
    }

    @GetMapping
    public List<ClienteDto> listar() {
        return service.listar();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClienteDto> obtener(@PathVariable("id") int id) {
        return service.obtener(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<ClienteDto> crear(@RequestBody ClienteDtoIn in) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.crear(in));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ClienteDto> actualizar(@PathVariable("id") int id, @RequestBody ClienteDtoIn in) {
        return service.actualizar(id, in)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable("id") int id) {
        return service.eliminar(id) ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }
}