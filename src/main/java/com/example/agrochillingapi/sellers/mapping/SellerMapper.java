package com.example.agrochillingapi.sellers.mapping;

import com.example.agrochillingapi.security.domain.model.entity.Role;
import com.example.agrochillingapi.sellers.domain.model.entity.Seller;
import com.example.agrochillingapi.sellers.resource.SellerResource;
import com.example.agrochillingapi.sellers.resource.UpdateSellerResource;
import com.example.agrochillingapi.shared.mapping.EnhancedModelMapper;
import org.modelmapper.AbstractConverter;
import org.modelmapper.Converter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.io.Serializable;
import java.util.List;

public class SellerMapper implements Serializable {

    @Autowired
    EnhancedModelMapper mapper;

    Converter<Role, String> roleToString = new AbstractConverter<>() {
        @Override
        protected String convert(Role role) {
            return role == null ? null : role.getName().name();
        }
    };
    public SellerResource toResource(Seller model) {
        mapper.addConverter(roleToString);
        return mapper.map(model, SellerResource.class);
    }

    public Page<SellerResource> modelListToPage(List<Seller> modelList, Pageable pageable) {
        mapper.addConverter(roleToString);
        return new PageImpl<>(mapper.mapList(modelList, SellerResource.class), pageable, modelList.size());
    }

    public List<SellerResource> modelListToResource(List<Seller> modelList){
        mapper.addConverter(roleToString);
        return mapper.mapList(modelList, SellerResource.class);
    }


    public Seller toModel(UpdateSellerResource resource) {
        return mapper.map(resource, Seller.class);
    }
}
