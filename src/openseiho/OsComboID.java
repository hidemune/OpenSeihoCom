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

import java.awt.Dimension;
import java.util.ArrayList;
import javax.swing.JPanel;

/**
 *
 * @author hdm
 * 
 * 以下のコマンドをコンボボックスに入力し、Enterを押せば裏技が使えます。
 * createtable! ：テーブルを作成します。
 * edit!        ：テーブルを編集します。（指定IDのみ）
 * editall!     ：テーブル全体を編集します。
 * read!        ：テーブルを再読み込みします。
 * 
 */
public class OsComboID extends JPanel {
private dbIdText dbIT;
private Integer ID0 = 0;
private String[][] rs;
private int defaultID1 = 0;
public static ArrayList<String[][]> arrRs = new ArrayList<String[][]>();        //同じIDで何度も読むのを防ぐため、機能追加

    //共通部分
    public static boolean DebugMode = false;
    public static void logDebug(String str) {
        if (DebugMode) {
            System.out.println(str);
        }
    }
    
    /**
     * Creates new form comboID
     */
    public OsComboID() {
        initComponents();
        //dbIT.DebugMode = true;
        dbIT = new dbIdText();
        //setComboWidth(100);
        //日本語入力はしない前提で。    Linuxでうまく動かない
        enableInputMethods(false);
        jComboBox1.enableInputMethods(false);
    }

