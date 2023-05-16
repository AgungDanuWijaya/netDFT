package main;

import java.util.ArrayList;

import com.DBProcess;

public class generage {

    public static void main(String[] args) {
        String server[] = {"jdbc:mysql://127.0.0.1:3306/SIAKAD_MIPA_I?allowPublicKeyRetrieval=true&useUnicode=true&useSSL=false&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC",
            "mabok_janda", "yut28092018DAM^"};

        DBProcess db = new DBProcess(server);
        //20221,20212,20211,20202
        int smt = 2019;
        int pemili = 135;
        double in = 0;
        for (int i = 0; i < 5; i++) {
            for (int j = 1; j < 3; j++) {
                pemili++;
                smt += i;
                in = in + 0.01;
                in*=Math.random();
                String smt_ = smt + "" + j;
                //KDPSTTRNLM,KELASTRNLM,THSMSTRNLM,NO_SISTEM
                String Q = "SELECT DISTINCT(CONCAT(KDPSTTRNLM,\"_\", KELASTRNLM,\"_\" ,THSMSTRNLM ,\"_\",KDKMKTRNLM,\"_\" ) ) "
                        + "FROM SIAKAD_MIPA_I.trnlm t where t.THSMSTRNLM ='" + smt_ + "'";
                ArrayList<String[]> data = db.getArrayData(Q);
                for (String[] strings : data) {
                    Q = "SELECT distinct(NIMHSTRNLM) FROM SIAKAD_MIPA_I.trnlm t where t.THSMSTRNLM ='" + smt_ + "' "
                            + "and CONCAT(KDPSTTRNLM,\"_\", KELASTRNLM,\"_\" ,THSMSTRNLM ,\"_\",KDKMKTRNLM,\"_\" ) ='" + strings[0] + "' and KDPSTTRNLM not like '%45201%'";

                    ArrayList<String[]> nim = db.getArrayData(Q);
                    for (String[] strings1 : nim) {

                        Q = "SELECT ID FROM LEON_MONEVAK.PERTANYAAN where ID_PEMILIK='" + pemili + "';";
                        ArrayList<String[]> dat = db.getArrayData(Q);
                        //
                        for (String[] strings2 : dat) {
                            Q = "replace INTO `LEON_MONEVAK`.`SURVEI` (`ID_PERTANYAAN`, `JAWABAN`, `TARGET`) VALUES ('" + strings2[0] + "', '" + (int) (Math.round(Math.random() * (4 - (3.33+in)) + (3.33+in))) + "', '" + strings[0] + "');\n"
                                    + "";
                            db.setData_1(Q);
                            System.out.println(Q);

                        }
                        Q = "replace INTO `LEON_MONEVAK`.`SUDAH_ISI` (`NIM`, `ID_PEMILIK`, `KEY`) VALUES ('" + strings1[0] + "', '" + pemili + "', '" + strings[0] + "');\n"
                                + "";
                        db.setData_1(Q);
                    }

                }
            }

        }
    }

}
