package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.DefaultListModel;

import view.ChooseCategory;
import view.ChooseType;
import view.DiscardQuestions;
import view.Extract_GUI;
import view.PopUp;
import view.SpecifyDate;
import view.UploadFilePanel;
import model.Model;
import model.Question;
import model.Question.Category;
import model.Question.Type;
import model.Survey;
import model.SurveyHandler;

public class Controller
{

   private Model model;
   private Extract_GUI view;
   private MyListener listener;

   public Controller()
   {

      model = new Model();
      view = new Extract_GUI();
      listener = new MyListener();

      addListeners();

   }

   private void populateQuestionList()
   {
      DiscardQuestions discardPanel;
      discardPanel = view.getDiscardPanel();
      Question[] questions = model.getQuestions();
      DefaultListModel<Question> discListModel = discardPanel.getListModel();
      discListModel.clear();
      for (int i = 0; i < questions.length; i++)
      {
         if (i>0)
         {
            //same objects poiing at the same memory space
            if (questions[i]!=questions[i-1])
            {
               discListModel.addElement(questions[i]);
            }
         }else {
            discListModel.addElement(questions[i]);

         }
      }
      discardPanel.getJLQuestions().revalidate();
   }
   
   private void populateQuestionListforCategoryPage()
   {
      ChooseCategory catPage = view.getChooseCategoryPanel();
      Question[] questions = model.getQuestions();
      DefaultListModel<Question> catListModel = catPage.getListModel();
      catListModel.clear();
      for (int i = 0; i < questions.length; i++)
      {
         if(!questions[i].isDiscard()){
            //if he comes back to change category 
            //of a questions other than personality
            if (i>0)
            {
               //same objects poiing at the same memory space
               if (questions[i]!=questions[i-1])
               {
                  questions[i].setType(Type.NoType);
                  catListModel.addElement(questions[i]);
               }
            }else {
               questions[i].setType(Type.NoType);
               catListModel.addElement(questions[i]);

            }
            
         }
      }
      catPage.getJLQuestions().revalidate();
   }
   
   private void populateQuestionListforTypePage()
   {
      ChooseType typePage = view.getChooseTypePanel();
      Question[] questions = model.getQuestions();
      DefaultListModel<Question> catListModel = typePage.getListModel();
      catListModel.clear();
      for (int i = 0; i < questions.length; i++)
      {
         if(!questions[i].isDiscard()&&questions[i].getCategory().equals(Category.PERSONALITY)){
            catListModel.addElement(questions[i]);
         }
      }
      typePage.getJLQuestions().revalidate();
   }
   
   private void fillCatCombo(){
      ChooseCategory catPage = view.getChooseCategoryPanel();
      catPage.getComboCategory().removeAllItems();
      //One question is enough to read the possible categories
      Question q= model.getQuestions()[0];
      Object[] possibleCategories = q.getCategory().getDeclaringClass().getEnumConstants();
      //-1 to remove the nocategory value
      for(int i=0; i<possibleCategories.length-1;i++){
         catPage.getComboCategory().addItem((Category)(possibleCategories[i]));;
      }
      catPage.getComboCategory().revalidate();
   }
   
   private void fillTypeCombo(){
      ChooseType typePage = view.getChooseTypePanel();
      typePage.getComboType().removeAllItems();
      //One question is enough to read the possible categories
      Question q= model.getQuestions()[0];
      Object[] possibleTypes = q.getType().getDeclaringClass().getEnumConstants();
      for(int i=0; i<possibleTypes.length&&
           ! possibleTypes[i].toString().contains("DATE")&&
           ! possibleTypes[i].toString().contains("NoType");i++){
         typePage.getComboType().addItem((Type)(possibleTypes[i]));;
      }
      typePage.getComboType().revalidate();
   }
   
   private void fillComboSpecifyDate(){

      SpecifyDate datePage = view.getSpecifyDatePanel();
      datePage.getComboType().removeAllItems();
      //One question is enough to read the possible categories
      Question q= model.getQuestions()[0];
      Object[] possibleTypes = q.getType().getDeclaringClass().getEnumConstants();
      for(int i=0; i<possibleTypes.length;i++){
         if(possibleTypes[i].toString().contains("DATE")){
         datePage.getComboType().addItem((Type)(possibleTypes[i]));
         }
      }
      datePage.getComboType().revalidate();
   }
   
