package model;
import java.util.ArrayList;


public class Question
{
   private String text;
   private boolean discard;
   private Category category;
   private boolean inserted;
   private int groupID;
   private Type type;
   //possible answers of the question
   private ArrayList<String> answers;
//   //position of question in the row (index)
//   private ArrayList<Integer> indexOfQ;
   public enum Category {PERSONALITY, LAYER, DEMOGRAPHIC, EXTRA,DATE, NoCategory}
   public enum Type {FORANDERLIG_FORANKRET, JEG_GRUPPE, STIMULANS_SIKKERHED, VELVÆRE_DOMINANS,NoType,DATE_STARTED,DATE_COMPLETED,DATE_OTHERS}
   
   public Question(String text)
{

   this.text = text;
   this.discard = false;
   this.category = Category.NoCategory;
   this.inserted = false;
   this.answers = new ArrayList<String>();
   this.groupID = -1;
   this.type=Type.NoType;
}
   
   
   public int getGroupID()
   {
      return groupID;
   }

   public void setGroupID(int groupID)
   {
      this.groupID = groupID;
   }

   public void setCategory(Category category)
   {
      this.category = category;
   }

   public String getText()
   {
      return text;
   }
   public void setText(String text)
   {
      this.text = text;
   }
   public boolean isDiscard()
   {
      return discard;
   }
   public void setDiscard(boolean discard)
   {
      this.discard = discard;
   }
   public Category getCategory()
   {
      return category;
   }

   public ArrayList<String> getAnswers()
   {
      return answers;
   }
   public void setAnswers(ArrayList<String> answers)
   {
      this.answers = answers;
   }


   public boolean isInserted()
   {
      return inserted;
   }


   public void setInserted(boolean inserted)
   {
      this.inserted = inserted;
   }


   public Type getType()
   {
      return type;
   }


   public void setType(Type type)
   {
      this.type = type;
   }
   
   public void addAnswerToQuestion(String answer){
      if(!existInArray(answer) && !answer.equals("")){
         answers.add(answer);
      }
   }
   
   private boolean existInArray(String questionText)
   {
      for (int i = 0; i < answers.size(); i++)
      {
         if (answers.get(i).equals(questionText))
         {
            return true;
         }
      }
      return false;
   }
   
   public String toString(){
     
      
      return text+(discard?"/ [Discarded]":"  [NotDiscarded]  ")
            +"   ["+category.toString()+"]   "
            +"["+type.toString()+"]";
   }
   
 /*  public ArrayList<Integer> getIndexOfQ()
   {
      return indexOfQ;
   }
   public void setIndexOfQ(ArrayList<Integer> indexOfQ)
   {
      this.indexOfQ = indexOfQ;
   }*/
   
   /*
    1. get question
    2. check if it is a single question or related to a question group
    3. Case single question
       a. The user will place it in a category or discard it
          if category is personality, ask for type of the question
       b. The user has the ability to change the text of the question
          we don't care because we are dealing with it as an index and not text
       c. mark the question as done. and store its index ( where is it in the list) if needed
       d. loop through the column of answers and select distinct to get all possible answers
       
       
       add it to a hash map <Question, index>?
    4. Case Question related to a group
       a. split the answer from the question
       b. verify the answer part
          if answer not verified
             try to verify question part with others
                -check using the question part if there are other questions related
                -group them
                -get the answer from every question and add it to the list
                
                do one more check to confirm the parallel read, 
                         is the answer for this question/check the list of answers in the question?
    */
   
}
