package view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Scrollbar;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.ScrollPaneConstants;

import model.Question;

public class DiscardQuestions extends JPanel
{
   private JButton BNext, BBack, BDiscard,BUndoDiscard;
   private JLabel Ltitle;
   private JPanel Ptitle, Pbutton, Pcontainer;
   private DefaultListModel listModel;
   private JList<Question> JLQuestions;
   
   public DiscardQuestions(){
      Ltitle = new JLabel("Survey Questions");
      listModel = new DefaultListModel();
      JLQuestions = new JList<Question>(listModel);
      BBack = new JButton("BACK");
      BUndoDiscard = new JButton("Undo Discard");
      BDiscard= new JButton("DISCARD");
      BNext= new JButton("NEXT");
      Ptitle = new JPanel(new BorderLayout());
      Pbutton = new JPanel();
    //  Pcontainer = new JPanel(new GridLayout(2,1));
      Pcontainer= new JPanel(new BorderLayout());
      Ltitle.setFont(new Font("title1", Font.BOLD,20));
      Ptitle.add(Ltitle, BorderLayout.NORTH);
      
      JLQuestions.setVisibleRowCount(15);
     // JLQuestions.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
      JLQuestions.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
      JLQuestions.setLayoutOrientation(JList.VERTICAL);

      JScrollPane js = new JScrollPane(JLQuestions);
      
      js.setPreferredSize(new Dimension(800,550));
      
      js.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
      Ptitle.add(js, BorderLayout.CENTER);
      
      Pbutton.add(BBack);
      Pbutton.add(BUndoDiscard);
      Pbutton.add(BDiscard);
      Pbutton.add(BNext);

      Pcontainer.add(Ptitle,BorderLayout.CENTER);
      Pcontainer.add(Pbutton,BorderLayout.SOUTH);

      add(Pcontainer);
      setSize(800, 800);
      setVisible(true);
   }

   public JButton getBNext()
   {
      return BNext;
   }

   public JButton getBBack()
   {
      return BBack;
   }

   public JButton getBDiscard()
   {
      return BDiscard;
   }
   
   public JButton getBUndoDiscard()
   {
      return BUndoDiscard;
   }

   public JList<Question> getJLQuestions()
   {
      return JLQuestions;
   }

   public DefaultListModel getListModel()
   {
      return listModel;
   }

   public void setListModel(DefaultListModel listModel)
   {
      this.listModel = listModel;
   }
   
   
   
   

}
