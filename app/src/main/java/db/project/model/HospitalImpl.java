package db.project.model;

public class HospitalImpl implements Hospital {

    private final Integer code;
    private final String name;
    private final String addressCity;
    private final String addressWay;
    private final String addressNumber;
    private final ASL asl;
    
    public HospitalImpl(final Integer code, final String name, final String addressCity, final String addressWay,
            final String addressNumber, final ASL asl) {
        this.code=code;
        this.name=name;
        this.addressCity = addressCity;
        this.addressWay = addressWay;
        this.addressNumber = addressNumber;
        this.asl = asl;
    }

    @Override
    public Integer getCode() {
        return this.code;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public String getAddressCity() {
        return this.addressCity;
    }

    @Override
    public String getAddressWay() {
        return this.addressWay;
    }

    @Override
    public String getAddressNumber() {
        return this.addressNumber;
    }

    @Override
    public ASL getAsl() {
        return this.asl;
    }
    
}
