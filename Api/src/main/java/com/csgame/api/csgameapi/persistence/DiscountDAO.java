package com.csgame.api.csgameapi.persistence;

import java.io.IOException;

import com.csgame.api.csgameapi.model.Discount;

public interface DiscountDAO {
    
    Discount getDiscount(int id) throws IOException;

    Discount createDiscount(Discount discount) throws IOException;

    Discount updateDiscount(Discount discount) throws IOException;

    boolean deleteDiscount(int id) throws IOException;
}
