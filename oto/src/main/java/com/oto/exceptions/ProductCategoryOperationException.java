package com.oto.exceptions;

import java.io.Serializable;

/**
 *
 */
public class ProductCategoryOperationException extends RuntimeException{


    private static final long serialVersionUID = 1182563719599527969L;

    public ProductCategoryOperationException(String msg){
        super(msg);
    }
}