   private void populateQuestionListforSpecifyDatePage()
   {
      SpecifyDate specifydatepage = view.getSpecifyDatePanel();
      Question[] questions = model.getQuestions();
      DefaultListModel<Question> catListModel = specifydatepage.getListModel();
      catListModel.clear();
      for (int i = 0; i < questions.length; i++)
      {
         if(!questions[i].isDiscard()&&questions[i].getCategory().equals(Category.DATE)){
            catListModel.addElement(questions[i]);
         }
      }
      specifydatepage.getJLQuestions().revalidate();
   }

   public static void main(String args[])
   {
      Controller controller = new Controller();

   }

   public void addListeners()
   {
      DiscardQuestions discardPanel = view.getDiscardPanel();
      discardPanel.getBBack().addActionListener(listener);
      discardPanel.getBDiscard().addActionListener(listener);
      discardPanel.getBNext().addActionListener(listener);
      discardPanel.getBUndoDiscard().addActionListener(listener);

      UploadFilePanel uploadPanel = view.getUploadPanel();
      uploadPanel.getBNext().addActionListener(listener);
      uploadPanel.getBCancel().addActionListener(listener);
      uploadPanel.getFc().addActionListener(listener);
      
      ChooseCategory categoryPanel = view.getChooseCategoryPanel();
      categoryPanel.getBBack().addActionListener(listener);
      categoryPanel.getBNext().addActionListener(listener);
      categoryPanel.getBAddCategory().addActionListener(listener);
      
      ChooseType typePanel= view.getChooseTypePanel();
      typePanel.getBBack().addActionListener(listener);
      typePanel.getBAddType().addActionListener(listener);
      typePanel.getBSubmit().addActionListener(listener);
      
      SpecifyDate datePanel = view.getSpecifyDatePanel();
      datePanel.getBBack().addActionListener(listener);
      datePanel.getBSpecifyDate().addActionListener(listener);
      datePanel.getBNext().addActionListener(listener);
   }

   private class MyListener implements ActionListener
   {
      DiscardQuestions discardPanel = view.getDiscardPanel();
      UploadFilePanel uploadPanel = view.getUploadPanel();
      ChooseCategory categoryPanel=view.getChooseCategoryPanel();
      ChooseType typePanel = view.getChooseTypePanel();
      SpecifyDate datePanel = view.getSpecifyDatePanel();
      String fullPath = "";

