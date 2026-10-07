package mx.unam.aragon.tsp.vmartinez.pizzas.controllers;

import java.util.List;
import mx.unam.aragon.tsp.vmartinez.pizzas.dtos.BebidaDto;
import mx.unam.aragon.tsp.vmartinez.pizzas.dtos.BebidaDtoIn;
import mx.unam.aragon.tsp.vmartinez.pizzas.services.BebidaService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/bebidas")
public class BebidaController {

    private final BebidaService service;

    public BebidaController(BebidaService service) {
        this.service = service;
    }

    @GetMapping
    public List<BebidaDto> listar() {
        return service.listar();
    }

    @GetMapping("/{id}")
    public ResponseEntity<BebidaDto> obtener(@PathVariable("id") int id) {
        return service.obtener(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<BebidaDto> crear(@RequestBody BebidaDtoIn in) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.crear(in));
    }

    @PutMapping("/{id}")
    public ResponseEntity<BebidaDto> actualizar(@PathVariable("id") int id, @RequestBody BebidaDtoIn in) {
        return service.actualizar(id, in)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable("id") int id) {
        return service.eliminar(id) ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }
}