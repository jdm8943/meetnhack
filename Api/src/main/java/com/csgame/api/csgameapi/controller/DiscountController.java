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

    @PostMapping("")
    public ResponseEntity<Discount> createDiscount(@RequestBody Discount discount) {
        LOG.info("POST /discounts " + discount);
        String name = discount.getDiscount();
        try{
            Discount response = discountDAO.getDiscount(discount.getId);
            if(response == null){
                return new ResponseEntity<Discount>(discountDAO.createDiscount(discount), HttpStatus.CREATED);
            }
            else
                return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
        catch(IOException e){
            LOG.log(Level.SEVERE,e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Discount> getDiscount(@PathVariable int id) {
        LOG.info("GET /discounts/" + id);
        try {
            Discount discount = discountDAO.getDiscount(id);
            if (discount != null)
                return new ResponseEntity<Discount>(discount, HttpStatus.OK);
            else
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        catch(IOException e) {
            LOG.log(Level.SEVERE,e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("")
    public ResponseEntity<Discount> updateDiscount(@RequestBody Discount discount) {
        LOG.info("PUT /discounts " + discount);
        
        try {
            Discount newDiscount = discountDAO.updateDiscount(discount);
            if (newDiscount != null)
                return new ResponseEntity<Discount>(newDiscount,HttpStatus.OK);
            else
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        catch(IOException e) {
            LOG.log(Level.SEVERE,e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
