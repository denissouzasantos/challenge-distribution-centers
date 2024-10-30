package com.challenge.distribution.centers.repository;


import com.challenge.distribution.centers.model.Order;
import org.springframework.data.mongodb.repository.MongoRepository;



public interface OrderRepository extends MongoRepository<Order, String> {
}
