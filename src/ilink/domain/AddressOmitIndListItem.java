package ilink.domain;

import java.io.Serializable;

public class AddressOmitIndListItem implements Serializable {

    private static final long serialVersionUID = 1L;

    private String code;
    private String value;

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Override
    public String toString() {
        return "{" +
                "code:\"" + code + "\"" +
                ", value:\"" + value + "\"" +
                "}";
    }
}
