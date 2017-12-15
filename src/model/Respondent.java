package model;

import java.util.ArrayList;

public class Respondent
{
   private ArrayList<Question> qa;
   
   public Respondent(){
      qa=new ArrayList<Question>();
   }
   
   public void addQuestion(String text){
      if(!existInArray(text)){
         qa.add(new Question(text));
      }
   }
   
   public void addAnswerToQuestion(String questionText,String answer){
      addQuestion(questionText);/////////So we don't need to call twice
      Question q=findQuestion(questionText);
      q.addAnswerToQuestion(answer);

   }

   private Question findQuestion(String questionText)
   {
      for (int i = 0; i < qa.size(); i++)
      {
         if (qa.get(i).getText().equals(questionText))
         {
            return qa.get(i);
         }
      }
      return null;
   }
   
   private boolean existInArray(String questionText)
   {
      for (int i = 0; i < qa.size(); i++)
      {
         if (qa.get(i).getText().equals(questionText))
         {
            return true;
         }
      }
      return false;
   }
   
//   private boolean existInArray(String questionText, ArrayList<String> list)
//   {
//      for (int i = 0; i < list.size(); i++)
//      {
//         if (list.get(i).equals(questionText))
//         {
//            return true;
//         }
//      }
//      return false;
//   }

   public ArrayList<Question> getQa()
   {
      return qa;
   }

   public void setQa(ArrayList<Question> qa)
   {
      this.qa = qa;
   }
   
}
