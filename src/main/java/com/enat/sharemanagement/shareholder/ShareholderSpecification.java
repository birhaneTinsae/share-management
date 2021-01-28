package com.enat.sharemanagement.shareholder;

import com.enat.sharemanagement.utils.SearchCriteria;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

public class ShareholderSpecification implements Specification<Shareholder> {
    private SearchCriteria criteria;

    public ShareholderSpecification(final SearchCriteria criteria) {
        super();
        this.criteria = criteria;
    }

    public SearchCriteria getCriteria() {
        return criteria;
    }

    @Override
    public Predicate toPredicate(final Root<Shareholder> root, final CriteriaQuery<?> query, final CriteriaBuilder builder) {
        switch (criteria.getOperation()) {
            case EQUALITY:

                if (criteria.getKey().equals("status")) {
                    return builder.equal(root.get(criteria.getKey()), Status.valueOf(criteria.getValue().toString()));
                }
                if (criteria.getKey().equals("martialStatus")) {
                    return builder.equal(root.get(criteria.getKey()), MaritalStatus.valueOf(criteria.getValue().toString()));
                }
                if (criteria.getKey().equals("sex")) {
                    return builder.equal(root.get(criteria.getKey()), Sex.valueOf(criteria.getValue().toString()));
                }
                return builder.equal(root.get(criteria.getKey()), criteria.getValue());
            case NEGATION:
                return builder.notEqual(root.get(criteria.getKey()), criteria.getValue());
            case GREATER_THAN:
                return builder.greaterThan(root.get(criteria.getKey()), criteria.getValue().toString());
            case LESS_THAN:
                return builder.lessThan(root.get(criteria.getKey()), criteria.getValue().toString());
            case LIKE:
                return builder.like(root.get(criteria.getKey()), criteria.getValue().toString());
            case STARTS_WITH:
                return builder.like(root.get(criteria.getKey()), criteria.getValue() + "%");
            case ENDS_WITH:
                return builder.like(root.get(criteria.getKey()), "%" + criteria.getValue());
            case CONTAINS:
                return builder.like(root.get(criteria.getKey()), "%" + criteria.getValue() + "%");
            default:
                return null;
        }
    }

}
