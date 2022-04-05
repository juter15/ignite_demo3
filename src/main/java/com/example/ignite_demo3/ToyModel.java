package com.example.ignite_demo3;

import lombok.Data;
import org.apache.ignite.cache.query.annotations.QuerySqlField;

import java.io.Serializable;

@Data
public class ToyModel implements Serializable {
    @QuerySqlField(index = true)
    private String toy_id;
    @QuerySqlField(index = true)
    private String toy_name;
}
