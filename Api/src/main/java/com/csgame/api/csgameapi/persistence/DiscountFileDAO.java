package com.csgame.api.csgameapi.persistence;

import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.TreeMap;

import org.springframework.beans.factory.annotation.Value;

import com.csgame.api.csgameapi.model.Discount;
import com.fasterxml.jackson.databind.ObjectMapper;

import ch.qos.logback.classic.Logger;

public class DiscountFileDAO implements DiscountDAO {
    public Map<Integer, Discount> discounts; 
    private ObjectMapper objectMapper;  
    private static int nextId;
    private String filename;

    public DiscountFileDAO(@Value("${discount.file}") String filename,ObjectMapper objectMapper) throws IOException {
        this.filename = filename;
        this.objectMapper = objectMapper;
        load();
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
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Discount createDiscount(Discount discount) throws IOException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Discount updateDiscount(Discount discount) throws IOException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public boolean deleteDiscount(Discount discount) throws IOException {
        // TODO Auto-generated method stub
        return false;
    }

    
}
