package com.example.agrochillingapi.sellers.domain.service.communication;

import com.example.agrochillingapi.sellers.resource.AuthenticateSellerResource;
import com.example.agrochillingapi.shared.domain.service.communication.BaseResponse;

public class AuthenticateSellerResponse extends BaseResponse<AuthenticateSellerResource> {

    public AuthenticateSellerResponse(String message) {
        super(message);
    }

    public AuthenticateSellerResponse(AuthenticateSellerResource resource) {
        super(resource);
    }
}
