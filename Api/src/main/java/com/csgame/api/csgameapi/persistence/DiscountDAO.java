package com.csgame.api.csgameapi.persistance.DiscountDAO;

import java.io.IOException;

import com.csgame.api.csgameapi.model.Discount;

public interface DiscountDAO {
    
    Boolean getDiscount(int id) throws IOException;

    Discount createDiscount(Discount discount) throws IOException;

    Discount updateDiscount(Discount discount) throws IOException;

    boolean deleteDiscount(Discount discount) throws IOException;
}
