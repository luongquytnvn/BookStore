package com.codegym.controllers;

import com.codegym.models.Author;
import com.codegym.models.order.OrderItem;
import com.codegym.repositories.OrderItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.Optional;
@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/api/order-item")
public class OrderItemController {
    @Autowired
    OrderItemRepository orderItemRepository;
    @GetMapping("")
    public ResponseEntity<Iterable<OrderItem>> showListOrderItem() {
        Iterable<OrderItem> orderItems = orderItemRepository.findAll();
        return new ResponseEntity<Iterable<OrderItem>>(orderItems, HttpStatus.OK);
    }

    @GetMapping("/cart/{id}")
    public ResponseEntity<Iterable<OrderItem>> showListOrderItemInOrder(@PathVariable Long id) {
        Iterable<OrderItem> orderItems = orderItemRepository.findAll();
        return new ResponseEntity<Iterable<OrderItem>>(orderItems, HttpStatus.OK);
    }

    @PostMapping("")
    public ResponseEntity<?> addNewOrderItem(@RequestBody OrderItem orderItem){
        try {
            orderItemRepository.save(orderItem);
            return new ResponseEntity<OrderItem>(orderItem, HttpStatus.CREATED);
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
    @GetMapping("/{id}")
    public ResponseEntity<OrderItem> getOrderItemById(@PathVariable Long id){
        Optional<OrderItem> orderItem = orderItemRepository.findById(id);
        if (orderItem.isPresent()){
            System.out.println("find order item");
            return new ResponseEntity<OrderItem>(orderItem.get(), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
