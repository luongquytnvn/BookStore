package com.codegym.repositories;

import com.codegym.models.order.Order;
import com.codegym.models.order.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    Order findByStatusAndUser_Id(Status status, Long user_id);
}
