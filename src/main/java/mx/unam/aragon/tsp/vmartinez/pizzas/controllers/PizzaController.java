package mx.unam.aragon.tsp.vmartinez.pizzas.controllers;

import mx.unam.aragon.tsp.vmartinez.pizzas.dtos.IdDto;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PizzaController {
    
    @GetMapping("/api/v1/pizzas")    
    public IdDto Saludar(){
        IdDto idDto = new IdDto(1, "Hola mundo");

        return  idDto;
    }
}