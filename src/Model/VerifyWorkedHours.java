package Model;

import Auxiliar.Valor;
import Model.Entitiy.HoraBanco;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import main.Gmail;
import sql.Banco;

public class VerifyWorkedHours {

    private static Gmail gmail = new Gmail("contabil.moresco@gmail.com", "q1W@e3R$");
    private static String mysqlPath = "//zac/robos/Tarefas/Arquivos/mysql.cfg";

    public static String run() {
        //Cria banco
        Banco mysql = new Banco(mysqlPath);
        if (mysql.testConnection()) {

            //Verifica hoje é domingo ou segunda
            Calendar hoje = Calendar.getInstance();
            int diaSemana = hoje.get(Calendar.DAY_OF_WEEK);
            if (diaSemana != Calendar.SUNDAY & diaSemana != Calendar.MONDAY) {
                Calendar ontem = Calendar.getInstance();
                ontem.add(Calendar.DAY_OF_MONTH, -1);

                ArrayList<String[]> horasMenosDe8Banco = Get_Horas_Menores_Que_8(new Valor(ontem).getSQLDate());

                List<HoraBanco> horas = new ArrayList();
                for (String[] horaMenosDe8Banco : horasMenosDe8Banco) {
                    horas.add(new HoraBanco(horaMenosDe8Banco));
                }

                //Envia Emails
                StringBuilder emailAdmin = new StringBuilder("Enviado email para:<br>");
                for (HoraBanco hora : horas) {
                    emailAdmin.append(hora.getNome()).append(":");
                    emailAdmin.append(
                            gmail.enviaZAC(
                                    hora.getEmail(),
                                    hora.getNome()
                                    + ", você fez "
                                    + hora.getHorasTrabalhadas()
                                    + " horas no zac ontem!",
                                     getMensagemEmail(hora)
                            )
                    );
                    emailAdmin.append("<br>");
                }

                return emailAdmin.toString();
            } else {
                return "Ontem foi sábado ou domingo, então não verifiquei as horas.<br>";
            }

        } else {
            return "Erro ao conectar ao banco de dados MySql!";
        }
    }

    private static String getMensagemEmail(HoraBanco horaBanco) {
        StringBuilder r = new StringBuilder();

        r.append(horaBanco.getNome()).append(", ontem você obteve menos de 8 horas totais referente ao ");
        r.append("tempo de suas tarefas.<br>");

        r.append("Você fez ").append(horaBanco.getHorasTrabalhadas()).append(" horas no dia ");
        r.append(horaBanco.getDia()).append("<br>");

        r.append("Você ficou online até ").append(horaBanco.getUltimoHorarioOnline()).append(" horas.<br>");
        r.append("Você pode solicitar o ajuste destas horas ");
        r.append("<a href='https://zac/SOLICITAR_HORAS'>CLICANDO AQUI</a>.");
        r.append(" (Não se esqueça de avisar seu gestor para que ele acesse e aprove.)<br>");

        return r.toString();
    }

    private static ArrayList<String[]> Get_Horas_Menores_Que_8(String data) {
        Banco mysql = new Banco(mysqlPath);
        String select = "SELECT \n"
                + "    m.nome,\n"
                + "    e.email_questor,\n"
                + "    SEC_TO_TIME(SUM(tempo)) AS tempinho,\n"
                + "    TIME(hl.final_logon) AS online_ate,\n"
                + "    dia \n"
                + "FROM\n"
                + "    relatorios r\n"
                + "        INNER JOIN\n"
                + "    membros m ON m.id = membro_id\n"
                + "        INNER JOIN\n"
                + "    email_questor e ON e.membro_id = m.id\n"
                + "        INNER JOIN\n"
                + "    horariologons hl ON hl.membro_id = m.id\n"
                + "        AND hl.dia_logon = dia\n"
                + "WHERE\n"
                + "    dia = '" + data + "' AND m.id <> 26\n"
                + "GROUP BY m.id\n"
                + "HAVING (SUM(tempo) < 28800)";

        return mysql.select(select);
    }
}
