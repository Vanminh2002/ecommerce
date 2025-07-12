package com.example.userservice.utilities.paginate;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class FilterBuilder<T> {

    public Specification<T> build(Map<String, String> query, Map<String, FilterType> config) {
        return (root, cq, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            for (Map.Entry<String, FilterType> entry : config.entrySet()) {
                String field = entry.getKey();
                FilterType type = entry.getValue();
                String value = query.get(field);

                if (value == null || value.isEmpty()) continue;

                switch (type) {
                    case LIKE -> predicates.add(cb.like(cb.lower(root.get(field)), "%" + value.toLowerCase() + "%"));
                    case EQ -> predicates.add(cb.equal(root.get(field), value));
                    case NUM -> predicates.add(cb.equal(root.get(field), Integer.valueOf(value)));
                    case BOOL -> predicates.add(cb.equal(root.get(field), Boolean.parseBoolean(value)));
                    case IN -> {
                        List<String> values = Arrays.asList(value.split(","));
                        predicates.add(root.get(field).in(values));
                    }
                    case BETWEEN -> {
                        String[] range = value.split("-");
                        if (range.length == 2)
                            predicates.add(cb.between(root.get(field), Integer.parseInt(range[0]), Integer.parseInt(range[1])));
                    }
                    case DATERANGE -> {
                        String[] dates = value.split("_");
                        if (dates.length == 2) {
                            LocalDate from = LocalDate.parse(dates[0]);
                            LocalDate to = LocalDate.parse(dates[1]);
                            predicates.add(cb.between(root.get(field), from, to));
                        }
                    }
                }
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }
}
