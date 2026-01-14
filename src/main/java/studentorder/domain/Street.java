package studentorder.domain;

public class Street {
    private Long streetCode;
    private String street_name;

    public Street() {
    }

    public Street(Long streetCode, String street_name) {
        this.streetCode = streetCode;
        this.street_name = street_name;
    }

    public Long getStreetCode() {
        return streetCode;
    }

    public void setStreetCode(Long streetCode) {
        this.streetCode = streetCode;
    }

    public String getStreet_name() {
        return street_name;
    }

    public void setStreet_name(String street_name) {
        this.street_name = street_name;
    }

    @Override
    public String toString() {
        return "Street{" +
                "streetCode=" + streetCode +
                ", street_name='" + street_name + '\'' +
                '}';
    }
}
