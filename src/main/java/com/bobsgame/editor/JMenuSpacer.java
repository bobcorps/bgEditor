package com.bobsgame.editor;

import javax.swing.*;

public class JMenuSpacer extends JMenuItem {
    public JMenuSpacer(String s) {
        super.setText(s);
        super.setEnabled(false);
        super.setArmed(false);
    }
}