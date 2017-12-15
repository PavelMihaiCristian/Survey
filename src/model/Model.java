package model;

import java.io.FileNotFoundException;
import java.sql.SQLException;
import java.util.ArrayList;

import database.MyDatabase;

public class Model
{
   private Survey survey;
   private SurveyHandler handler;
   private Question[] questions;
   private ArrayList<Respondent> respondants;
   private MyDatabase db;
   
   public Model(){
      handler = new SurveyHandler();
      try
      {
         db = new MyDatabase();
      }
      catch (SQLException e)
      {
         e.printStackTrace();
      }
      survey=null; 
   }
   
   public void setFilePath(String path){
      try
      {
         handler.setFilePath(path);
         this.questions=handler.getQuestions();
      }
      catch (FileNotFoundException e)
      {
         e.printStackTrace();
      }
   }
   
   public void createSurvey(String title, String description, int year){
      survey = new Survey(title, description, year);
   }
   
   public boolean writeToDB(){
      try
      {
         this.respondants= handler.getRespondantsWithAnswers();
      }
      catch (FileNotFoundException e)
      {
         e.printStackTrace();
      }
      db.prepareForExtract();
      db.writeToDB(questions, respondants, survey);
      return false;
   }

   public Survey getSurvey()
   {
      return survey;
   }

   public void setSurvey(Survey survey)
   {
      this.survey = survey;
   }

   public Question[] getQuestions()
   {
      return questions;
   }

   public void setQuestions(Question[] questions)
   {
      this.questions = questions;
   }

   public ArrayList<Respondent> getRespondants()
   {
      return respondants;
   }

   public void setRespondants(ArrayList<Respondent> respondants)
   {
      this.respondants = respondants;
   }
   

}
