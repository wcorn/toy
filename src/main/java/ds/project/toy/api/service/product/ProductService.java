package ds.project.toy.api.service.product;

import ds.project.toy.api.controller.product.dto.response.PostProductResponse;
import ds.project.toy.api.service.product.dto.PostProductServiceDto;

public interface ProductService {

    PostProductResponse postProduct(PostProductServiceDto of);
}
