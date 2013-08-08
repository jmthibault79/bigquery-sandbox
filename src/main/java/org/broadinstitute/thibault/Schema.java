package org.broadinstitute.thibault;

import com.google.api.services.bigquery.model.TableFieldSchema;
import com.google.api.services.bigquery.model.TableSchema;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: thibault
 * Date: 8/12/13
 * Time: 12:49 PM
 * To change this template use File | Settings | File Templates.
 */
public class Schema {

    static TableSchema getSimpleSchema() {
        List<TableFieldSchema> fields = new ArrayList<TableFieldSchema>();
        TableFieldSchema field = new TableFieldSchema().setName("my_col").setMode("nullable").setType("string");
        fields.add(field);

        return new TableSchema().setFields(fields);
    }
}
