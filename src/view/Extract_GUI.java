package view;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import model.Question;


public class Extract_GUI extends JFrame 
{
   private JFrame frame;
   private JPanel discardPage;
   private JPanel uploadfilePage;
   private JPanel chooseCatPage;
   private JPanel chooseTypePage;
   private JPanel specifyDatePage;
   private JPanel panel;
   private CardLayout cl;
   private ActionListener myAction;
   private Container overAllPanel;
   
   public Extract_GUI(){
      frame = new JFrame();
      discardPage = new DiscardQuestions();
      uploadfilePage = new UploadFilePanel();
      chooseCatPage = new ChooseCategory();
      chooseTypePage= new ChooseType();
      specifyDatePage= new SpecifyDate();
      cl = new CardLayout();
      panel = new JPanel(cl);
      overAllPanel=new JPanel(new BorderLayout());
      
      panel.add(discardPage, "Discard Page");
      panel.add(uploadfilePage, "Upload Page");
      panel.add(chooseCatPage, "Choose Category");
      panel.add(chooseTypePage, "Choose Type");
      panel.add(specifyDatePage, "Specify Date");
      cl.show(panel, "Upload Page");

      frame.add(panel);
      frame.setSize(1000, 700);
      frame.setVisible(true);
      frame.setResizable(true);

      frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
      frame.setLocationRelativeTo(null);
      
   }
   
   public DiscardQuestions getDiscardPanel(){
      return (DiscardQuestions)discardPage;
   }
   public UploadFilePanel getUploadPanel(){
      return (UploadFilePanel)uploadfilePage;
   }
   public ChooseCategory getChooseCategoryPanel(){
      return (ChooseCategory)chooseCatPage;
   }
   public ChooseType getChooseTypePanel(){
      return (ChooseType)chooseTypePage;
   }
   public SpecifyDate getSpecifyDatePanel(){
      return (SpecifyDate)specifyDatePage;
   }
   public void switchToUploadPage(String panelName){
      cl.show(panel, panelName);
   }
   
   public  void infoBox(String infoMessage, String titleBar)
   {
       JOptionPane.showMessageDialog(null, infoMessage, "InfoBox: " + titleBar, JOptionPane.INFORMATION_MESSAGE);
   }
   
}
