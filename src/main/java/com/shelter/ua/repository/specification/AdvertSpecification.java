package com.shelter.ua.repository.specification;

import com.shelter.ua.dto.SearchCriteria;
import com.shelter.ua.entity.Advert;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.shelter.ua.entity.Advert.getSearchableFieldNames;

@SuppressWarnings("ALL")
@Slf4j
@AllArgsConstructor
public class AdvertSpecification implements Specification<Advert> {

    private final SearchCriteria criteria;

    public static Specification<Advert> of(List<SearchCriteria> params) {
        List<Specification<Advert>> specs = params.stream()
                .filter(x -> getSearchableFieldNames().contains(x.getField()))
                .map(AdvertSpecification::new)
                .collect(Collectors.toList());

        if (specs.isEmpty()) return null;

        Specification<Advert> result = specs.get(0);

        for (int i = 1; i < specs.size(); i++) {
            result = Specification.where(result).and(specs.get(i));
        }
        return result;
    }

    @Override
    public Predicate toPredicate(Root<Advert> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
        String field = criteria.getField();
        if (getSearchableFieldNames().contains(field)) {
            Class fieldClass = root.get(field).getJavaType();
            List values = castValues(fieldClass, criteria.getValue());

            if (fieldClass.equals(List.class)) {
                final List<Predicate> predicates = new ArrayList<>();

                values
                        .stream()
                        .forEach(val -> {
                            predicates.add(builder.isMember(val, root.get(field)));
                        });

                return builder.and(predicates.toArray(new Predicate[predicates.size()]));
            } else {
                return builder.equal(root.get(field), values.get(0));
            }
        }
        return null;
    }

    private List castValues(Class clazz, List<String> values) {
        List result = values;
        if (clazz.equals(Integer.class)) {
            result = values.stream()
                    .map(this::mapToInteger)
                    .collect(Collectors.toList());
        }
        return result;
    }

    private Integer mapToInteger(String value) {
        try {
            return Integer.valueOf(value);
        } catch (NumberFormatException e) {
            log.error("Cannot convert '{}' to Integer.", value, e);
            throw new RuntimeException("Incorrect search value '" + value + "' for integer field");
        }
    }
}