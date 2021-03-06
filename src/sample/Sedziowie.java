package sample;


public class Sedziowie {

  private String idSedziego;
  private String imie;
  private String nazwisko;
  private Integer wiek;
  private String pochodzenie;

  public Sedziowie(String id, String imie, String nazwisko, Integer wiek, String kraj) {
    idSedziego = id;
    this.imie = imie;
    this.nazwisko = nazwisko;
    this.wiek = wiek;
    pochodzenie = kraj;
  }


  public String getIdSedziego() {
    return idSedziego;
  }

  public void setIdSedziego(String idSedziego) {
    this.idSedziego = idSedziego;
  }


  public String getImie() {
    return imie;
  }

  public void setImie(String imie) {
    this.imie = imie;
  }


  public String getNazwisko() {
    return nazwisko;
  }

  public void setNazwisko(String nazwisko) {
    this.nazwisko = nazwisko;
  }


  public Integer getWiek() {
    return wiek;
  }

  public void setWiek(Integer wiek) {
    this.wiek = wiek;
  }


  public String getPochodzenie() {
    return pochodzenie;
  }

  public void setPochodzenie(String pochodzenie) {
    this.pochodzenie = pochodzenie;
  }

}
