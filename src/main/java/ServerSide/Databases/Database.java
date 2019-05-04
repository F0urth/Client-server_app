package ServerSide.Databases;

import Absolute.MergedData;
import ServerSide.Databases.DataTables.Cena;
import ServerSide.Databases.DataTables.Produkt;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;

import java.util.ArrayList;
import java.util.List;

public final
    class Database {


    private SessionFactory sessionFactory;

    public static Database getInstance() {
        return new Database();
    }
    private Database() {
        this.sessionFactory = new Configuration().configure().buildSessionFactory();
    }


    public List<MergedData> getMergetData() {
        var prices = getCenaData();
        var produkts = getProduktData();
        var res = new ArrayList<MergedData>();
        for (var price: prices) {
            res.add(
                new MergedData(price.getIdCena(),
                    price.getData(), price.getPrice(),
                    produkts.stream()
                        .filter(e -> e.getIdProdukt().equals(price.getIdProduktu()))
                        .map(Produkt::getNazwa).findAny().get()));
        }
        return res;
    }

    public List<Produkt> getProduktData() {
        var session = sessionFactory.openSession();
        return getData(session.createQuery("from Produkt", Produkt.class), session);
    }

    public List<Cena> getCenaData() {
        var session = sessionFactory.openSession();
        return getData(session.createQuery("from Cena", Cena.class), session);
    }

    private<T> List<T> getData(Query<T> query, Session session) {

        session.beginTransaction();
        var prices = query.list();
        session.getTransaction().commit();
        session.close();

        return prices;
    }
}
