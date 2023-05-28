package com.driver;

import lombok.Getter;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
@Getter
public class OrderRepository {
    private HashMap<String,Order> amazonOrder = new HashMap<>();
    private HashMap<String, List<String>> orderPartnerPair = new HashMap<>();
    private HashMap<String,DeliveryPartner> partners = new HashMap<>();
    private HashSet<String> orderAssigned = new HashSet<>();


    public void addOrder(Order order) {
        amazonOrder.put(order.getId(),order);
    }

    public void addPartner(String partnerId) {
        partners.put(partnerId,new DeliveryPartner(partnerId));
    }

    public boolean checkOrderId(String orderId) {
        return amazonOrder.containsKey(orderId);
    }

    public void addOrderPartnerPair(String orderId, String partnerId) {
        if(!checkPartnerId(partnerId)){
            orderPartnerPair.put(partnerId,new ArrayList<>());
        }

        orderPartnerPair.get(partnerId).add(orderId);
        partners.get(partnerId).increamentOrder();
        orderAssigned.add(orderId);
    }

    public boolean checkPartnerId(String partnerId) {
        return partners.containsKey(partnerId);
    }

    public Order getOrderById(String orderId) {
        return amazonOrder.get(orderId);
    }

    public DeliveryPartner getPartnerById(String partnerId) {
        return partners.get(partnerId);
    }

    public Integer getOrderCountByPartnerId(String partnerId) {
        return partners.get(partnerId).getNumberOfOrders();
    }

    public List<String> getOrdersByPartnerId(String partnerId) {
        return orderPartnerPair.get(partnerId);
    }

    public List<String> getAllOrders() {
        return new ArrayList<>(amazonOrder.keySet());
    }

    public void unassignedAllOrdersOfPartner(String partnerId) {
        List<String> orders = orderPartnerPair.get(partnerId);

        for(String s : orders){
            orderAssigned.remove(s);
        }
    }

    public void deletePartnerId(String partnerId) {
        partners.remove(partnerId);
    }

    public void deleteOrderAssined(String orderId) {
        orderAssigned.remove(orderId);
        for(Map.Entry<String,List<String>> e : orderPartnerPair.entrySet()){
            String partner = e.getKey();
            List<String> orders = e.getValue();

            for(int i=0;i<orders.size();i++){
                String s = orders.get(i);
                if(s.equals(orderId)){
                    orderPartnerPair.get(partner).remove(i);
                    return;
                }
            }
        }
    }
}
