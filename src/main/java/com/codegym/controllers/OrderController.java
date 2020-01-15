package com.codegym.controllers;

import com.codegym.models.order.Order;
import com.codegym.services.impl.OrderServiceImpl;
import com.codegym.services.impl.user.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping(value = "api/order", produces = MediaType.APPLICATION_JSON_VALUE)
public class OrderController {
    @Autowired
    OrderServiceImpl orderService;
    @Autowired
    UserDetailsServiceImpl userDetailsService;
    @PostMapping("/save")
    public ResponseEntity createOrder(@RequestBody Order order){
        orderService.save(order);
        return new ResponseEntity(HttpStatus.CREATED);
    }
    //cc
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/list")
    public ResponseEntity<List<Order>> findAllOrder(){
        List<Order> orderList = orderService.findAll();
        if(orderList != null){
            return new ResponseEntity<List<Order>>(orderList, HttpStatus.OK);
        }else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    @GetMapping("/{id}")
    public ResponseEntity<Order> findById(@PathVariable Long id){
        Optional<Order> order = orderService.findById(id);
        if(order.isPresent()){
            return new ResponseEntity<Order>(order.get(),HttpStatus.OK);
        }else {
            return new ResponseEntity<Order>(HttpStatus.NOT_FOUND );
        }
    }
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/update")
    public ResponseEntity updateOrder(@RequestBody Order order){
        Optional<Order> currentOrder = orderService.findById(order.getId());
        if(currentOrder.isPresent()){
            currentOrder.get().setUser(order.getUser());
            currentOrder.get().setDate(order.getDate());
            currentOrder.get().setStatus(order.getStatus());
            currentOrder.get().setOrderItem(order.getOrderItem());
            currentOrder.get().setTotal(order.getTotal());
            orderService.save(currentOrder.get());
            return new ResponseEntity(HttpStatus.OK);
        }else {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
    }
    @DeleteMapping("/delete")
    public ResponseEntity deleteOrder(@RequestBody Order order) {
        Optional<Order> currentOrder = orderService.findById(order.getId());
        if (currentOrder.isPresent()) {
            orderService.deleteOrder(currentOrder.get());
            return new ResponseEntity(HttpStatus.OK);
        } else {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
    }
}
