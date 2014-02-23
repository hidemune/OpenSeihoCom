/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package openseiho;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import javax.swing.DefaultListModel;
import javax.swing.JOptionPane;

/**
 *
 * @author hdm
 */
public class JyusyoJDialog extends javax.swing.JDialog {
private DefaultListModel lstM;
private JyusyoPanel jyusyoPanel;
//private ConmoboxModel cmbLst;

    /**
     * Creates new form JyusyoJDialog
     * @deprecated 
     */
    public JyusyoJDialog(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
    }
    /**
     * こちらを使って下さい。
     * @param parent
     * @param modal
     * @param parentPanel 
     */
    public JyusyoJDialog(java.awt.Frame parent, boolean modal, JyusyoPanel parentPanel) {
        super(parent, modal);
        initComponents();
        
        jyusyoPanel = parentPanel;
        
        //画面を中心に表示
        java.awt.GraphicsEnvironment env = java.awt.GraphicsEnvironment.getLocalGraphicsEnvironment();
        // 変数desktopBoundsにデスクトップ領域を表すRectangleが代入される
        java.awt.Rectangle desktopBounds = env.getMaximumWindowBounds();
        java.awt.Rectangle thisBounds = this.getBounds();
        int x = desktopBounds.width / 2 - thisBounds.width / 2;
        int y = desktopBounds.height / 2 - thisBounds.height / 2;
        this.setBounds(x, y, thisBounds.width, thisBounds.height);
        
        lstM = new DefaultListModel();
        listJyusyo.setModel(lstM);
        comboTodoufuken.removeAllItems();
        //IME抑止
        textZipCode.enableInputMethods(false);
    }

