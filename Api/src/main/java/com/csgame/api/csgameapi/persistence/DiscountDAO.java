package com.csgame.api.csgameapi.persistence;

<<<<<<< HEAD
import java.io.IOException;

import com.csgame.api.csgameapi.model.Discount;

=======
>>>>>>> f59cbdd9cf60293b9085369c404fb2a81ba500bc
public interface DiscountDAO {
    
    Discount getDiscount(int id) throws IOException;

    Discount createDiscount(Discount discount) throws IOException;

    Discount updateDiscount(Discount discount) throws IOException;

    boolean deleteDiscount(Discount discount) throws IOException;
}
