package ServerSide.Databases.DataTables;

import java.util.Date;

/**
 * @author F0urth
 */

public class Cena {

    private Integer idCena;
    private Date data;
    private Double price;
    private Integer idProduktu;


    public Integer getIdCena() {
        return this.idCena;
    }

    public Date getData() {
        return this.data;
    }

    public Double getPrice() {
        return this.price;
    }

    public Integer getIdProduktu() {
        return this.idProduktu;
    }


    public void setIdCena(Integer idCena) {
        this.idCena = idCena;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public void setIdProduktu(Integer idProduktu) {
        this.idProduktu = idProduktu;
    }


    @Override
    public String toString() {
        return "Cena{" +
            "idCena=" + idCena +
            ", data=" + data +
            ", price=" + price +
            ", idProduktu=" + idProduktu +
            '}';
    }
}
