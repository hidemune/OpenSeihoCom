/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package openseiho;

/**
 *
 * @author TANAKA_Hidemune
 */
public class OsTextNum extends javax.swing.JTextField{
    public OsTextNum() {
        super.setHorizontalAlignment(RIGHT);
    }
    
    /**
     * 整数にしてセットします。
     * @param str
     */
    @Override
    public void setText(String str) {
        try {
            str = "" + Long.parseLong(str);
        } catch (Exception e) {
            str = "0";
        }
        super.setText(str);
    }
    /**
     * 整数の文字列を返します。
     * @return
     */
    @Override
    public String getText(){
        String wk = super.getText();
        try {
            wk = "" + Long.parseLong(wk);
        } catch (Exception e) {
            wk = "0";
        }
        return wk;
    }
}
