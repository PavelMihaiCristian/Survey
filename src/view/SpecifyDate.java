package view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.ScrollPaneConstants;

import model.Question;
import model.Question.Type;

public class SpecifyDate extends JPanel
{

   private JButton BNext, BBack, BSpecifyDate;
   private JLabel Ltitle;
   private JPanel Ptitle, Pbutton, Pcontainer;
   private DefaultListModel listModel;
   private JList<Question> JLQuestions;
   private JComboBox<Type> ComboType;

   public SpecifyDate()
   {
      Ltitle = new JLabel("Survey Questions");
      listModel = new DefaultListModel();
      JLQuestions = new JList<Question>(listModel);
      ComboType = new JComboBox<Type>();
      BSpecifyDate = new JButton("SPECIFY DATE");
      BBack = new JButton("BACK");
      BNext = new JButton("NEXT");
      Ptitle = new JPanel(new BorderLayout());
      Pbutton = new JPanel();

      Pcontainer = new JPanel(new BorderLayout());
      Ltitle.setFont(new Font("title1", Font.BOLD, 20));
      Ptitle.add(Ltitle, BorderLayout.NORTH);

      JLQuestions.setVisibleRowCount(15);

      JLQuestions
            .setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
      JLQuestions.setLayoutOrientation(JList.VERTICAL);

      JScrollPane js = new JScrollPane(JLQuestions);

      js.setPreferredSize(new Dimension(800, 550));

      js.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
      Ptitle.add(js, BorderLayout.CENTER);

      Pbutton.add(ComboType);
      Pbutton.add(BSpecifyDate);
      Pbutton.add(BBack);
      Pbutton.add(BNext);

      Pcontainer.add(Ptitle, BorderLayout.CENTER);
      Pcontainer.add(Pbutton, BorderLayout.SOUTH);

      add(Pcontainer);
      setSize(800, 800);
      setVisible(true);

   }

   public JComboBox<Type> getComboType()
   {
      return ComboType;
   }

   public void setComboType(JComboBox<Type> comboCategory)
   {
      ComboType = comboCategory;
   }

   public JButton getBNext()
   {
      return BNext;
   }

   public JButton getBBack()
   {
      return BBack;
   }

   public JButton getBSpecifyDate()
   {
      return BSpecifyDate;
   }

   public DefaultListModel getListModel()
   {
      return listModel;
   }

   public JList<Question> getJLQuestions()
   {
      return JLQuestions;
   }
}
