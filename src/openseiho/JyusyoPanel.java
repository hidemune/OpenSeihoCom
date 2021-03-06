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

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import javax.swing.JOptionPane;

/**
 *
 * @author hdm
 */
public class JyusyoPanel extends javax.swing.JPanel {
java.awt.Frame frm = null;

    /**
     * Creates new form JyusyoPanel
     */
    public JyusyoPanel() {
        initComponents();
        //IME抑止
        textZipCode.enableInputMethods(false);
    }

    public String getYubinNo(){
        return textZipCode.getText();
    }
    public String getJyusyo1(){
        return textJyusyo1.getText();
    }
    public String getJyusyo2(){
        return textJyusyo2.getText();
    }
    public void setAddress(String zipcode, String Jyusyo1) {
        textZipCode.setText(zipcode);
        textJyusyo1.setText(Jyusyo1);
    }
    public void setAddress(String zipcode, String Jyusyo1, String Jyusyo2) {
        textZipCode.setText(zipcode);
        textJyusyo1.setText(Jyusyo1);
        textJyusyo2.setText(Jyusyo2);
    }
    public void setMainFrame(java.awt.Frame parent) {
        frm = parent;
    }
    private void findZip() {
        //郵便番号検索
        //画面の郵便番号を取得
        String searchZip = textZipCode.getText().replaceAll("-", "");
        
        int serchL = searchZip.length();
        if ((serchL == 3) || (serchL == 5)) {
            //３桁・５桁指定
            //一覧呼び出し
            JyusyoJDialog digJyusyo = new JyusyoJDialog(frm, true, this);
            digJyusyo.setZipCode(searchZip);
            //モーダルの場合ここで止まる
// textZipCodeActionPerformed(evt);　これをやると閉じただけでも上書きして危険
            return;
        }
        
        if (serchL != 7) {
            JOptionPane.showMessageDialog(this, "郵便番号の桁数をご確認下さい。\n指定できるのは、3,5,7桁のみです。");
            return;
        }
        
        //CSVファイルの読み込み
        File csv = null;
        try {
            csv = new File("KEN_ALL.CSV");
            BufferedReader br;
            br = new BufferedReader(new InputStreamReader(new FileInputStream(csv), "MS932"));
            String line = "";
            boolean find = false;
            while ((line = br.readLine()) != null) {
                String[] strArr = line.split(",");
                String zipcode = strArr[2].replaceAll("\"", "");
                if (searchZip.equals(zipcode)) {
                    textZipCode.setText(searchZip.substring(0, 3) + "-" + searchZip.substring(3));
                    textJyusyo1.setText(strArr[6].replaceAll("\"", "") + strArr[7].replaceAll("\"", "") + strArr[8].replaceAll("\"", ""));
                    textJyusyo2.setText("");
                    find = true;
                    break;
                }
            }
            if (!find) {
                JOptionPane.showMessageDialog(this, "指定された郵便番号が見つかりません。");
            }
        } catch (FileNotFoundException e) {
            // Fileオブジェクト生成時の例外捕捉
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "郵便番号CSVファイルがありません。\n" + csv.getAbsolutePath());
        } catch (IOException e) {
            // BufferedReaderオブジェクトのクローズ時の例外捕捉
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "エラーが発生しました");
        }
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jFrame1 = new javax.swing.JFrame();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        textZipCode = new openseiho.OsText();
        textJyusyo1 = new openseiho.OsText();
        textJyusyo2 = new openseiho.OsText();

        org.jdesktop.layout.GroupLayout jFrame1Layout = new org.jdesktop.layout.GroupLayout(jFrame1.getContentPane());
        jFrame1.getContentPane().setLayout(jFrame1Layout);
        jFrame1Layout.setHorizontalGroup(
            jFrame1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(0, 400, Short.MAX_VALUE)
        );
        jFrame1Layout.setVerticalGroup(
            jFrame1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(0, 300, Short.MAX_VALUE)
        );

        setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel4.setFont(new java.awt.Font("Dialog", 0, 12)); // NOI18N
        jLabel4.setText("〒");

        jLabel5.setFont(new java.awt.Font("Dialog", 0, 12)); // NOI18N
        jLabel5.setText("住所１");

        jLabel6.setFont(new java.awt.Font("Dialog", 0, 12)); // NOI18N
        jLabel6.setText("住所２");

        textZipCode.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                textZipCodeActionPerformed(evt);
            }
        });

        textJyusyo1.setMode(2);
        textJyusyo1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                textJyusyo1ActionPerformed(evt);
            }
        });

        textJyusyo2.setMode(2);

        org.jdesktop.layout.GroupLayout layout = new org.jdesktop.layout.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(layout.createSequentialGroup()
                .add(jLabel4)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(textZipCode, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 86, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.UNRELATED)
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(jLabel5)
                    .add(jLabel6))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING, false)
                    .add(textJyusyo2, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 490, Short.MAX_VALUE)
                    .add(textJyusyo1, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .add(0, 18, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(layout.createSequentialGroup()
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(jLabel4)
                    .add(jLabel5)
                    .add(textZipCode, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(textJyusyo1, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(jLabel6)
                    .add(textJyusyo2, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void textZipCodeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_textZipCodeActionPerformed
        findZip();
    }//GEN-LAST:event_textZipCodeActionPerformed

    private void textJyusyo1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_textJyusyo1ActionPerformed
        JyusyoJDialog digJyusyo = new JyusyoJDialog(frm, true, this);
        digJyusyo.setJyusyo1(textJyusyo1.getText());
        //モーダルの場合ここで止まる
        //textZipCodeActionPerformed(evt); これをやると閉じただけでも上書きして危険
    }//GEN-LAST:event_textJyusyo1ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JFrame jFrame1;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private openseiho.OsText textJyusyo1;
    private openseiho.OsText textJyusyo2;
    private openseiho.OsText textZipCode;
    // End of variables declaration//GEN-END:variables
}
