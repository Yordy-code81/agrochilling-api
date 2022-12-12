package com.example.agrochillingapi.sellers.domain.service.communication;

import com.example.agrochillingapi.sellers.resource.SellerResource;
import com.example.agrochillingapi.shared.domain.service.communication.BaseResponse;

public class RegisterSellerResponse extends BaseResponse<SellerResource> {

    public RegisterSellerResponse(String message) {
        super(message);
    }

    public RegisterSellerResponse(SellerResource resource) {
        super(resource);
    }
}
