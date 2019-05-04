package ServerSide.Databases;

import java.io.Serializable;
import java.util.Date;

public class MergedData implements Serializable {

    public static final long serialVersionUID = -1337L;

    private final Integer idCena;
    private final Date data;
    private final Double price;
    private final String nazwa;


    MergedData(Integer idCena, Date data, Double price, String nazwa) {
        this.idCena = idCena;
        this.data = data;
        this.price = price;
        this.nazwa = nazwa;
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

    public String getNazwa() {
        return nazwa;
    }

    @Override
    public String toString() {
        return "MergedData{" +
            "idCena=" + idCena +
            ", data=" + data +
            ", price=" + price +
            ", nazwa='" + nazwa + '\'' +
            '}';
    }
}
