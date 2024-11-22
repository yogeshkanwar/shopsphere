package com.example.ShopSphere.specification;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;
import com.example.ShopSphere.entity.Product;
import com.example.ShopSphere.param.ProductSearchParam;
import com.example.ShopSphere.util.SpecificationFactory;


public interface ProductSpecification extends SpecificationFactory<Product>{
	default Specification<Product> filterProduct(ProductSearchParam param) {
		return (root, cq, cb) -> {

        List<Predicate> predicates = new ArrayList<Predicate>();

        if(param.getSearch() != null) {
            predicates.add(cb.like(root.get("name"), "%" + param.getSearch() + "%"));        }
        
         return cb.and(predicates.toArray(new Predicate[0]));                                                                                   
		};
	}
    
}



