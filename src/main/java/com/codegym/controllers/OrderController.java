package com.codegym.controllers;

import com.codegym.models.order.Order;
import com.codegym.models.order.OrderItem;
import com.codegym.models.order.Status;
import com.codegym.repositories.OrderRepository;
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
@RequestMapping("/api/order")
public class OrderController {
    @Autowired
    OrderServiceImpl orderService;
    @Autowired
    OrderRepository orderRepository;
    @Autowired
    UserDetailsServiceImpl userDetailsService;

    @PostMapping("")
    public ResponseEntity<?> createOrder(@RequestBody Order order) {
        order.setStatus(Status.normal);
        orderService.save(order);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("")
    public ResponseEntity<Order> findAllOrder() {
//        List<Order> orderList = orderService.findAll();
//        if(orderList != null){
        Order order = new Order();
        return new ResponseEntity<Order>(order, HttpStatus.OK);
    }

    @GetMapping("/cart/{id}")
    public ResponseEntity<Order> findByStatusAndUser_Id(@PathVariable("id") Long id) {
        Status status = Status.normal;
        Order order = orderRepository.findByStatusAndUser_Id(status, id);
        if (order!=null) {
            return new ResponseEntity<Order>(order, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/cart/order-item/{id}")
    public ResponseEntity<?> findIdOrderItemListByUserId(@PathVariable Long id) {
        List<Long> idOrderItemList = orderRepository.findIdOrderItemListByUserId(id);
        if (!idOrderItemList.isEmpty()) {
            return new ResponseEntity<List<Long>>(idOrderItemList, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Order> findById(@PathVariable Long id) {
        Optional<Order> order = orderService.findById(id);
        if (order.isPresent()) {
            return new ResponseEntity<Order>(order.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<Order>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("")
    public ResponseEntity<?> updateOrder(@RequestBody Order order) {
        Optional<Order> currentOrder = orderService.findById(order.getId());
        if (currentOrder.isPresent()) {
            currentOrder.get().setUser(order.getUser());
            currentOrder.get().setDate(order.getDate());
            currentOrder.get().setStatus(order.getStatus());
            currentOrder.get().setOrderItem(order.getOrderItem());
            currentOrder.get().setTotal(order.getTotal());
            orderService.save(currentOrder.get());
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/toOrder")
    public ResponseEntity<?> orderToOrder(@RequestBody Order order) {
        long millis = System.currentTimeMillis();
        java.sql.Date date = new java.sql.Date(millis);
        Optional<Order> currentOrder = orderService.findById(order.getId());
        if (currentOrder.isPresent()) {
            currentOrder.get().setDate(date);
            currentOrder.get().setStatus(Status.order);
            currentOrder.get().setTotal(order.getTotal());
            currentOrder.get().setPhone(order.getPhone());
            currentOrder.get().setShippingAddress(order.getShippingAddress());
            orderService.save(currentOrder.get());
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/add/{id}")
    public ResponseEntity<?> updateOrder(@RequestBody List<OrderItem> orderItems, @PathVariable("id") Long id) {

        Optional<Order> currentOrder = orderService.findById(id);
        if (currentOrder.isPresent()) {
            currentOrder.get().setOrderItem(orderItems);
            orderService.save(currentOrder.get());
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }



    @DeleteMapping("")
    public ResponseEntity<?> deleteOrder(@RequestBody Order order) {
        Optional<Order> currentOrder = orderService.findById(order.getId());
        if (currentOrder.isPresent()) {
            orderService.deleteOrder(currentOrder.get());
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
