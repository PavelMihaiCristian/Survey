import java.io.FileNotFoundException;
import java.io.ObjectInputStream.GetField;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

import database.MyDatabase;
import model.Question;
import model.Respondent;
import model.Survey;
import model.SurveyHandler;


public class SimpleTest
{

   public static void main(String[] args) throws FileNotFoundException
   {
      
      long startTime = System.nanoTime();
      
      
      Scanner keyboard = new Scanner(System.in);
      SurveyHandler handler = new SurveyHandler();
      handler.setFilePath("C:/Users/pavel/Documents/Originaldata.csv");
//      handler.setFilePath("C:/Users/pavel/Documents//VEMC Service with personality questions.csv");
     
      
//      String[][] data=handler.getData();
//      for (int i = 0; i < data.length; i++)
//      {
//         for (int j = 0; j < data[i].length; j++)
//         {
//            System.out.print(data[i][j]+"  ");
//         }
//         System.out.println();
//      }
      
      Question[] questions= null;
      
      try
      {
          questions = handler.getQuestions();
          
      }
      catch (FileNotFoundException e)
      {
         // TODO Auto-generated catch block
         e.printStackTrace();
      }
//      for (int i = 0; i < questions.length; i++)
//      {
//         System.out.println(questions[i].getText() +
//               "(Group: "+questions[i].getGroupID()+")");
//         System.out.println(questions[i].getAnswers().toString());
//         System.out.println("---------------------------");
//      }
//      System.out.println(questions.length);
      ArrayList<Respondent> respondants=null;
      respondants = null;
      try
      {
         
        respondants = handler.getRespondantsWithAnswers();
        
      }
      catch (FileNotFoundException e)
      {
         // TODO Auto-generated catch block
         e.printStackTrace();
      }
//      System.out.println("Number of responses: "+ respondants.size());
//      Respondent person;
//       for ( int i=0 ; i<respondants.size();i++){
//   
//          person=respondants.get(i);
//          
//          for(int k=0;k<person.getQa().size();k++){
//             ArrayList<String> personAnswers=person.getQa().get(k).getAnswers();
//             System.out.println("Question is: "+person.getQa().get(k).getText());
//             for(int j=0; j<personAnswers.size();j++){
////                System.out.println(personAnswers.size());
////                keyboard.nextLine();
//                System.out.print(personAnswers.get(j)+" && ");
//             }
//             System.out.println();
//          }
//          
//          
//         
//       }
       /**********************/
       long  difference = System.nanoTime() - startTime; 
       System.out.println("Total execution time: " +
             String.format("%d min, %d sec",
                     TimeUnit.NANOSECONDS.toHours(difference),
                     TimeUnit.NANOSECONDS.toSeconds(difference) -
                             TimeUnit.MINUTES.toSeconds(TimeUnit.NANOSECONDS.toMinutes(difference))));
       /*************************/
       MyDatabase d = null;
       try {
          d = new MyDatabase();
       } catch (SQLException e) {
          e.printStackTrace();
       }
//     d.insertSurveyInfo(new Survey("BED", "BED survey", 2017));
//     d.writeInABatchQuestions(questions, new Survey("BED", "BED survey", 2017));
//     for (int i = 0; i < questions.length; i++) {
//        questions[i].setInserted(false);
//     }
//     d.writeInABatchAnswers(questions);
//     d.insertResponses(respondants);
       d.prepareForExtract();
       d.writeToDB(questions, respondants, new Survey("BED", "About beds", 2017));
      
       /***********************/
        difference = System.nanoTime() - startTime; 
       System.out.println("Total execution time: " +
             String.format("%d min, %d sec",
                     TimeUnit.NANOSECONDS.toHours(difference),
                     TimeUnit.NANOSECONDS.toSeconds(difference) -
                             TimeUnit.MINUTES.toSeconds(TimeUnit.NANOSECONDS.toMinutes(difference))));
       
   }
  

}
