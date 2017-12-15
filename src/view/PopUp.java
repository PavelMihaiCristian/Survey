package view;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;

public class PopUp
{

   
private JPanel pickerIdPanel;
   

   private JPanel container;
   private JOptionPane jOption ;
   

   private JLabel PickerIDLabel;
   private JTextField id ;/*************/
   
   private JPanel textAreaPanel;
   private JTextArea textArea;/**************/
   private JScrollPane scroll; 
   
   public PopUp()
   {
     
      pickerIdPanel = new JPanel();
      container = new JPanel(new BorderLayout());
      jOption = new JOptionPane();
      PickerIDLabel = new JLabel("Information: ");
      id = new JTextField(10);
      textAreaPanel = new JPanel();
      textArea = new JTextArea();
      scroll = new JScrollPane(textArea);
      
   }
   
   
   public void build()
   {
      
      pickerIdPanel.add(PickerIDLabel);
      pickerIdPanel.add(id);
      scroll.setPreferredSize(new Dimension(500, 500));
      textArea.setEditable(false);
      scroll.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
      textAreaPanel.add(scroll);
      container.add(pickerIdPanel, BorderLayout.NORTH);
      container.add(textAreaPanel, BorderLayout.SOUTH);
      
      jOption.add(container);
      
      jOption.setVisible(true);

      jOption.showMessageDialog(null, container, "Information Message",
            JOptionPane.INFORMATION_MESSAGE);
   }
   
   public JTextField getPickerId()
   {
      return id;
   }
   
   public JOptionPane getjOption()
   {
      return jOption;
   }
   
   public JTextArea getTextArea()
   {
      return textArea;
   }
   
   public static void main(String[] args)
   {
      PopUp or=new PopUp();
      or.getTextArea().setText("Tessssssssssst");
   }
}
