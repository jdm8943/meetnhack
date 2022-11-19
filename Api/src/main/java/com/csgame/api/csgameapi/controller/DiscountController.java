package com.csgame.api.csgameapi.controller.DiscountController;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.csgame.api.csgameapi.persistance.DiscountDAO;

@RestController
@RequestMapping("discounts")
public class DiscountController {
    private static final Logger LOG = Logger.getLogger(UserController.class.getName());
    private DiscountDAO discountDAO;

    public DiscountController(DiscountDAO discountDAO) {
        this.discountDAO = discountDAO;
    }

    
}
