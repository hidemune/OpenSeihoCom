/*
 * Copyright (C) 2014 hdm
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
 */

package openseiho;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import java.util.TreeSet;

/**
 *
 * @author hdm
 * DBアクセス機能（スーパークラス）
 * 
 */
public class dbAccess {
    String host = "localhost";
    String port = "5432";
    String dbname = "openseiho";
    String rolename = "postgres";
    String password = "xxxxxxxx";
    String url = "jdbc:postgresql://" + host + ":" + port + "/" + dbname;
    
    //共通部分
    public static boolean DebugMode = true;
    public static void logDebug(String str) {
        if (DebugMode) {
            System.out.println(str);
        }
    }
    //テーブル定義
    private String tableNameSup;
    private String[] tableUniqueSup;
    private String[] tablePrimarySup;
    private String[][] tableFieldSup ;
    
    public void setTableName(String str){
        tableNameSup = str;
        logDebug("テーブル名:" + tableNameSup);
    }
    public void setTableUnique(String[] str){
        tableUniqueSup = str;
        logDebug("Uniq:" + tableUniqueSup);
    }
    public void setTablePrimary(String[] str){
        tablePrimarySup = str;
        logDebug("Primary:" + tablePrimarySup);
    }
    public void setTableField(String[][] str){
        tableFieldSup = str;
        logDebug("Field:" + tableFieldSup);
    }
    
    public String getTableName(){
        return tableNameSup;
    }

    //Edit by Sheet
    public void editTable(dbAccess dbA, String where) {
        dbSheetFrame frm = new dbSheetFrame(dbA);
        frm.setVisible(true);
        frm.editTable(where);
        //テーブルのレザルトセットを取得
        //String[][] str = getResultSetTable(where, frm.getPage(), frm.pageCount);
        //結果を一覧にセット
        //frm.setResultSet(str, where);
    }
    
