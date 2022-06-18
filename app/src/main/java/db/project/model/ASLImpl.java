package db.project.model;

public class ASLImpl implements ASL {

    private final Integer code;
    private final String name;
    private final String city;
    private final String way;
    private final String number;

    public ASLImpl(final Integer code, final String name, final String city, final String way, final String number) {
        super();
        this.code = code;
        this.name = name;
        this.city = city;
        this.way = way;
        this.number = number;
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
    public String getCity() {
        return this.city;
    }

    @Override
    public String getWay() {
        return this.way;
    }

    @Override
    public String getNumber() {
        return this.number;
    }

    @Override
    public String getAddress() {
        return this.way + " " + this.number + ", " + this.city;
    }
    
    

}
