package mx.unam.aragon.tsp.vmartinez.pizzas.controllers;

import java.util.List;
import mx.unam.aragon.tsp.vmartinez.pizzas.dtos.TamanoDto;
import mx.unam.aragon.tsp.vmartinez.pizzas.dtos.TamanoDtoIn;
import mx.unam.aragon.tsp.vmartinez.pizzas.services.TamanoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/tamanos")
public class TamanoController {

    private final TamanoService service;

    public TamanoController(TamanoService service) {
        this.service = service;
    }

    @GetMapping
    public List<TamanoDto> listar() {
        return service.listar();
    }

    @GetMapping("/{id}")
    public ResponseEntity<TamanoDto> obtener(@PathVariable("id") int id) {
        return service.obtener(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<TamanoDto> crear(@RequestBody TamanoDtoIn in) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.crear(in));
    }

    @PutMapping("/{id}")
    public ResponseEntity<TamanoDto> actualizar(@PathVariable("id") int id, @RequestBody TamanoDtoIn in) {
        return service.actualizar(id, in)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable("id") int id) {
        return service.eliminar(id) ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }
}