    //ExecSQL
    private String execSQLUpdate(String SQL){
        System.out.println("execSQLUpdate" + tableNameSup);
        Properties props = new Properties();
        props.setProperty("user", rolename);
        props.setProperty("password", password);
        Connection con = null;
        String msg = "";
        
        try {
            Class.forName("org.postgresql.Driver");
            
            con = DriverManager.getConnection(url, props);
            System.out.println("データベースに接続しました。");
            
            //自動コミットを無効にする
            con.setAutoCommit(false);
            
            //ステートメント作成
            Statement stmt = con.createStatement();
            
            //SQLの実行
            int rows = stmt.executeUpdate(SQL);
            System.out.println("Update:" + rows);
            
            //ステートメントのクローズ
            stmt.close();
            
            //コミットする
            con.commit();
        } catch (ClassNotFoundException e) {
            System.err.println("JDBCドライバが見つかりませんでした。");
            msg = msg + "JDBCドライバが見つかりませんでした。\n";
        } catch (SQLException e) {
            System.err.println("エラーコード　　: " + e.getSQLState());
            System.err.println("エラーメッセージ: " + e.getMessage());
            msg = msg + "エラーコード　　: " + e.getSQLState() + "\n";
            msg = msg + "エラーメッセージ: " + e.getMessage() + "\n";
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (con != null) {
                    con.close();
                    System.out.println("データベースとの接続を切断しました。");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        /* メッセージは画面側に任せる
        if (msg.equals("")) {
            msg = "正常に更新しました。";
        }
        */
        return msg;
    }
    
    /**
     * デフォルトソート版：強制的に、主キーでソートされます。
     * @param where
     * @param pageNo : 0から始まります
     * @param rowCount : ページあたりの行数です
     * @return 
     */
    public String[][] getResultSetTable(String where, int pageNo, int rowCount) {
        System.out.println("getResultSetTable" + tableNameSup);
        String[][] ret = new String[tableFieldSup.length][1]; //col,rowの順
        Properties props = new Properties();
        props.setProperty("user", rolename);
        props.setProperty("password", password);
        Connection con = null;
        
        try {
            Class.forName("org.postgresql.Driver");
            
            con = DriverManager.getConnection(url, props);
            System.out.println("データベースに接続しました。");
            
            //自動コミットを無効にする
            //con.setAutoCommit(false);
            
            //ステートメント作成
            Statement stmt = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
            
            //列数取得
//            ResultSet rs = stmt.executeQuery("SELECT COUNT(*) FROM " + tableNameSup);
 //           rs.next();
 //           int rows = rs.getInt("COUNT");
//            System.out.println(rows);
            //Order by
            StringBuilder sb = new StringBuilder();
            sb.append(" ORDER BY ");
            String sep = "";
            for (int i = 0; i < tablePrimarySup.length; i++) {
                sb.append(sep);
                sb.append(tablePrimarySup[i]);
                sep = ",";
            }
            //行数取得
//            String SQL = "SELECT COUNT(*) FROM " + tableNameSup + " " + where + sb.toString();
//            logDebug(SQL);
//            ResultSet rs = stmt.executeQuery(SQL);
//            rs.next();
//            int rows = rs.getInt("COUNT");
//            System.out.println("rows:" + rows);
//            rs.close();
            
            //SQLの実行
            String Offset = " LIMIT " + rowCount + " OFFSET " + (rowCount * pageNo);
            String SQL = "SELECT * FROM " + tableNameSup + " " + where + sb.toString() + " " + Offset;
            logDebug(SQL);
            ResultSet rs = stmt.executeQuery(SQL);
            
            /*
            rs.setFetchDirection(ResultSet.FETCH_FORWARD);
            rs.last();
            int rows = rs.getRow() - 1;
            rs.setFetchDirection(ResultSet.FETCH_REVERSE);
            rs.first();
            logDebug("rows:" + rows);
            
            int cols = tableFieldSup.length;
            logDebug("cols:" + cols);
            //配列の枠を作成
            ret = new String[cols][rows + 1]; //タイトル分１行多い
            */
            
            ResultSetMetaData rsmd= rs.getMetaData();
            List listRs = new ArrayList();
            //配列の枠を作成
            String[] wk = new String[rsmd.getColumnCount()];
            for (int i = 1; i <= rsmd.getColumnCount(); i++) {      //列の数は１から
                logDebug("取得RSカラム名：" + rsmd.getColumnName(i));
                wk[i - 1] = rsmd.getColumnName(i);
            }
            listRs.add(wk);
            
            //Data
            int idx = 0;
            while (rs.next()) {
                wk = new String[rsmd.getColumnCount()];
                for (int j = 0; j < rsmd.getColumnCount(); j++) {
                    wk[j] = rs.getString(j + 1);
                }
                listRs.add(wk);
            }
            
            //ArrayListから２次元配列を作成
            ret = (String[][])listRs.toArray(new String[0][0]);
            printRS(ret);
            
            rs.close();
            
            //ステートメントのクローズ
            stmt.close();
            
            //コミットする
            //con.commit();
        } catch (ClassNotFoundException e) {
            System.err.println("JDBCドライバが見つかりませんでした。");
        } catch (SQLException e) {
            System.err.println("エラーコード　　: " + e.getSQLState());
            System.err.println("エラーメッセージ: " + e.getMessage());
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (con != null) {
                    con.close();
                    System.out.println("データベースとの接続を切断しました。");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        //ret = sortArray(ret);
        return ret;
    }
    public int getResultSetTableCount(String where) {
        String[][] ret = new String[tableFieldSup.length][1]; //col,rowの順
        Properties props = new Properties();
        props.setProperty("user", rolename);
        props.setProperty("password", password);
        Connection con = null;
        int rows = 0;
        
        try {
            Class.forName("org.postgresql.Driver");
            
            con = DriverManager.getConnection(url, props);
            System.out.println("データベースに接続しました。");
            
            //自動コミットを無効にする
            //con.setAutoCommit(false);
            
            //ステートメント作成
            Statement stmt = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
            
            //列数取得
            ResultSet rs = stmt.executeQuery("SELECT COUNT(*) FROM " + tableNameSup + " " + where);
            rs.next();
            rows = rs.getInt("COUNT");
            System.out.println(rows);
            
            rs.close();
            
            //ステートメントのクローズ
            stmt.close();
            
            //コミットする
            //con.commit();
        } catch (ClassNotFoundException e) {
            System.err.println("JDBCドライバが見つかりませんでした。");
        } catch (SQLException e) {
            System.err.println("エラーコード　　: " + e.getSQLState());
            System.err.println("エラーメッセージ: " + e.getMessage());
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (con != null) {
                    con.close();
                    System.out.println("データベースとの接続を切断しました。");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        //ret = sortArray(ret);
        return rows;
    }
    
    /**
     * レザルトセットを標準出力に
     * 
     */
    public static void printRS(String[][] rs) {
        for (int i = 0; i < rs.length; i++) {
            StringBuilder sb = new StringBuilder();
            for (int j = 0; j < rs[i].length; j++) {
                sb.append(rs[i][j]);
                sb.append("\t");
            }
            //sb.append("\n");
            System.out.println(sb.toString());
        }
    }
    
    /**
     * ２次元配列をソートして返します
     * 　:Resultset用なので、最初の一行はカラム名とみなし、ソートしません。
     * @param src
     * @return 
     */ /*
    private String[][] sortArray(String[][] src) {
        int cols = src.length;
        int rows = src[0].length;
        String[][] desc = new String[cols][rows];
        
        DecimalFormat exFormat10 = new DecimalFormat("0000000000");
        TreeSet<String> arrayKeys = new TreeSet<String>();
        //１行目はカラム名：
        arrayKeys.add(exFormat10.format(0));            //強制的に０を指定
        //Sort
        //Integer[] idx = new Integer[rows];
        for (int i = 1; i < rows; i++) {                //２行目から始まっていることに注意
            StringBuilder key = new StringBuilder();
            for (int j = 0; j < cols; j++) {
                //ソートキー作成
                try {
                    long wk = Long.parseLong(src[j][i]);
                    key.append(exFormat10.format(wk));
                } catch (Exception e) {
                    key.append(src[j][i]);
                }
            }
            //最後の１０桁はインデックス
            key.append(exFormat10.format(i));
            arrayKeys.add(key.toString());
        }
        //新しい配列に紐付け
        Iterator<String> itr = arrayKeys.iterator();
        for (int i = 0; i < rows; i++) {
            String key = itr.next();
            int idx = 0;
            try {
                idx = Integer.parseInt(key.substring(key.length() - 10));
            } catch (Exception e) {
                e.printStackTrace();
            }
            for (int j = 0; j < cols; j++) {
                desc[j][i] = src[j][idx];
            }
        }
        
        return desc;
    }
// */
    //CREATE TABLE
    public void createTable(){
        logDebug("Create/テーブル名:" + tableNameSup);
        StringBuilder sb = new StringBuilder();
        sb.append("CREATE TABLE ");
        sb.append(tableNameSup);
        sb.append(" (");
        String separator = "";
        for (int i = 0; i < tableFieldSup.length; i++) {
            sb.append(separator);
            for (int j = 0; j < tableFieldSup[i].length; j++) {
                sb.append(tableFieldSup[i][j]);
                sb.append(" ");
                logDebug(tableFieldSup[i][j]);
            }
            separator = ",";
        }
        sb.append(");");
        
        sb.append("ALTER TABLE ");
        sb.append(tableNameSup);
        sb.append(" ADD PRIMARY KEY(");
        separator = "";
        for (int i = 0; i < tablePrimarySup.length; i++) {
            sb.append(separator);
            sb.append(tablePrimarySup[i]);
            sb.append(" ");
            separator = ",";
        }
        sb.append(");");
        
        sb.append("ALTER TABLE ");
        sb.append(tableNameSup);
        sb.append(" ADD UNIQUE (");
        separator = "";
        for (int i = 0; i < tableUniqueSup.length; i++) {
            sb.append(separator);
            sb.append(tableUniqueSup[i]);
            sb.append(" ");
            separator = ",";
        }
        sb.append(");");
        
        logDebug(sb.toString());
        //DB Access
        execSQLUpdate(sb.toString());
    }
    //INSERT
    /**
    *   fieldは、2次元配列でフィールド名・値の順とする。
    *       id0     value
    *       id1     value
    *       text    value
    */
    public String insert(ArrayList field[]) {
        /*設計方針：
            ・名前との対応を必ず行い、列番号に頼らない
            　（パッケージのバージョンアップに伴い、データが壊れなければOK）
            ・エラー情報を画面で確認可能とする
        */
        String msg ="";
        
        //tableFieldSup[][] スーパークラスで管理するカラム名    チェック可能
        StringBuilder sb = new StringBuilder();
        sb.append("INSERT INTO ");
        sb.append(tableNameSup);
        sb.append("(");
        String sep = "";
        for (int i = 0; i < field[0].size(); i++) {
            sb.append(sep);
            sb.append(field[0].get(i));
            sep = ",";
        }
        sb.append(") VALUES (");
        sep = "";
        for (int i = 0; i < field[1].size(); i++) {
            sb.append(sep);
            sb.append("'");
            sb.append(field[2].get(i));
            sb.append("'");
            sep = ",";
        }
        
        sb.append(")");
        
        //Exec
        msg = execSQLUpdate(sb.toString());
        
        return msg;
    }
    
    //UPDATE
    /**
    *   fieldは、2次元配列でフィールド名・値の順とする。
    *       id0     value
    *       id1     value
    *       text    value
    */
    public String update(ArrayList field[]) {
        /*設計方針：
            ・名前との対応を必ず行い、列番号に頼らない
            　（パッケージのバージョンアップに伴い、データが壊れなければOK）
            ・エラー情報を画面で確認可能とする
        */
        String msg ="";
        
        //tableFieldSup[][] スーパークラスで管理するカラム名    チェック可能
        StringBuilder sb = new StringBuilder();
        sb.append("UPDATE ");
        sb.append(tableNameSup);
        sb.append(" SET ");
        String sep = "";
        for (int i = 0; i < field[0].size(); i++) {
            sb.append(sep);
            sb.append(field[0].get(i));
            sb.append(" = ");
            sb.append("'");
            sb.append(field[2].get(i));
            sb.append("'");
            sep = ",";
        }
        
        sb.append(" WHERE ");
        sep = "";
        for (int i = 0; i < tableUniqueSup.length; i++) {
            sb.append(sep);
            sb.append(tableUniqueSup[i]);
            sb.append(" = ");
            //キーの値を取得
            for (int j = 0; j < field[0].size(); j++) {
                if (field[0].get(i).equals(tableUniqueSup[i])) {
                    //同じの！
                    sb.append("'");
                    sb.append(field[1].get(i));
                    sb.append("'");
                    
                    sep = " AND ";
                    break;
                }
            }
        }

        
        //Exec
        msg = execSQLUpdate(sb.toString());
        
        return msg;
    }
    
    //DELETE
    /**
    *   fieldは、2次元配列でフィールド名・値の順とする。
    *       id0     value
    *       id1     value
    *       text    value
    *   UNIQ のみをWHERE句に設定
    */
    public String delete(ArrayList field[]) {
        String msg = "";
        
        StringBuilder sb = new StringBuilder();
        sb.append("DELETE FROM ");
        sb.append(tableNameSup);
        sb.append(" WHERE ");
        String sep = "";
        for (int i = 0; i < tableUniqueSup.length; i++) {
            sb.append(sep);
            sb.append(tableUniqueSup[i]);
            sb.append(" = ");
            //キーの値を取得
            for (int j = 0; j < field[0].size(); j++) {
                if (field[0].get(i).equals(tableUniqueSup[i])) {
                    //同じの！
                    sb.append("'");
                    sb.append(field[1].get(i));
                    sb.append("'");
                    
                    sep = " AND ";
                    break;
                }
            }
        }
        
        logDebug(sb.toString());
        msg = execSQLUpdate(sb.toString());
        
        return msg;
    }
}
