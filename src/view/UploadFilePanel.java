package view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class UploadFilePanel extends JPanel{

   private JPanel uploadFilePanel;
   private JTextField textSurveyTitle;
   private JTextField textSurveyYear;
   private JButton BNext;
   private JButton BCancel;
   private JTextArea textAreaSurveyDescription;
   private JScrollPane scroll;
   private JFileChooser fc;
 
   public UploadFilePanel(){
      initialize();
   }

   private void initialize() {
      
     // JFrame frame = new JFrame("Upload Page");
      
      uploadFilePanel=new JPanel();
      uploadFilePanel.setLayout(new BorderLayout());
      
      JPanel uploadFilePanelOption=new JPanel(new BorderLayout());
      
      JLabel lblUpload=new JLabel("LOAD FILE");
      uploadFilePanelOption.add(lblUpload,BorderLayout.NORTH);
      fc=new JFileChooser();
      fc.setPreferredSize(new Dimension(400,400));
      uploadFilePanelOption.add(fc,BorderLayout.CENTER);
      
      uploadFilePanel.add(uploadFilePanelOption,BorderLayout.WEST);
      
      JPanel titlePanel=new JPanel(new FlowLayout());
      
      Box box = Box.createVerticalBox();
      
      JLabel lblSurveyTitle=new JLabel("SURVEY TITLE");
      textSurveyTitle=new JTextField(25);
      
      titlePanel.add(lblSurveyTitle);
      titlePanel.add(textSurveyTitle);
      box.add(titlePanel);
      
      JPanel descriptionPanel=new JPanel(new FlowLayout());
      
      JLabel lblSurveyDescription=new JLabel("SURVEY DESCRIPTION");
      textAreaSurveyDescription=new JTextArea(10,25);
      textAreaSurveyDescription.setLineWrap(true);
      textAreaSurveyDescription.setWrapStyleWord(true);
      scroll = new JScrollPane(textAreaSurveyDescription, 
            JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, 
            JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
      descriptionPanel.add(lblSurveyDescription);
      descriptionPanel.add(scroll);
      box.add(descriptionPanel);
      
      JPanel yearPanel=new JPanel(new FlowLayout());
      
      JLabel lblSurveyYear=new JLabel("SURVEY YEAR");
      textSurveyYear=new JTextField(4);
      yearPanel.add(lblSurveyYear);
      yearPanel.add(textSurveyYear);
      box.add(yearPanel);
      
      uploadFilePanel.add(box,BorderLayout.CENTER);
      
      JPanel buttonsPanel=new JPanel(new FlowLayout());
      BCancel=new JButton("CANCEL");
      buttonsPanel.add(BCancel);
      BNext=new JButton("NEXT");
      buttonsPanel.add(BNext);
      uploadFilePanel.add(buttonsPanel,BorderLayout.SOUTH);
      
      
         add(uploadFilePanel);
         setSize(650, 650);
         setVisible(true);
         
         
         
   }

   public JTextField getTextSurveyTitle()
   {
      return textSurveyTitle;
   }

   public void setTextSurveyTitle(JTextField textSurveyTitle)
   {
      this.textSurveyTitle = textSurveyTitle;
   }

   public JTextField getTextSurveyYear()
   {
      return textSurveyYear;
   }

   public void setTextSurveyYear(JTextField textSurveyYear)
   {
      this.textSurveyYear = textSurveyYear;
   }

   public JTextArea getTextAreaSurveyDescription()
   {
      return textAreaSurveyDescription;
   }

   public void setTextAreaSurveyDescription(JTextArea textAreaSurveyDescription)
   {
      this.textAreaSurveyDescription = textAreaSurveyDescription;
   }

   public JFileChooser getFc()
   {
      return fc;
   }

   public void setFc(JFileChooser fc)
   {
      this.fc = fc;
   }

   public JButton getBNext()
   {
      return BNext;
   }

   public JButton getBCancel()
   {
      return BCancel;
   }
   

}