    public void setParentJyusyoPanel(JyusyoPanel parentPanel) {
        jyusyoPanel = parentPanel;
    }
    public void setZipCode(String zipcode) {
        textZipCode.setText(zipcode);
        searchZipCode(zipcode);
        this.setVisible(true);
        //モーダルの場合ここで止まる
        
    }
    public void setJyusyo1(String jyusyo1) {
        textJyusyo1.setText(jyusyo1);
        searchJyusyo1(jyusyo1);
        this.setVisible(true);
        //モーダルの場合ここで止まる
    }
    private void searchJyusyo1(String jyusyo1) {
        comboTodoufuken.removeAllItems();
        comboTodoufuken.addItem("絞り込み");
        comboTodoufuken.setSelectedIndex(0);
        lstM.clear();
        //CSVファイルの読み込み
        File csv = null;
        try {
            csv = new File("KEN_ALL.CSV");
            BufferedReader br;      // close 忘れずに！！！
            br = new BufferedReader(new InputStreamReader(new FileInputStream(csv), "MS932"));
            String line = "";
            boolean find = false;
            while ((line = br.readLine()) != null) {
                String[] strArr = line.split(",");
                //System.err.println(strArr[2]);
                String zipcode = strArr[2].replaceAll("\"", "");
                //System.err.println(zipcode);
                if (line.indexOf(jyusyo1) >= 0) {
                    //                    textJyusyo1.setText(strArr[6].replaceAll("\"", "") + strArr[7].replaceAll("\"", ""));
                    //                    textJyusyo2.setText(strArr[8].replaceAll("\"", ""));
                    lstM.addElement(line);
                    //都道府県絞り込み用コンボボックス設定
                    String ken = strArr[6].replaceAll("\"", "");
                    boolean todo = false;
                    for (int i = 0; i < comboTodoufuken.getItemCount(); i++) {
                        
                        if (comboTodoufuken.getItemAt(i).equals(ken)) {
                            todo = true;
                        }
                    }
                    if (!todo) {
                        comboTodoufuken.addItem(ken);
                    }
                    find = true;
                }
            }
            br.close();
            if (!find) {
                JOptionPane.showMessageDialog(this, "指定された住所が郵便番号CSVに見つかりません。");
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
    private void searchZipCode(String zipcodeOrg) {
        lstM.clear();
        
        //郵便番号検索
        String serchZip = zipcodeOrg.replaceAll("-", "");
        
        int serchL = serchZip.length();
        if ((serchL == 3) || (serchL == 5) || (serchL == 7)) {
            //３桁・５,7桁指定
            //CSVファイルの読み込み
            File csv = null;
            try {
                csv = new File("KEN_ALL.CSV");
                BufferedReader br;      // close 忘れずに！！！
                br = new BufferedReader(new InputStreamReader(new FileInputStream(csv), "MS932"));
                String line = "";
                boolean find = false;
                while ((line = br.readLine()) != null) {
                    String[] strArr = line.split(",");
                    String findZip = strArr[2].replaceAll("\"", ""); //.substring(0, serchL);
                    //System.out.println(findZip);
//                    if (serchZip.equals(findZip)) {
                    if (findZip.startsWith(serchZip)) {
                        lstM.addElement(line);
                        find = true;
                    }
                }
                br.close();
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

            return;
        }

        JOptionPane.showMessageDialog(this, "郵便番号の桁数をご確認下さい。\n３桁、５桁、７桁が指定できます。");
        return;
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        textZipCode = new javax.swing.JTextField();
        textJyusyo1 = new javax.swing.JTextField();
        comboTodoufuken = new javax.swing.JComboBox();
        jButton2 = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        listJyusyo = new javax.swing.JList();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        textZipCode.setText(org.openide.util.NbBundle.getMessage(JyusyoJDialog.class, "JyusyoJDialog.textZipCode.text")); // NOI18N
        textZipCode.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                textZipCodeActionPerformed(evt);
            }
        });

        textJyusyo1.setText(org.openide.util.NbBundle.getMessage(JyusyoJDialog.class, "JyusyoJDialog.textJyusyo1.text")); // NOI18N
        textJyusyo1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                textJyusyo1ActionPerformed(evt);
            }
        });

        comboTodoufuken.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        comboTodoufuken.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                comboTodoufukenActionPerformed(evt);
            }
        });

        org.openide.awt.Mnemonics.setLocalizedText(jButton2, org.openide.util.NbBundle.getMessage(JyusyoJDialog.class, "JyusyoJDialog.jButton2.text")); // NOI18N
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        org.openide.awt.Mnemonics.setLocalizedText(jLabel3, org.openide.util.NbBundle.getMessage(JyusyoJDialog.class, "JyusyoJDialog.jLabel3.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(jLabel4, org.openide.util.NbBundle.getMessage(JyusyoJDialog.class, "JyusyoJDialog.jLabel4.text")); // NOI18N

        listJyusyo.setModel(new javax.swing.AbstractListModel() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public Object getElementAt(int i) { return strings[i]; }
        });
        jScrollPane2.setViewportView(listJyusyo);

        org.jdesktop.layout.GroupLayout layout = new org.jdesktop.layout.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(layout.createSequentialGroup()
                .addContainerGap()
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(jScrollPane2)
                    .add(layout.createSequentialGroup()
                        .add(jLabel3)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(textZipCode, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 80, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(jLabel4)
                        .add(2, 2, 2)
                        .add(textJyusyo1, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 242, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(comboTodoufuken, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 105, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED, 363, Short.MAX_VALUE)
                        .add(jButton2)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(layout.createSequentialGroup()
                .addContainerGap()
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(textZipCode, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(textJyusyo1, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(comboTodoufuken, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(jButton2)
                    .add(jLabel3)
                    .add(jLabel4))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jScrollPane2, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 247, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void textZipCodeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_textZipCodeActionPerformed
        // TODO add your handling code here:
        
        searchZipCode(textZipCode.getText());
    }//GEN-LAST:event_textZipCodeActionPerformed

    private void textJyusyo1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_textJyusyo1ActionPerformed
        // TODO add your handling code here:
        
        String searchStr = textJyusyo1.getText();
        searchJyusyo1(searchStr);
    }//GEN-LAST:event_textJyusyo1ActionPerformed

    private void comboTodoufukenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comboTodoufukenActionPerformed
        // TODO add your handling code here:
        //都道府県絞り込み
        if (comboTodoufuken.getSelectedIndex() <= 0) {
            return;
        }
        String searchTodo = (String) comboTodoufuken.getSelectedItem();
        int max = lstM.getSize();
        for (int i = max - 1; i >= 0; i--) {
            String wk = (String) lstM.getElementAt(i);
            if (wk.indexOf(searchTodo) >= 0) {
                //該当
            } else {
                lstM.removeElementAt(i);
            }
        }
        //都道府県コンボも再設定しとく
        comboTodoufuken.removeAllItems();
        comboTodoufuken.addItem(searchTodo);
    }//GEN-LAST:event_comboTodoufukenActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
        int idx = listJyusyo.getSelectedIndex();
        if (idx < 0) {
             JOptionPane.showMessageDialog(this, "選択されていません。");
            return;
        }
        String line = (String) listJyusyo.getSelectedValue();
        System.err.println(line);
        String[] strArr = line.split(",");
        //System.err.println(strArr[2]);
        String zipcode = strArr[2].replaceAll("\"", "");
        zipcode = zipcode.substring(0, 3) + "-" + zipcode.substring(3);
        String jyusyo1 = strArr[6].replaceAll("\"", "") + strArr[7].replaceAll("\"", "") + strArr[8].replaceAll("\"", "");
        
        System.err.println(zipcode);
        
        jyusyoPanel.setAddress(zipcode, jyusyo1);
        //閉じて終了
        this.setVisible(false);
    }//GEN-LAST:event_jButton2ActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(JyusyoJDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(JyusyoJDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(JyusyoJDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(JyusyoJDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                JyusyoJDialog dialog = new JyusyoJDialog(new javax.swing.JFrame(), true);
                dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                    @Override
                    public void windowClosing(java.awt.event.WindowEvent e) {
                        System.exit(0);
                    }
                });
                dialog.setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox comboTodoufuken;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JList listJyusyo;
    private javax.swing.JTextField textJyusyo1;
    private javax.swing.JTextField textZipCode;
    // End of variables declaration//GEN-END:variables
}
