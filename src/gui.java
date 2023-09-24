import java.io.FileInputStream;
import java.io.IOException;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class gui {
    static int currentSlot;
    

    public static void main(String[] args) throws Exception {
        currentSlot = 0;
        FileInputStream save = new FileInputStream("S.sav");
        byte[] saveAsByteArray = new byte[131072];
        save.read(saveAsByteArray);


        JFrame frame = new JFrame("Chat Frame");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 400);

        //Creating the panel at bottom and adding components
        JPanel panel = new JPanel(); // the panel is not visible in output
        JButton next = new JButton("Next");
        JTextArea currentSlotDisplay = new JTextArea(Integer.toString(currentSlot));
        JButton prev = new JButton("Previous");
        
        // Components Added using Flow Layout
        panel.add(prev);
        panel.add(currentSlotDisplay);
        panel.add(next);

        // Text Area at the Center
        JPanel panel2 = new JPanel();
        JLabel index = new JLabel(Integer.toString(SavReadFunctions.readID(SavReadFunctions.readPKMN(currentSlot, saveAsByteArray))));
        JLabel name = new JLabel(SavReadFunctions.indexNameLookup(SavReadFunctions.readID(SavReadFunctions.readPKMN(currentSlot, saveAsByteArray))));
        name.setHorizontalAlignment(0);
        index.setHorizontalAlignment(0);
        panel2.add(index);
        panel2.add(name);

        //Adding Components to the frame.
        frame.getContentPane().add(BorderLayout.SOUTH, panel);
        frame.getContentPane().add(BorderLayout.CENTER, panel2);
        frame.setVisible(true);



        next.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                if(currentSlot >= 419){currentSlot = 0;}
                else{currentSlot = currentSlot + 1;}
                currentSlotDisplay.setText(Integer.toString(currentSlot));
                try {
                    index.setText(Integer.toString(SavReadFunctions.readID(SavReadFunctions.readPKMN(currentSlot, saveAsByteArray))));
                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                try {
                    name.setText(SavReadFunctions.indexNameLookup(SavReadFunctions.readID(SavReadFunctions.readPKMN(currentSlot, saveAsByteArray))));
                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        });

        prev.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                if(currentSlot <= 0){currentSlot = 419;}
                else{currentSlot = currentSlot - 1;}
                currentSlotDisplay.setText(Integer.toString(currentSlot));
                try {
                    index.setText(Integer.toString(SavReadFunctions.readID(SavReadFunctions.readPKMN(currentSlot, saveAsByteArray))));
                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                try {
                    name.setText(SavReadFunctions.indexNameLookup(SavReadFunctions.readID(SavReadFunctions.readPKMN(currentSlot, saveAsByteArray))));
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        });
    }
}
