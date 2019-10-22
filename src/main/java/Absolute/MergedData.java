package Absolute;

import java.io.Serializable;
import java.util.Date;

/**
 * @author F0urth
 */

public class MergedData implements Serializable {

    public static final long serialVersionUID = -1337L;

    private final Integer idCena;
    private final Date data;
    private final Double price;
    private final String name;


    public MergedData(Integer idCena, Date data, Double price, String name) {
        this.idCena = idCena;
        this.data = data;
        this.price = price;
        this.name = name;
    }

    public Integer getIdCena() {
        return idCena;
    }

    public Date getData() {
        return data;
    }

    public Double getPrice() {
        return price;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "MergedData{" +
            "idCena=" + idCena +
            ", data=" + data +
            ", price=" + price +
            ", name='" + name + '\'' +
            '}';
    }
}
