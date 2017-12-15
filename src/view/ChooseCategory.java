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
import model.Question.Category;

public class ChooseCategory extends JPanel
{
   private JButton BNext, BBack, BAddCategory;
   private JLabel Ltitle;
   private JPanel Ptitle, Pbutton, Pcontainer;
   private DefaultListModel listModel;
   private JList<Question> JLQuestions;
   private JComboBox<Category> ComboCategory;

   public ChooseCategory()
   {
      Ltitle = new JLabel("Survey Questions");
      listModel = new DefaultListModel();
      JLQuestions = new JList<Question>(listModel);
      ComboCategory = new JComboBox<Category>();
      BAddCategory = new JButton("Add Category");
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

      Pbutton.add(ComboCategory);
      Pbutton.add(BAddCategory);
      Pbutton.add(BBack);
      Pbutton.add(BNext);

      Pcontainer.add(Ptitle, BorderLayout.CENTER);
      Pcontainer.add(Pbutton, BorderLayout.SOUTH);

      add(Pcontainer);
      setSize(800, 800);
      setVisible(true);

   }

   public JComboBox<Category> getComboCategory()
   {
      return ComboCategory;
   }

   public void setComboCategory(JComboBox<Category> comboCategory)
   {
      ComboCategory = comboCategory;
   }

   public JButton getBNext()
   {
      return BNext;
   }

   public JButton getBBack()
   {
      return BBack;
   }

   public JButton getBAddCategory()
   {
      return BAddCategory;
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
