package co.develhope.crud.controllers;

import co.develhope.crud.entities.Car;
import co.develhope.crud.repositories.CarRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

@RestController
@RequestMapping("/cars")
public class CarController {

    @Autowired
    private CarRepository carRepository;

    // Create
    @PostMapping("/post")
    public Car create(@RequestBody Car car){
        return carRepository.saveAndFlush(car);
    }

    // Read one
    @GetMapping("/get")
    public List<Car> getAllCars(){
        return carRepository.findAll();
    }

    // Read all
    @GetMapping("/get/{id}")
    public Car getACar(@PathVariable long id){
        return carRepository.existsById(id)
                ? carRepository.getById(id)
                : new Car();
    }

    // Update
    @PutMapping("/put/{id}")
    public Car updateCar(@PathVariable long id, @RequestParam String type){
        Car car;
        if (carRepository.existsById(id)){
            car = carRepository.getById(id);
            car.setType(type);
            car = carRepository.saveAndFlush(car);
        }else{
            car = new Car();
        }
        return car;
    }

    // delete a specific car
    @DeleteMapping("/delete/{id}")
    public void deleteSingle(@PathVariable long id, HttpServletResponse response){
        if (carRepository.existsById(id))
            carRepository.deleteById(id);
        else
            response.setStatus(409);
    }

    // delete all
    @DeleteMapping("/delete")
    public void deleteAll(){
        carRepository.deleteAll();
    }

}