    //Focus
    //public FocusTraversalPolicy getFocusTraversalPolicy() {
    //    return getFocusTraversalPolicy();
    //}
    public void setDefaultID1(String id1) {
        try {
            defaultID1 = Integer.parseInt(id1);
        }catch (Exception e) {
            defaultID1 = 0;
        }
    }
    public void setDefaultID1(int id1) {
        defaultID1 = id1;
    }
    public void setEnabled(boolean flg) {
        jComboBox1.setEnabled(flg);
    }
    public String getSelectedItem() {
        return (String) jComboBox1.getSelectedItem();
    }
    public int getValue() {
        return jComboBox1.getSelectedIndex();
    }
    public String getID1() {
        return rs[1][jComboBox1.getSelectedIndex()];
    }
    public void setID1(String id1) {
        try {
            int wk = Integer.parseInt(id1);
            setID1(wk);
        } catch (Exception e) {
            if (jComboBox1.getItemCount() > 0) {
                jComboBox1.setSelectedIndex(0);
            }
            return;
        }
    }
    public void setID1(Integer id1) {
        for (int i = 1; i < rs[1].length; i++) {
            int idx = 0;
            try {
                idx = Integer.parseInt(rs[1][i]);
            } catch (Exception e) {
                //何もしない
            }
            if (idx == id1) {
                jComboBox1.setSelectedIndex(i);
            }
        }
    }
    public void setCaption(String str) {
        this.Caption.setText(str);
    }
    public void setPostCap(String str) {
        this.postCap.setText(str);
    }
    public void setId0(Integer id0) {
        this.ID0 = id0;
        this.setList();
    }
    public void setComboWidth(Integer width) {
        jSplitPane2.setDividerLocation(width);
        jComboBox1.setPreferredSize(new Dimension(width, 23));
        jComboBox1.setMinimumSize(new Dimension(width, 23));
        jSplitPane2.repaint();
    }
    private void setList(){
        boolean flg = false;
        for (int i = 0; i < arrRs.size(); i++) {
            try {
                String str = ((String[][])arrRs.get(i))[0][1];
                /*
                System.err.println(((String[][])arrRs.get(i))[0][1]);
                System.err.println(((String[][])arrRs.get(i))[1][1]);
                System.err.println(((String[][])arrRs.get(i))[2][1]);
           //     */
                long id0 = Long.parseLong(str);
                if (id0 == ID0) {
                    rs = arrRs.get(i);
                    flg = true;
                }
            } catch (Exception e) {
                //何もしない
            }
        }
        //選択したIDをコンボボックスに表示
        if (!flg) {
            rs = dbIT.getResultSetTable("WHERE id0 = " + ID0);
            arrRs.add(rs);
        }
        jComboBox1.removeAllItems();
        jComboBox1.addItem("");
        for (int i = 1; i < rs[2].length; i++) {
            jComboBox1.addItem(rs[2][i]);
        }
        logDebug("Default:" + defaultID1);
        setSelectedIndexID1(0);
    }
    public void setSelectedIndexID1(int id1) {
        jComboBox1.setSelectedIndex(0);
        try {
            if ((defaultID1 != 0) && (id1 == 0)) {
                //デフォルト値あり、ID指定なしの場合
                for1:for (int i = 1; i < rs[1].length; i++) {
                    logDebug("rs_id0:" + rs[0][i]);
                    logDebug("rs_id1:" + rs[1][i]);
                    if (Integer.parseInt(rs[1][i]) == defaultID1) {
                        jComboBox1.setSelectedIndex(i);
                        break for1;
                    }
                }
            } else {
                for2:for (int i = 1; i < rs[1].length; i++) {
                    if (Integer.parseInt(rs[1][i]) == id1) {
                        jComboBox1.setSelectedIndex(i);
                        break for2;
                    }
                }
            }
        }catch (Exception e) {
            e.printStackTrace();
            //何もしない
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
        bindingGroup = new org.jdesktop.beansbinding.BindingGroup();

        jSplitPane1 = new javax.swing.JSplitPane();
        Caption = new javax.swing.JLabel();
        jSplitPane2 = new javax.swing.JSplitPane();
        postCap = new javax.swing.JLabel();
        jComboBox1 = new javax.swing.JComboBox();

        setLayout(new javax.swing.BoxLayout(this, javax.swing.BoxLayout.LINE_AXIS));

        jSplitPane1.setBorder(null);
        jSplitPane1.setDividerSize(1);
        jSplitPane1.setDoubleBuffered(true);
        jSplitPane1.setFocusTraversalPolicyProvider(true);
        jSplitPane1.setMinimumSize(new java.awt.Dimension(300, 22));

        Caption.setFont(new java.awt.Font("Dialog", 0, 12)); // NOI18N
        Caption.setText("Caption");
        Caption.setDoubleBuffered(true);
        jSplitPane1.setLeftComponent(Caption);
        Caption.getAccessibleContext().setAccessibleParent(this);

        jSplitPane2.setBorder(null);
        jSplitPane2.setDividerLocation(100);
        jSplitPane2.setDividerSize(1);
        jSplitPane2.setDoubleBuffered(true);
        jSplitPane2.setFocusTraversalPolicyProvider(true);
        jSplitPane2.setLastDividerLocation(100);
        jSplitPane2.setOneTouchExpandable(true);
        jSplitPane2.setPreferredSize(new java.awt.Dimension(0, 22));

        postCap.setFont(new java.awt.Font("Dialog", 0, 12)); // NOI18N
        postCap.setText("postCap");
        postCap.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        postCap.setPreferredSize(new java.awt.Dimension(0, 13));
        jSplitPane2.setRightComponent(postCap);

        jComboBox1.setEditable(true);
        jComboBox1.setFont(new java.awt.Font("Dialog", 0, 12)); // NOI18N
        jComboBox1.setMinimumSize(new java.awt.Dimension(50, 22));
        jComboBox1.setPreferredSize(new java.awt.Dimension(100, 22));

        org.jdesktop.beansbinding.Binding binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, jComboBox1, org.jdesktop.beansbinding.ObjectProperty.create(), jComboBox1, org.jdesktop.beansbinding.BeanProperty.create("elements"));
        bindingGroup.addBinding(binding);

        jComboBox1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox1ActionPerformed(evt);
            }
        });
        jComboBox1.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                jComboBox1FocusLost(evt);
            }
        });
        jComboBox1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jComboBox1KeyPressed(evt);
            }
        });
        jSplitPane2.setLeftComponent(jComboBox1);

        jSplitPane1.setRightComponent(jSplitPane2);

        add(jSplitPane1);

        bindingGroup.bind();
    }// </editor-fold>//GEN-END:initComponents

    private void jComboBox1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox1ActionPerformed
        // TODO add your handling code here:
        //DebugMode = true;
        logDebug("jComboBox1ActionPerformed:" + evt.getActionCommand());
        if (evt.getActionCommand().equals("comboBoxEdited")){
            String str = (String) jComboBox1.getSelectedItem();
            int id = 0;
            try {
                id = Integer.parseInt((String) jComboBox1.getSelectedItem());
            }catch (Exception e) {
                //何もしない
            }
            
            logDebug("jComboBox1ActionPerformed:" + str);
            if (str.equals("createtable!")) {
                dbIT.createTable();
                dbIT.logDebug(evt.paramString());
                //再設定
                //setId0(ID0);  処理を待たないため無意味
                return;
            }
            if (str.equals("edit!")) {
                dbIT.editTable(dbIT, "WHERE id0 = " + ID0);
                dbIT.logDebug(evt.paramString());
                //再設定
                //setId0(ID0);
                return;
            }
            if (str.equals("editall!")) {
                dbIT.editTable(dbIT, "");
                dbIT.logDebug(evt.paramString());
                //再設定
                //setId0(ID0);
                return;
            }
            if (str.equals("read!")) {
                //再設定
                arrRs = new ArrayList<String[][]>();
                setId0(ID0);
                return;
            }
            
            //エディット時のみ設定（入力したものを選択）
            logDebug("エディット時のみ設定:" + id);
            for (int i = 1; i < rs[1].length; i++) {
                int idWk = 0;
                try {
                    idWk = Integer.parseInt(rs[1][i]);
                } catch (Exception e) {
                    //何もしない
                }
                if (id == idWk) {
                    logDebug("id:" + id);
                    jComboBox1.setSelectedIndex(i);
                    break;
                }
            }
        }
        logDebug("Idx:" + jComboBox1.getSelectedIndex());
    }//GEN-LAST:event_jComboBox1ActionPerformed

    private void jComboBox1FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jComboBox1FocusLost

    }//GEN-LAST:event_jComboBox1FocusLost

    private void jComboBox1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jComboBox1KeyPressed

    }//GEN-LAST:event_jComboBox1KeyPressed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel Caption;
    private javax.swing.JComboBox jComboBox1;
    private javax.swing.JSplitPane jSplitPane1;
    private javax.swing.JSplitPane jSplitPane2;
    private javax.swing.JLabel postCap;
    private org.jdesktop.beansbinding.BindingGroup bindingGroup;
    // End of variables declaration//GEN-END:variables
}
