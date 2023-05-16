package main;

import java.util.ArrayList;

import com.DBProcess;

public class generage1 {

    public static void main(String[] args) {
        String server[] = {"jdbc:mysql://127.0.0.1:3306/SIAKAD_MIPA_I?allowPublicKeyRetrieval=true&useUnicode=true&useSSL=false&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC",
            "mabok_janda", "yut28092018DAM^"};

        DBProcess db = new DBProcess(server);
        //20221,20212,20211,20202
        int smt = 2019;
        int pemili = 134;
double in=0;
        for (int i = 0; i < 5; i++) {
            for (int j = 1; j < 3; j++) {
                in = in + 0.01;
                in*=Math.random();
                pemili++;
                smt += i;
                String smt_ = smt + "" + j;
                //KDPSTTRNLM,KELASTRNLM,THSMSTRNLM,NO_SISTEM
                String Q = "SELECT distinct(NIMHSTRNLM) FROM SIAKAD_MIPA_I.trnlm  where THSMSTRNLM='" + smt_ + "' ;";
              System.out.println(Q);
                ArrayList<String[]> data = db.getArrayData(Q);
                for (String[] strings : data) {
                    Q = "SELECT ID FROM LEON_MONEVAK.PERTANYAAN where ID_PEMILIK='" + pemili + "';";
                      System.out.println(Q);
                    ArrayList<String[]> dat = db.getArrayData(Q);
                    //
                    for (String[] strings2 : dat) {
                        Q = "INSERT INTO `LEON_MONEVAK`.`QUIZ` (`NIM`, `ID_PERTANYAAN`, `JAWABAN`) VALUES ('"+strings[0]+"', '"+strings2[0]+"', '"+(int) (Math.round(Math.random() * (4 - (3.31+in)))+(in+3.31))+"');";
                        db.setData_1(Q);
                        System.out.println(Q);

                    }
                }
            }

        }
    }

}
