package io.github.Istiyakmi9.models;

public class JDBCParameters {
    public String parameter;
    public Object value;
    public int type;

    public JDBCParameters(String parameter, Object value, int type) {
        this.parameter = parameter;
        this.value = value;
        this.type = type;
    }
}
