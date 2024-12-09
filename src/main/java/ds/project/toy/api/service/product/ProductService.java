package ds.project.toy.api.service.product;

import ds.project.toy.api.controller.product.dto.response.GetProductResponse;
import ds.project.toy.api.controller.product.dto.response.PostProductResponse;
import ds.project.toy.api.service.admin.dto.GetProductServiceDto;
import ds.project.toy.api.service.product.dto.DeleteInterestProductServiceDto;
import ds.project.toy.api.service.product.dto.PostInterestProductServiceDto;
import ds.project.toy.api.service.product.dto.PostProductServiceDto;

public interface ProductService {

    PostProductResponse postProduct(PostProductServiceDto of);

    GetProductResponse getProduct(GetProductServiceDto of);

    void postInterestProduct(PostInterestProductServiceDto of);

    void deleteInterestProduct(DeleteInterestProductServiceDto of);
}
