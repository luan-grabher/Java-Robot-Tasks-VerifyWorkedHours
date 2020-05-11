package Model.Entitiy;

public class HoraBanco {
    private String nome = "";
    private String email = "";
    private String horasTrabalhadas = "00:00:00";
    private String ultimoHorarioOnline = "00:00:00";
    private String dia = "2019-01-01";

    public HoraBanco(String[] pesquisaBanco) {
        try {
            nome = pesquisaBanco[0];
            email = pesquisaBanco[1];
            horasTrabalhadas=  pesquisaBanco[2];
            ultimoHorarioOnline = pesquisaBanco[3];
            dia =  pesquisaBanco[4];
        } catch (Exception e) {
        }
    }

    public String getDia() {
        return dia;
    }

    public void setDia(String dia) {
        this.dia = dia;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getHorasTrabalhadas() {
        return horasTrabalhadas;
    }

    public void setHorasTrabalhadas(String horasTrabalhadas) {
        this.horasTrabalhadas = horasTrabalhadas;
    }

    public String getUltimoHorarioOnline() {
        return ultimoHorarioOnline;
    }

    public void setUltimoHorarioOnline(String ultimoHorarioOnline) {
        this.ultimoHorarioOnline = ultimoHorarioOnline;
    }
    
    
}
