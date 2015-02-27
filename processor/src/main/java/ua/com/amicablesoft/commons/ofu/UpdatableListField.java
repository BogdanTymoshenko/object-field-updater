package ua.com.amicablesoft.commons.ofu;

import com.squareup.javawriter.JavaWriter;

import java.io.IOException;

/**
 * Created by bogdan on 2/26/15.
 */
public class UpdatableListField implements Field {

    private String listVarName;

    public UpdatableListField(String listVarName) {
        this.listVarName = listVarName;
    }

    @Override
    public void emitUpdateStatement(JavaWriter writer) throws IOException {
        // do nothing
    }
}
