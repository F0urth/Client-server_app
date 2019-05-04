package ServerSide.Databases.DataTables;

public
    class Produkt {

    private Integer idProdukt;
    private String nazwa;


    public Integer getIdProdukt() {
        return this.idProdukt;
    }

    public String getNazwa() {
        return this.nazwa;
    }

    public void setIdProdukt(Integer idProdukt) {
        this.idProdukt = idProdukt;
    }

    public void setNazwa(String nazwa) {
        this.nazwa = nazwa;
    }
}
