package com.csgame.api.csgameapi.persistence;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.csgame.api.csgameapi.model.Discount;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class DiscountFileDAO implements DiscountDAO {
    public Map<Integer, Discount> discounts; 
    private ObjectMapper objectMapper;  
    private static int nextId;
    private String filename;


    public DiscountFileDAO(@Value("${discounts.file}") String filename,ObjectMapper objectMapper) throws IOException {
        this.filename = filename;
        this.objectMapper = objectMapper;
        load();
    }

    private synchronized static int nextId() {
        int id = nextId;
        ++nextId;
        return id;
    }

    private boolean load() throws IOException {
        discounts = new TreeMap<>();
        nextId = 0;

        Discount[] discountArray = objectMapper.readValue(new File(filename),Discount[].class);

        for (Discount discount : discountArray) {
            discounts.put(discount.getId(),discount);
            if (discount.getId() > nextId)
                nextId = discount.getId();
        }
        ++nextId;
        return true;
    }

    @Override
    public Discount getDiscount(int id) throws IOException {
        synchronized(discounts) {
            if (discounts.containsKey(id))
                return discounts.get(id);
            else
                return null;
        }
    }

    @Override
    public Discount createDiscount(Discount discount) throws IOException {
        synchronized(discounts) {
            Discount newDiscount = new Discount(nextId(), discount.geName(), discount.getLevelRequired(), discount.getPointsRequired(), discount.getCompanyId());
            discounts.put(newDiscount.getId(),newDiscount);
            save();
            return newDiscount;
        }
    }

    private Discount[] getDiscountsArray(String containsText) { // if containsText == null, no filter
        ArrayList<Discount> discountArrayList = new ArrayList<>();

        for (Discount discount : discounts.values()) {
            if (containsText == null || discount.geName().contains(containsText)) {
                discountArrayList.add(discount);
            }
        }

        Discount[] discountArray = new Discount[discountArrayList.size()];
        discountArrayList.toArray(discountArray);
        return discountArray;
    }

    private boolean save() throws IOException {
        Discount[] userArray = getDiscountsArray(null);

        objectMapper.writeValue(new File(filename),userArray);
        return true;
    }

    @Override
    public Discount updateDiscount(Discount discount) throws IOException {
        synchronized(discounts) {
            if (discounts.containsKey(discount.getId()) == true){
                discounts.put(discount.getId(), discount);
                save(); // may throw an IOException
                return discount;
            }  
        }
        return null; // product does not exist
    }

    @Override
    public boolean deleteDiscount(int id) throws IOException {
        synchronized(discounts) {
            if (discounts.containsKey(id)) {
                discounts.remove(id);
                return save();
            }
            else
                return false;
        }
    }

    
}
