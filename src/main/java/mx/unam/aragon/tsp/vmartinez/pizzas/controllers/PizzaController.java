package mx.unam.aragon.tsp.vmartinez.pizzas.controllers;

import java.util.List;
import mx.unam.aragon.tsp.vmartinez.pizzas.dtos.PizzaDto;
import mx.unam.aragon.tsp.vmartinez.pizzas.dtos.PizzaDtoIn;
import mx.unam.aragon.tsp.vmartinez.pizzas.services.PizzaService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/pizzas")
public class PizzaController {

    // Spring 
    private final PizzaService service;

    public PizzaController(PizzaService service) {
        this.service = service;
    }

    @GetMapping
    public List<PizzaDto> listar() {
        return service.listar();
    }

    @GetMapping("/{id}")
    public ResponseEntity<PizzaDto> obtener(@PathVariable("id") int id) {
        return service.obtener(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<PizzaDto> crear(@RequestBody PizzaDtoIn in) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.crear(in));
    }

    @PutMapping("/{id}")
    public ResponseEntity<PizzaDto> actualizar(@PathVariable("id") int id, @RequestBody PizzaDtoIn in) {
        return service.actualizar(id, in)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable("id") int id) {
        return service.eliminar(id) ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }
}