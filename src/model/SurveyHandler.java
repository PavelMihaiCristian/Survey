package model;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.ObjectInputStream.GetField;
import java.util.ArrayList;
import java.util.Scanner;

public class SurveyHandler
{
   private String path;
   private String[][] data;

   public SurveyHandler()
   {
      this.path = "";
   }

   public String[][] getData(){
      return data;
   }
   public void setFilePath(String path) throws FileNotFoundException
   {
      this.path = path;
      getDataInBiArray();
   }

   private void getDataInBiArray() throws FileNotFoundException
   {
      Scanner scanner = getNewScanner();
      int rows = 1;
      int columns =0;
      String[] line = scanner.nextLine().split(",\"");
      columns = line.length;
      while (scanner.hasNextLine())
      {
         rows++;  
         scanner.nextLine();
      }
      data = new String[rows][columns];
      scanner.close();
      scanner=getNewScanner();
      rows=0;
      while (scanner.hasNextLine())
      {
         line = scanner.nextLine().split(",\"");
         for (int column = 0; column < line.length; column++)
         {
            data[rows][column]=line[column].replace("\"", "");
         }
         rows++;
      }
   }
   
   private String[] getHeaderLine() throws FileNotFoundException{
      String[] questions=new String[data[0].length];
      for (int i = 0; i < data[0].length; i++)
      {
         questions[i]=data[0][i];
      }
      return questions;
   }
      
   private Scanner getNewScanner() throws FileNotFoundException
   {

      Scanner scanner = new Scanner(new File(path), "UTF-8");
      scanner.useDelimiter(",");
      return scanner;
   }

   public Question[] getQuestions() throws FileNotFoundException
   {
      String[] questions = getHeaderLine();
     
      Question[] questionObject = new Question[questions.length];
      String[] questionParts;
      int groupNumber = 1;
      for (int i = 0; i < questions.length; i++)
      {
         if (questions[i].contains(":"))
         {
            questionParts = questions[i].split(":");
            questions[i] = questionParts[1].replace("\"", "");
         }
         if (i > 0)
         {
            if (questionObject[i - 1].getText().equals(questions[i]))
            {
               if (questionObject[i - 1].getGroupID() == -1)
               {
                  questionObject[i - 1].setGroupID(groupNumber);
                  groupNumber++;
               }
               questionObject[i] = questionObject[i - 1];
            }
            else
            {
               questionObject[i] = new Question(questions[i]);
            }
         }
         else
         {
            questionObject[i] = new Question(questions[i]);
         }

      }
      populatePossibleAnswers(questionObject);
      return questionObject;
   }

   public void populatePossibleAnswers(Question[] questions)
         throws FileNotFoundException
   {
      for (int i = 0; i < questions.length; i++)
      {
         if (questions[i].getGroupID() != -1)
         {
            getMultichoiceAnswer(i, questions[i]);
         }
         else
         {
            addAnswers(i, questions[i]);
         }
      }
   }

   private boolean verifyAnswer(int index, String Qanswer)
         throws FileNotFoundException
   {
      for(int row = 1; row < data.length; row++)
      {
         if (data[row][index].equals(Qanswer))
         {
            return true;
         }
      }
      return false;
   }

   // goes throuth the a column to collect the possible answer for a question
   // it is regarding questions that do not have also the answer in the header
   // This method is used for collecting the questions and their answer part
   private void addAnswers(int index, Question quesiton)
         throws FileNotFoundException
   {
      for (int row = 1; row < data.length; row++)
      {
         String[] answersInColumn = data[row][index].split(";");
         if (answersInColumn.length > 1)
         {
            for (int i = 0; i < answersInColumn.length; i++)
            {
               quesiton.addAnswerToQuestion(answersInColumn[i]);
            }
         }
         else
         {
            if (!existInArray(data[row][index], quesiton.getAnswers()))
            {
               quesiton.addAnswerToQuestion(data[row][index]);
            }
         }
      }
   }

   private boolean existInArray(String answer, ArrayList<String> answers)
   {
      for (int i = 0; i < answers.size(); i++)
      {
         if (answers.get(i).equals(answer))
         {
            return true;
         }
      }
      return false;
   }

   // method for adding the answers to the questions that are grouped and have
   // the answer in
   // the question header splitted by a :, the else case is for the question
   // gender that does
   // not have a : for both header question
   private void getMultichoiceAnswer(int index, Question question)
         throws FileNotFoundException
   {
      String[] questions=getHeaderLine();
      String[] questionParts = questions[index].split(":");
      if (questionParts.length == 2)
      {
         if (!verifyAnswer(index, questionParts[0])
               && !(questionParts[0].toUpperCase()).contains("OTHER"))
         {
            addConcatenatedAnswersToQuestion(questionParts[0], index, question);
         }
         else
         {
            question.addAnswerToQuestion(questionParts[0]);
         }
      }
      else
      {
         addAnswers(index, question);
      }
   }

   private void addConcatenatedAnswersToQuestion(String answerFromHeader,
         int index, Question question) throws FileNotFoundException
   {
      for (int row = 1; row < data.length; row++)
      {
         if (!data[row][index].equals(""))
         {
            question.addAnswerToQuestion(answerFromHeader + "-"
                  + data[row][index]);
         }
      }
   }
   //this method returns a string of answer from the header if the
   //answer in the column is different than it
   public String questionContainsAnswer(int index) throws FileNotFoundException
   {
      String[] questions = getHeaderLine();
      String[] questionParts = questions[index].split(":");
      if (questionParts.length == 2)
      {
         if (!verifyAnswer(index, questionParts[0]))
         {
            return questionParts[0];
         }
      }
      return "";
   }

   public ArrayList<Respondent> getRespondantsWithAnswers()
         throws FileNotFoundException
   {
      Question[] questions = getQuestions();
      ArrayList<Respondent> respondants = new ArrayList<Respondent>();
      for (int row = 1; row < data.length; row++)
      {
         String[] responses = data[row];
         Respondent person = new Respondent();
         for (int i = 0; i < responses.length; i++)
         {
            String answerfromQuestion = questionContainsAnswer(i);
            //IF the question at this index contains an answer as part of it
            //and the answer is not verified then concatenate
            if (!answerfromQuestion.equals(""))
            {
               //In case of "Other", not to add it
               if (!responses[i].equals(""))
               {
                  if(!(answerfromQuestion.toUpperCase()).contains("OTHER")){
                  person.addAnswerToQuestion(questions[i].getText(),
                        answerfromQuestion + "-" + responses[i]);
                  }
               }
            }
            else
            {
                  //Handle the part when the response contains multiple responses
               String[] multipleAnswersInColumn=responses[i].split(";");
               if (multipleAnswersInColumn.length>1)
               {
                  for (int j = 0; j < multipleAnswersInColumn.length; j++)
                  {
                     person.addAnswerToQuestion(questions[i].getText(), multipleAnswersInColumn[j]);
                  }
               }else {
                  person.addAnswerToQuestion(questions[i].getText(),
                        responses[i]);
               }
            }

         }
         respondants.add(person);
      }
      return respondants;
   }

}
