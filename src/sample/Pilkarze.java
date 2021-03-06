package sample;


import java.sql.Date;

public class Pilkarze {

  private String idPilkarza;
  private String imie;
  private String nazwisko;
  private java.sql.Date dataUrodzenia;
  private String pozycja;
  private Double wartoscRynkowa;
  private Double pensja;
  private String nazwaKlubu;

  public Pilkarze(String id_pilkarza, String imie, String nazwisko, Date data_urodzenia, String pozycja, Double wartosc_rynkowa, Double pensja, String nazwa_klubu) {
      idPilkarza = id_pilkarza;
      this.imie = imie;
      this.nazwisko = nazwisko;
      dataUrodzenia = data_urodzenia;
      this.pozycja = pozycja;
      wartoscRynkowa = wartosc_rynkowa;
      this.pensja = pensja;
      nazwaKlubu = nazwa_klubu;
  }


  public String getIdPilkarza() {
    return idPilkarza;
  }

  public void setIdPilkarza(String idPilkarza) {
    this.idPilkarza = idPilkarza;
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


  public java.sql.Date getDataUrodzenia() {
    return dataUrodzenia;
  }

  public void setDataUrodzenia(java.sql.Date dataUrodzenia) {
    this.dataUrodzenia = dataUrodzenia;
  }


  public String getPozycja() {
    return pozycja;
  }

  public void setPozycja(String pozycja) {
    this.pozycja = pozycja;
  }


  public Double getWartoscRynkowa() {
    return wartoscRynkowa;
  }

  public void setWartoscRynkowa(Double wartoscRynkowa) {
    this.wartoscRynkowa = wartoscRynkowa;
  }


  public Double getPensja() {
    return pensja;
  }

  public void setPensja(Double pensja) {
    this.pensja = pensja;
  }


  public String getNazwaKlubu() {
    return nazwaKlubu;
  }

  public void setNazwaKlubu(String nazwaKlubu) {
    this.nazwaKlubu = nazwaKlubu;
  }

}
