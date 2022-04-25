package com.dataken.pojo;

import javax.persistence.Index;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import java.lang.annotation.Annotation;

public class DynamicTable implements Table {

    private String name;
    private String catalog;
    private String schema;
    public DynamicTable(String name, String catalog, String schema) {
        this.name = name;
        this.catalog = catalog;
        this.schema = schema;
    }

    @Override
    public String name() {
        return name;
    }

    @Override
    public String catalog() {
        return catalog;
    }

    @Override
    public String schema() {
        return schema;
    }

    @Override
    public UniqueConstraint[] uniqueConstraints() {
        return new UniqueConstraint[0];
    }

    @Override
    public Index[] indexes() {
        return new Index[0];
    }

    @Override
    public Class<? extends Annotation> annotationType() {
        return DynamicTable.class;
    }
}
