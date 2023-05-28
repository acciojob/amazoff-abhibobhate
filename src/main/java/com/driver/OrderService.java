package com.driver;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderService {
    @Autowired
    OrderRepository orderRepository;
    public void addOrder(Order order) {
        orderRepository.addOrder(order);
    }

    public void addPartner(String partnerId) {
        orderRepository.addPartner(partnerId);
    }

    public void addOrderPartnerPair(String orderId, String partnerId) {
        if(!orderRepository.checkOrderId(orderId)){
            throw new OrderIdDoesNotExists("Order Id Does Not Exists");
            //return;
        }
        if(!orderRepository.checkPartnerId(partnerId)){
            throw new PartnerIdDoesNotExists("PartnerIdDoesNotExists");
            //return;
        }
        orderRepository.addOrderPartnerPair(orderId,partnerId);
    }

    public Order getOrderById(String orderId) {
        if(!orderRepository.checkOrderId(orderId)){
            throw new OrderIdDoesNotExists("Order Id Does Not Exists");
        }
        return orderRepository.getOrderById(orderId);
    }

    public DeliveryPartner getPartnerById(String partnerId) {
        if(!orderRepository.checkPartnerId(partnerId)){
            throw new PartnerIdDoesNotExists("PartnerIdDoesNotExists");
            //return;
        }
        return orderRepository.getPartnerById(partnerId);
    }

    public Integer getOrderCountByPartnerId(String partnerId) {
        if(!orderRepository.checkPartnerId(partnerId)){
            throw new PartnerIdDoesNotExists("PartnerIdDoesNotExists");
            //return;
        }
        return orderRepository.getOrderCountByPartnerId(partnerId);
    }

    public List<String> getOrdersByPartnerId(String partnerId) {
        if(!orderRepository.checkPartnerId(partnerId)){
            throw new PartnerIdDoesNotExists("PartnerIdDoesNotExists");
            //return;
        }
        return orderRepository.getOrdersByPartnerId(partnerId);
    }

    public List<String> getAllOrders() {
        return orderRepository.getAllOrders();
    }

    public Integer getCountOfUnassignedOrders() {
        return orderRepository.getAmazonOrder().size() - orderRepository.getOrderAssigned().size();
    }

    public Integer getOrdersLeftAfterGivenTimeByPartnerId(String time, String partnerId) {
        List<String> orders = getOrdersByPartnerId(partnerId);
        Integer t = TimeUtil.convertToInt(time);

        int count = 0;
        for(String s : orders){
            String s1 = s+"0";
            Integer t1 = getOrderById(s).getDeliveryTime();
            if(t1 > t){
                count++;
            }
        }
        return count;
    }

    public String getLastDeliveryTimeByPartnerId(String partnerId) {
        List<String> orders = getOrdersByPartnerId(partnerId);
        if(orders.size()==0)return "";
        int maxTime = getOrderById(orders.get(0)).getDeliveryTime();
        String max = orders.get(0);

        for(String s : orders){
            int currTime = getOrderById(s).getDeliveryTime();
            if(currTime > maxTime){
                maxTime = currTime;
                max = s;
            }
        }
        return max;
    }

    public void deletePartnerById(String partnerId) {
        if(!orderRepository.checkPartnerId(partnerId)){
            throw new PartnerIdDoesNotExists("PartnerIdDoesNotExists");
            //return;
        }
        orderRepository.unassignedAllOrdersOfPartner(partnerId);
        orderRepository.deletePartnerId(partnerId);
    }

    public void deleteOrderById(String orderId) {
        if(!orderRepository.checkOrderId(orderId)){
            throw new OrderIdDoesNotExists("Order Id Does Not Exists");
        }
        orderRepository.deleteOrderAssined(orderId);
    }
}