      public void actionPerformed(ActionEvent e)
      {
         if (e.getSource() == discardPanel.getBDiscard())
         {
            List<Question> ques=discardPanel.getJLQuestions().getSelectedValuesList();
            for(int i=0; i<ques.size();i++){
               ques.get(i).setDiscard(true);;
            }

            populateQuestionList();
            
         }
         if (e.getSource() == discardPanel.getBUndoDiscard())
         {
            List<Question> ques=discardPanel.getJLQuestions().getSelectedValuesList();
            for(int i=0; i<ques.size();i++){
               ques.get(i).setDiscard(false);;
            }
            populateQuestionList();
            
         }
         
         if (e.getSource() == discardPanel.getBBack())
         {
            view.switchToUploadPage("Upload Page");
         }
         if (e.getSource() == typePanel.getBBack())
         {
//            populateQuestionListforCategoryPage();
//            view.switchToUploadPage("Choose Category");
              populateQuestionListforSpecifyDatePage();
              view.switchToUploadPage("Specify Date");
         }
         
         if (e.getSource() == datePanel.getBBack())
         {
            populateQuestionListforCategoryPage();
            view.switchToUploadPage("Choose Category");
         }
         if (e.getSource() == datePanel.getBNext())
         {
            boolean flag=true;
            Question[] questions=model.getQuestions();
            for(int i=0;i<questions.length;i++){
               if(!questions[i].isDiscard()
                     &&questions[i].getCategory().equals(Category.DATE)){
                  if(questions[i].getType().equals(Type.NoType)){
                     flag=false;
                  }
               }
            }
            if(flag){
             populateQuestionListforTypePage();
             fillTypeCombo();
             view.switchToUploadPage("Choose Type");
               
            }else{
               view.infoBox("You didn't set Type of all the questions", "Warning");
            } 
         }
         
         if (e.getSource() == datePanel.getBSpecifyDate())
         {
            Type choosenType=(Type)datePanel.getComboType().getSelectedItem();
            List<Question> ques=datePanel.getJLQuestions().getSelectedValuesList();
            for(int i=0; i<ques.size();i++){
               ques.get(i).setType(choosenType);
            }
            populateQuestionListforSpecifyDatePage();
         }
         if (e.getSource() == discardPanel.getBNext())
         {
            populateQuestionListforCategoryPage();
            fillCatCombo();
            view.switchToUploadPage("Choose Category");
         }
         if (e.getSource() == categoryPanel.getBBack())
         {
            populateQuestionList();
            view.switchToUploadPage("Discard Page");
         }
         if (e.getSource() == categoryPanel.getBNext())
         {
            boolean flag=true;
            Question[] questions=model.getQuestions();
            for(int i=0;i<questions.length;i++){
               if(!questions[i].isDiscard()){
                  if(questions[i].getCategory().equals(Category.NoCategory)){
                     flag=false;
                  }
               }
            }
            if(flag){
               populateQuestionListforSpecifyDatePage();
               fillComboSpecifyDate();
               view.switchToUploadPage("Specify Date");
            }else{
               view.infoBox("You didn't set Category of all the questions", "Warning");
            }
         }
         if (e.getSource() == categoryPanel.getBAddCategory())
         {
            Category choosenCat=(Category)categoryPanel.getComboCategory().getSelectedItem();
            List<Question> ques=categoryPanel.getJLQuestions().getSelectedValuesList();
            for(int i=0; i<ques.size();i++){
               ques.get(i).setCategory(choosenCat);
            }
            populateQuestionListforCategoryPage();
         }
         if (e.getSource() == typePanel.getBAddType())
         {
            Type choosenType=(Type)typePanel.getComboType().getSelectedItem();
            List<Question> ques=typePanel.getJLQuestions().getSelectedValuesList();
            for(int i=0; i<ques.size();i++){
               ques.get(i).setType(choosenType);
            }
            populateQuestionListforTypePage();
         }
         
         
         if (e.getSource() == typePanel.getBSubmit())
         {
            boolean flag=true;
            Question[] questions=model.getQuestions();
            for(int i=0;i<questions.length;i++){
               if(!questions[i].isDiscard()
                     &&questions[i].getCategory().equals(Category.PERSONALITY)){
                  if(questions[i].getType().equals(Type.NoType)){
                     flag=false;
                  }
               }
            }
            if(flag){
               view.infoBox("Survey Will be Submitted. Please Wait!", "Success");
               model.writeToDB();
               view.infoBox("Survey Successfully inserted!", "Success");
               
            }else{
               view.infoBox("You didn't set Type of all the questions", "Warning");
            }
         }
         
         
         
         if (e.getSource() == uploadPanel.getBNext())
         {
            boolean flag = true;
            String title = uploadPanel.getTextSurveyTitle().getText();
            String description = uploadPanel.getTextAreaSurveyDescription()
                  .getText();
            int year = 0;
            try
            {
               year = Integer.parseInt(uploadPanel.getTextSurveyYear()
                     .getText());
            }
            catch (NumberFormatException exc)
            {
               exc.printStackTrace();
               flag = false;
               view.infoBox("YEAR is not valid", "Warning");
            }
            if (flag && !title.equals("") && !description.equals("")
                  && !fullPath.equals(""))
            {
               model.createSurvey(title, description, year);
               populateQuestionList();
               view.switchToUploadPage("Discard Page");
            }
            else
            {
               view.infoBox("Title or Description"
                     + " are empty OR file not selected!!!", "Warning");
            }
         }
         if (e.getSource() == uploadPanel.getBCancel())
         {
            System.exit(0);
         }
         if (e.getSource() == uploadPanel.getFc())
         {

            if (e.getActionCommand().equals(
                  javax.swing.JFileChooser.APPROVE_SELECTION))
            {
               File file = uploadPanel.getFc().getSelectedFile();
               fullPath = file.getAbsolutePath();
               fullPath = fullPath.replace("\\", "/");
               model.setFilePath(fullPath);
            }
            else if (e.getActionCommand().equals(
                  javax.swing.JFileChooser.CANCEL_SELECTION))
            {
            }
         }

      }
   }
}
