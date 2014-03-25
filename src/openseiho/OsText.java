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
public class OsText extends javax.swing.JTextField {

    public OsText() {
        super();
    }
    public OsText(String string) {
        super.setText(string);
    }
    
    /**
     *
     * @param str
     */
    @Override
    public void setText(String str) {
        str = str.replaceAll("''*", "'");
        super.setText(str);
    }
    /**
     * SQLインジェクション対策をここで行う
     * @return
     */
    @Override
    public String getText() {
        String wk = super.getText();
        
        wk = wk.replaceAll("''*", "'");
        wk = wk.replaceAll("'", "''");
        wk = wk.replaceAll("\\\\", "");
        return wk;
    }
}
