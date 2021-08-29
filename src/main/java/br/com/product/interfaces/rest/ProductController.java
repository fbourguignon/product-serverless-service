package br.com.product.interfaces.rest;

import br.com.product.application.mapper.ProductMapper;
import br.com.product.application.service.ProductService;
import br.com.product.interfaces.requests.ProductRequest;
import br.com.product.interfaces.responses.ProductResponse;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.*;

import javax.inject.Inject;
import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@Controller("products")
public class ProductController {

    private final ProductMapper mapper;
    private final ProductService service;

    @Inject
    public ProductController(ProductMapper mapper, ProductService service) {
        this.mapper = mapper;
        this.service = service;
    }

    @Get
    public HttpResponse<List<ProductResponse>> list(){
        var products = service.list();
        var response = products
                .stream()
                .map(mapper::toResponse)
                .collect(Collectors.toList());
        return HttpResponse.ok(response);
    }

    @Post
    public HttpResponse<ProductResponse> save(@Valid @Body ProductRequest request){
        var product = mapper.toEntity(request);
        var response = mapper.toResponse(service.save(product));
        return HttpResponse.created(response);
    }

    @Get("/{id}")
    public HttpResponse<ProductResponse> get(@PathVariable String id){
        var response = mapper.toResponse(service.get(id));
        return HttpResponse.ok(response);
    }

    @Delete("/{id}")
    public HttpResponse delete(@PathVariable String id){
        return HttpResponse.noContent();
    }

    @Put("/{id}")
    public HttpResponse<ProductResponse> put(@PathVariable String id,@Valid @Body ProductRequest productRequest){
        var product = mapper.toEntity(productRequest);
        var response = mapper.toResponse(service.update(id,product));
        return HttpResponse.ok(response);
    }
}
